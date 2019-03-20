package com.example.wangji.changemax.activity.mea;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.activity.mea.detailed.MedicalDiseaseDetailedDataActivity;
import com.example.wangji.changemax.service.internal.DiseaseService;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.wangji.changemax.model.internal.Disease;
import com.example.wangji.changemax.model.internal.Symptom;
import com.example.wangji.changemax.service.internal.PartService;
import com.example.wangji.changemax.service.internal.SymptomService;
import com.example.wangji.changemax.util.sqlite_util.external.DBManager;


public class MedicalEncyclopediaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnScrollListener {
    private static final String TAG = "MedicalEncyclopediaActivity";

    private Context myContext = this;


    private int diSySwitch = 0;//疾病症状选择，默认为疾病

    //定义一个变量，来标识是否关闭
    private static boolean isShutDown = false;

    private EditText et_search;
    private Button btn_search_for;
    private ImageButton return_me;
    private TextView loadInfo;
    private ListView lv_disy_object;
    private LinearLayout loadLayout;

    private List<Disease> diseaseList;
    private DiseaseService diseaseService;

    private List<Symptom> symptomList;
    private SymptomService symptomService;

    private PartService partService;

    private String msg = "";
    private String buWei = "";


    private int currentPage = 1; //默认在第一页
    private static final int lineSize = 7;    //每次显示数
    private int allRecorders = 0;  //全部记录数
    private int pageSize = 1;  //默认共一页
    private int lastItem;
    private Aleph0 baseAdapter;

    //集合,用于显示在view中
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            isShutDown = false;
            switch (what) {
                case 1:
                    //ListView条目控制在第一行
                    lv_disy_object.setSelection(0);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_encyclopedia);

        init();

        onClickListener();

        addLoadView();
        showData();//初始时，为空，则查询所有数据

    }//end onCreate()


    private void init() {
        setTitle("医疗百科    类别：" + "不限");
        viewInit();
        objectInit();
    }

    private void onClickListener() {
        //设置listview点击事件
        lv_disy_object.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (diseaseList != null & i > diseaseList.size() - 1) {
                    Toast.makeText(MedicalEncyclopediaActivity.this, "该医疗数据为空.", Toast.LENGTH_LONG).show();
                } else {
                    Disease disease = diseaseList.get(i);
                    Intent intent = new Intent(myContext, MedicalDiseaseDetailedDataActivity.class);
                    String disease_id = disease.getDisease_id() + "";
                    intent.putExtra("disease_id", disease_id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

                }
            }
        });

        return_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalEncyclopediaActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                MedicalEncyclopediaActivity.this.finish();
            }
        });
    }

    private void viewInit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lv_disy_object = (ListView) findViewById(R.id.lv_disy_object);

        return_me = (ImageButton)findViewById(R.id.return_me);
    }

    private void objectInit() {
        diseaseService = new DiseaseService(myContext);
        initSqlite();
    }

    private void addLoadView() {
        //创建一个角标线性布局用来显示"正在加载"
        loadLayout = new LinearLayout(this);
        loadLayout.setGravity(Gravity.CENTER);
        //定义一个文本显示“正在加载”
        loadInfo = new TextView(this);
        loadInfo.setText("正在加载...");
        loadInfo.setGravity(Gravity.CENTER);
        //增加组件
        loadLayout.addView(loadInfo, new LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //增加到listView底部
        lv_disy_object.addFooterView(loadLayout);
        lv_disy_object.setOnScrollListener(MedicalEncyclopediaActivity.this);
    }

    //初始化数据库
    private void initSqlite() {
        String sqliteName = "medical_library.db";
        new DBManager(myContext).CopySqliteFileFromRawToDatabases(sqliteName);//传入数据库到本地
    }

    /**
     * 读取全部数据或部分数据
     */
    public void showData() {
        Log.v("Medical...showData", "刷新数据");
        if (TextUtils.isEmpty(buWei)) {
            allRecorders = diseaseService.getCountByName(msg);
        } else {
            allRecorders = diseaseService.getCountByPartOrgan(msg);
        }
        Log.v("Medical...数据或部分数据", msg);
        //计算总页数
        pageSize = (allRecorders + lineSize - 1) / lineSize;
        System.out.println("allRecorders（所有记录） =  " + allRecorders);
        System.out.println("pageSize（页面大小）  =  " + pageSize);
        if (TextUtils.isEmpty(buWei)) {
            diseaseList = diseaseService.getAllDiseaseByPaginationName(msg, currentPage, lineSize);
        } else {
            diseaseList = diseaseService.getAllDiseaseByPaginationPartOrgan(msg, currentPage, lineSize);
        }
        if (diseaseList != null) {
            System.out.println("测试数据：" + diseaseList.get(0).getDisease_name());

            for (int i = 0; i < diseaseList.size(); i++) {
                System.out.println(diseaseList.get(i));
            }
            baseAdapter = new Aleph0();
            lv_disy_object.setAdapter(baseAdapter);
        } else {//如果查询数据为空，那么将再次执行一次查询
            buWei = "";//置为不限
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
        lastItem = firstVisible + visibleCount - 1; //统计是否到最后
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scorllState) {
        System.out.println("进入滚动界面了");
        //是否到最底部并且数据没读完
        if (lastItem == baseAdapter.getCount() && currentPage < pageSize && scorllState == OnScrollListener.SCROLL_STATE_IDLE) {
            currentPage++;
            //设置显示位置
            lv_disy_object.setSelection(lastItem);
            //增加数据
            appendDate();
        }
    }


    /**
     * 增加数据
     */
    private void appendDate() {
        List<Disease> addDiseaseList = null;
        if (TextUtils.isEmpty(buWei)) {
            addDiseaseList = diseaseService.getAllDiseaseByPaginationName(msg, currentPage, lineSize);
        } else {
            addDiseaseList = diseaseService.getAllDiseaseByPaginationPartOrgan(msg, currentPage, lineSize);
        }
        Log.v("Medical...Activity增加数据", msg);
        if (addDiseaseList != null && addDiseaseList.size() > 0) {
            baseAdapter.setCount(baseAdapter.getCount() + addDiseaseList.size());
            //判断，如果到了最末尾则去掉“正在加载”
            if (allRecorders == baseAdapter.getCount()) {
                loadInfo.setText("到底了...");
            }
            diseaseList.addAll(addDiseaseList);
            //通知记录改变
            baseAdapter.notifyDataSetChanged();
        }
    }

    class Aleph0 extends BaseAdapter {
        int count = lineSize; /* starting amount */

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getItem(int pos) {
            return pos;
        }

        public long getItemId(int pos) {
            return pos;
        }

        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = null;
            //布局不变，数据变

            try {
                // 如果缓存为空，我们生成新的布局作为1个item
                if (convertView == null) {
                    Log.i("info:", "没有缓存，重新生成" + position);
                    LayoutInflater inflater = MedicalEncyclopediaActivity.this.getLayoutInflater();
                    //因为getView()返回的对象，adapter会自动赋给ListView
                    view = inflater.inflate(R.layout.disy_object_item, null);
                } else {
                    Log.i("info:", "有缓存，不需要重新生成" + position);
                    view = convertView;
                }

                TextView tv_disy_name = (TextView) view.findViewById(R.id.tv_disy_name);
                TextView tv_disy_intro = (TextView) view.findViewById(R.id.tv_disy_intro);
                if (diseaseList != null) {
                    Disease disease = diseaseList.get(position);
                    tv_disy_name.setText(disease.getDisease_name());
                    tv_disy_intro.setText(disease.getDisease_intro());
                } else {
                    tv_disy_name.setText(position);
                }
            } catch (Exception e) {
                loadInfo.setText("到底了...");
                LayoutInflater inflater = MedicalEncyclopediaActivity.this.getLayoutInflater();
                return inflater.inflate(R.layout.disy_object_item, null);
            }
            return view;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isShutDown) {
            isShutDown = true;
            Toast.makeText(getApplicationContext(), "再按一次将退出医疗百科", Toast.LENGTH_SHORT).show();
            //利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            MedicalEncyclopediaActivity.super.onBackPressed();
            MedicalEncyclopediaActivity.this.finish();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //处理导航视图项目单击此处。
        int id = item.getItemId();

        String oldBuWei = buWei;

        partService = new PartService(myContext);
        switch (id) {
            case R.id.bw_toubu:
                buWei = "头部";
                break;
            case R.id.bw_jingbu:
                buWei = "颈部";
                break;
            case R.id.bw_xiongbu:
                buWei = "胸部";
                break;
            case R.id.bw_fubu:
                buWei = "腹部";
                break;
            case R.id.bw_yaobu:
                buWei = "腰部";
                break;
            case R.id.bw_huiyinbu:
                buWei = "会阴部";
                break;
            case R.id.bw_nanxsz:
                buWei = "男性生殖";
                break;
            case R.id.bw_nvxsz:
                buWei = "女性生殖";
                break;
            case R.id.bw_quanshen:
                buWei = "全身";
                break;
            case R.id.bw_shangzhi:
                buWei = "上肢";
                break;
            case R.id.bw_xiazhi:
                buWei = "下肢";
                break;
            case R.id.bw_penqiang:
                buWei = "盆腔";
                break;
            case R.id.bw_tunbu:
                buWei = "臀部";
                break;
            case R.id.bw_gu:
                buWei = "骨";
                break;
            case R.id.bw_xinli:
                buWei = "心理";
                break;
            case R.id.bw_qita:
                buWei = "其他";
                break;
            case R.id.nav_send:
                buWei = "";
                break;
        }

        if (!oldBuWei.equals(buWei)) {
            //            diseaseList.clear();

            if (!TextUtils.isEmpty(buWei)) {
                setTitle("医疗百科    类别：" + buWei);
                //            Part part = partService.getPartByName(buWei);
                //            if (part != null) {
                //                List<String> allPartOrganList = part.allPartOrgan();
                //                for (String string : allPartOrganList) {
                //
                //                }
                //            }
                msg = buWei;
            } else {
                setTitle("医疗百科    类别：" + "不限");
                msg = "";
            }

            showData();//刷新页面数据

            baseAdapter.notifyDataSetChanged();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}

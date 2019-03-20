package com.example.wangji.changemax.activity.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.activity.map.customize.MyBusOverLay;
import com.example.wangji.changemax.activity.map.customize.MyOverLay;
import com.example.wangji.changemax.util.overlay_util.DrivingRouteOverlay;
import com.example.wangji.changemax.util.overlay_util.WalkingRouteOverlay;
import com.example.wangji.changemax.util.permissions.PermissionBaseActivity;
import com.example.wangji.changemax.util.permissions.RequestPermissionCallBack;
import com.example.wangji.changemax.util.plugin_util.IosPopupWindow;


/**
 * Created by WangJi.
 */

public class MapActivity extends PermissionBaseActivity {
    private static final String TAG = "MapActivity";

    private Context myContext = MapActivity.this;
    private static MapActivity mapActivity;


    private MapView mMapView = null;    //百度地图控件
    private BaiduMap mBaiduMap = null;    //百度地图对象

    private PoiSearch poiSearch;//Poi搜索对象
    private PoiSearch busPoiSearch;//公交地铁的Poi搜索对象
    private BusLineSearch busLineSearch;//公交检索对象
    private RoutePlanSearch routePlanSearch;//路线规划

    private Button locCurplaceBtn;    //按钮 定位当前位置
    private Button btn_range_search, btn_center_search;
    private Button btn_walk_route, btn_drive_route;
    private EditText et_around, et_start;

    //当前位置经纬度
    private double latitude;
    private double longitude;
    private boolean isFirstLoc = true;    //是否首次定位
    private LocationClient mLocClient;    //定位SDK的核心类
    private LocationMode mCurrentMode;    //定位图层显示模式 (普通-跟随-罗盘)
    private BitmapDescriptor mCurrentMarker = null;    //定位图标描述
    public MyLocationListenner locListener = new MyLocationListenner();    //定位SDK监听函数

    /**
     * 需要进行检测的权限数组
     */
    protected String[] myPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    //定义一个变量，来标识是否关闭
    private static boolean isShutDown = false;

    //集合,用于显示在view中
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isShutDown = false;
        }
    };


    private View view;//布局对象

    public MapActivity() {
        mapActivity = this;
    }

    public static MapActivity getMapActivity() {
        return mapActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        init();//初始化
        listener();

        getPermission();
    }//end onCreate()


    private void init() {
        initView();
        initBaiduMap();
    }

    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map_view);

        locCurplaceBtn = (Button) findViewById(R.id.btn_cur_place);

        btn_range_search = (Button) findViewById(R.id.btn_range_search);
        btn_center_search = (Button) findViewById(R.id.btn_center_search);

        btn_walk_route = (Button) findViewById(R.id.btn_walk_route);
        btn_drive_route = (Button) findViewById(R.id.btn_drive_route);

        et_around = (EditText) findViewById(R.id.et_around);

        et_start = (EditText) findViewById(R.id.et_start);
        LayoutInflater li = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.activity_map, null);
    }

    private void initBaiduMap() {
        //1-20级 20级室内地图
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        //地图类型为：普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //定位初始化
        //注意: 实例化定位服务 LocationClient类必须在主线程中声明 并注册定位监听接口
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(locListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);              //打开GPS
        option.setCoorType("bd09ll");        //设置坐标类型
        option.setScanSpan(5000);            //设置发起定位请求的间隔时间为5000ms
        mLocClient.setLocOption(option);     //设置定位参数
        mLocClient.start();                  //调用此方法开始定位

        //搜索对象的创建
        poiSearch = PoiSearch.newInstance();
        busPoiSearch = PoiSearch.newInstance();
        //设置Poi监听对象
        poiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);
        busPoiSearch.setOnGetPoiSearchResultListener(busPoiSearchResultListener);
    }

    private void getKeyWord() {
        Intent intent = getIntent();
        String keyWord = intent.getStringExtra("keyWord");

        if (!TextUtils.isEmpty(keyWord)) {
            et_around.setText(keyWord);
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            selectPoint(keyWord);
        }
    }

    private void listener() {
        busSearchMonitor();//公交检索及监听、回调结果
        routeMonitor();//路线规划及监听
        clickEventListener();//点击事件监听
    }

    //公交检索及监听、回调结果
    private void busSearchMonitor() {
        //公交检索对象
        busLineSearch = BusLineSearch.newInstance();
        //设置公交监听对象，公交检索得到结果后，里面的方法得到回调
        busLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                MyBusOverLay overlay = new MyBusOverLay(mBaiduMap, busPoiSearch);
                //设置数据,这里只需要一步，
                overlay.setData(busLineResult);
                //添加到地图
                overlay.addToMap();
                //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
                overlay.zoomToSpan();//计算工具
                //设置标记物的点击监听事件
                mBaiduMap.setOnMarkerClickListener(overlay);

            }
        });
    }

    //路线规划及监听
    private void routeMonitor() {
        //路线规划对象
        routePlanSearch = RoutePlanSearch.newInstance();
        //给路线规划添加监听
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            //步行路线结果回调
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                mBaiduMap.clear();
                if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    WalkingRouteOverlay walkingOverlay = new WalkingRouteOverlay(mBaiduMap);
                    walkingOverlay.setData(walkingRouteResult.getRouteLines().get(0));// 设置一条路线方案
                    walkingOverlay.addToMap();
                    walkingOverlay.zoomToSpan();
                    mBaiduMap.setOnMarkerClickListener(walkingOverlay);
                    Log.e("TAG", walkingOverlay.getOverlayOptions() + "");

                } else {
                    Toast.makeText(getBaseContext(), "搜不到！", Toast.LENGTH_SHORT).show();
                }
            }

            //换乘线结果回调
            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            //跨城公共交通路线结果回调
            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            //驾车路线结果回调
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                mBaiduMap.clear();//清除图标或路线
                if (drivingRouteResult == null
                        || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getBaseContext(), "抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            mBaiduMap);
                    drivingRouteOverlay.setData(drivingRouteResult.getRouteLines().get(1));// 设置一条驾车路线方案
                    mBaiduMap.setOnMarkerClickListener(drivingRouteOverlay);
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    int totalLine = drivingRouteResult.getRouteLines().size();
                    Toast.makeText(getBaseContext(),
                            "共查询出" + totalLine + "条符合条件的线路", Toast.LENGTH_LONG).show();

                    // 通过getTaxiInfo()可以得到很多关于打车的信息
                    Toast.makeText(getBaseContext(), "该路线打车总路程" + drivingRouteResult.getTaxiInfo()
                            .getDistance(), Toast.LENGTH_LONG).show();
                }
            }


            //室内路线规划回调
            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
            }

            // 骑行路线结果回调
            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            }
        });
    }

    //点击事件监听
    private void clickEventListener() {

        //Button 定位当前位置
        locCurplaceBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addMyLocation();
            }
        });

        btn_range_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = et_around.getText().toString();
                selectAround(keyWord);
            }
        });

        btn_center_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = et_around.getText().toString();
                selectPoint(keyWord);
            }
        });

        btn_walk_route.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = et_start.getText().toString();
                selectWalkingLine(start);
            }
        });

        btn_drive_route.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = et_start.getText().toString();
                selectDrivingLine(start);
            }
        });
    }

    //定位并添加标注
    private void addMyLocation() {
        //更新
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        mBaiduMap.clear();
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_me);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    //POI检索的监听对象
    OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {
        //获得POI的检索结果，一般检索数据都是在这里获取
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            mBaiduMap.clear();
            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {//如果没有错误
                MyOverLay overlay = new MyOverLay(mBaiduMap, poiSearch);
                //设置数据,这里只需要一步，
                overlay.setData(poiResult);
                //添加到地图
                overlay.addToMap();
                //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
                overlay.zoomToSpan();//计算工具
                //设置标记物的点击监听事件
                mBaiduMap.setOnMarkerClickListener(overlay);
                //                return;
            } else {
                Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
            }
        }

        //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getApplication(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                Log.e("WANGJI", poiDetailResult.getName() + ": " + poiDetailResult.getAddress());

                IosPopupWindow iosPopupWindow = new IosPopupWindow(MapActivity.this, poiDetailResult);
                iosPopupWindow.showAtScreenBottom(view);
            }
        }

        //获得POI室内检索结果
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };

    //公交路线的点的POI检索的监听对象
    OnGetPoiSearchResultListener busPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        //获得POI的检索结果，一般检索数据都是在这里获取
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            mBaiduMap.clear();//清除标志
            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {//如果没有错误
                //遍历所有数据
                for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                    //获取里面的数据对象
                    PoiInfo poiInfo = poiResult.getAllPoi().get(i);
                    //判断检索到的点的类型是不是公交路线或地铁路线
                    if (poiInfo.type == PoiInfo.POITYPE.BUS_LINE || poiInfo.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                        //发起公交检索
                        BusLineSearchOption busLineOptions = new BusLineSearchOption();
                        busLineOptions.city("九江").uid(poiInfo.uid);
                        busLineSearch.searchBusLine(busLineOptions);
                    }
                }
                return;
            } else {
                Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
            }
        }

        //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getApplication(), "抱歉，未找到结果",
                        Toast.LENGTH_SHORT).show();
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                Toast.makeText(getApplication(), poiDetailResult.getName() + ": "
                                + poiDetailResult.getAddress(),
                        Toast.LENGTH_LONG).show();
            }
        }

        //获得POI室内检索结果
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    //POI搜索，城市检索
    public void select(View view) {
        //        //获得Key
        //        String city = et_city.getText().toString();
        //        String key = et_key.getText().toString();
        //        //发起检索
        //        PoiCitySearchOption poiCity = new PoiCitySearchOption();
        //        poiCity.keyword(key).city(city);
        //        poiSearch.searchInCity(poiCity);
    }

    //POI搜索，范围检索,
    public void selectAround(String keyWord) {
        //发起周边检索
        PoiBoundSearchOption boundSearchOption = new PoiBoundSearchOption();
        LatLng southwest = new LatLng(latitude - 0.02, longitude - 0.024);// 西南
        LatLng northeast = new LatLng(latitude + 0.02, longitude + 0.024);// 东北
        LatLngBounds bounds = new LatLngBounds.Builder().include(southwest)
                .include(northeast).build();// 得到一个地理范围对象
        boundSearchOption.bound(bounds);// 设置poi检索范围
        boundSearchOption.keyword(keyWord);// 检索关键字
        boundSearchOption.pageNum(1);//搜索一页
        boolean flag = poiSearch.searchInBound(boundSearchOption);// 发起poi范围检索请求
        Toast.makeText(getApplicationContext(), "范围检索：" + String.valueOf(flag), Toast.LENGTH_SHORT).show();
    }

    //POI搜索，周边检索,
    public void selectPoint(String keyWord) {
        LatLng point = new LatLng(latitude, longitude);
        //周边检索
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(point);
        nearbySearchOption.keyword(keyWord);
        nearbySearchOption.radius(3000);// 检索半径，单位是米
        nearbySearchOption.pageNum(1);//搜索一页
        boolean flag = poiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求
        Toast.makeText(getApplicationContext(), "周边检索：" + String.valueOf(flag), Toast.LENGTH_SHORT).show();
    }

    //公交路线搜索,步骤：
    //1.先发起一个poi检索，(新建一个Poi检索对象)
    //2.在poi的检索结果中发起公交搜索
    //3.在公交检索搜索结果中获取数据
    public void selectBusLine(String destination) {
        //城市内的公交、地铁路线检索
        PoiCitySearchOption poiCitySearchOptions = new PoiCitySearchOption();
        poiCitySearchOptions.city("九江").keyword(destination);//城市检索的数据设置
        busPoiSearch.searchInCity(poiCitySearchOptions);
    }

    //普通路线搜索,步行路线：
    public void selectWalkingLine(String destination) {
        LatLng point = new LatLng(latitude, longitude);
        //创建步行路线搜索对象
        WalkingRoutePlanOption walkingSearch = new WalkingRoutePlanOption();
        //设置节点对象，可以通过城市+关键字或者使用经纬度对象来设置
        PlanNode fromeNode = PlanNode.withLocation(point);
        PlanNode toNode = PlanNode.withCityNameAndPlaceName("九江", destination);
        walkingSearch.from(fromeNode).to(toNode);
        routePlanSearch.walkingSearch(walkingSearch);
    }


    //普通路线搜索,驾车路线：
    public void selectDrivingLine(String destination) {
        LatLng point = new LatLng(latitude, longitude);
        //获得关键字
        //创建驾车路线搜索对象
        DrivingRoutePlanOption drivingOptions = new DrivingRoutePlanOption();
        //设置节点对象，可以通过城市+关键字或者使用经纬度对象来设置
        PlanNode fromeNode = PlanNode.withLocation(point);
        PlanNode toNode = PlanNode.withCityNameAndPlaceName("九江", destination);
        drivingOptions.from(fromeNode).to(toNode);
        //drivingOptions.passBy(list);//设置路线经过的点，要放入一个PlanNode的集合对象
        drivingOptions.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_AVOID_JAM);//设置驾车策略，避免拥堵
        routePlanSearch.drivingSearch(drivingOptions);//发起驾车检索
    }


    /**
     * 定位SDK监听器 需添加locSDK jar和so文件
     */
    public class MyLocationListenner implements BDLocationListener {
        public void onReceivePoi(BDLocation location) {
        }

        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapview 销毁后不在处理新接收的位置
            if (location == null || mBaiduMap == null) {
                return;
            }
            //MyLocationData.Builder定位数据建造器
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            //设置定位数据
            mBaiduMap.setMyLocationData(locData);
            mCurrentMode = LocationMode.NORMAL;
            //获取经纬度
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //           Toast.makeText(getApplicationContext(), "实时经纬度：" + String.valueOf(latitude) + ", " + String.valueOf(longitude), Toast.LENGTH_SHORT).show();

            //第一次定位的时候，那地图中心点显示为定位到的位置
            if (isFirstLoc) {
                isFirstLoc = false;
                //地理坐标基本数据结构
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                //MapStatusUpdate描述地图将要发生的变化
                //MapStatusUpdateFactory生成地图将要反生的变化
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(loc);
                mBaiduMap.animateMapStatus(msu);
                //                Toast.makeText(getApplicationContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void jumpPage(Intent intent) {
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        }
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();                       //退出时销毁定位
        mBaiduMap.setMyLocationEnabled(false);   //关闭定位图层
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            shutDown();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void shutDown() {
        if (!isShutDown) {
            isShutDown = true;
            Toast.makeText(getApplicationContext(), "再按一次将退出周边搜索", Toast.LENGTH_SHORT).show();
            //利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            super.onBackPressed();
            MapActivity.this.finish();
        }
    }

    private void getPermission() {
        requestPermissions(this, myPermissions, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                Toast.makeText(MapActivity.this, "权限获取成功，执行下一步操作", Toast.LENGTH_SHORT).show();
                openGPSSettings();//判断GPS开启情况
            }

            @Override
            public void denied() {
                Toast.makeText(MapActivity.this, "部分权限获取失败，正常功能受到影响,2秒钟之后自动退出", Toast.LENGTH_LONG).show();
                //2秒钟之后自动退出
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MapActivity.super.onBackPressed();
                        MapActivity.this.finish();
                    }
                }, 2000);
            }
        });
    }

    private int GPS_REQUEST_CODE = 10;

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            getKeyWord();
        } else {
            //没有打开则弹出对话框
            new AlertDialog.Builder(this)
                    .setTitle("定位权限")
                    .setMessage("需要打开GPS功能")
                    // 拒绝, 退出应用
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(myContext, "GPS开启异常，无法实现周围搜索", Toast.LENGTH_LONG).show();
                                    MapActivity.this.finish();
                                }
                            })
                    .setPositiveButton("GPS设置",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转GPS设置界面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, GPS_REQUEST_CODE);
                                }
                            })
                    .setCancelable(false)
                    .show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
    }

}
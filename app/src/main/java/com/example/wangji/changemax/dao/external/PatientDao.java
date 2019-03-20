package com.example.wangji.changemax.dao.external;

import android.content.Context;

import com.example.wangji.changemax.model.external.Patient;
import com.example.wangji.changemax.util.sqlite_util.external.PatientDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */
public class PatientDao {
    private Context myContext;
    private PatientDatabaseUtil patientDatabaseUtil;

    public PatientDao(Context myContext) {
        this.myContext = myContext;
        patientDatabaseUtil = new PatientDatabaseUtil(myContext);
    }

    public void add(Patient patient) {
        patientDatabaseUtil.openDataBase();
        patientDatabaseUtil.insertData(patient);
        patientDatabaseUtil.closeDataBase();
    }

    public Patient read(String uid) {
        patientDatabaseUtil.openDataBase();
        uid = "'" + uid + "'";
        List<Patient> list = patientDatabaseUtil.queryData(uid);
        patientDatabaseUtil.closeDataBase();
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public List<Patient> readAll() {
        patientDatabaseUtil.openDataBase();
        List<Patient> list = patientDatabaseUtil.queryDataList();
        patientDatabaseUtil.closeDataBase();
        return list;
    }
}

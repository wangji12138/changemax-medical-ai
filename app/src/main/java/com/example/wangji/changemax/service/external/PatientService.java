package com.example.wangji.changemax.service.external;

import android.content.Context;

import com.example.wangji.changemax.dao.external.PatientDao;
import com.example.wangji.changemax.model.external.Patient;

import java.util.List;

/**
 * Created by WangJi.
 */
public class PatientService {
    private Context myContext;
    private PatientDao patientDao;

    public PatientService(Context myContext) {
        this.myContext = myContext;
        patientDao = new PatientDao(myContext);
    }

    public void add(Patient patient) {
        patientDao.add(patient);
    }


    public Patient read(String uid) {
        return patientDao.read(uid);
    }

    public List<Patient> readAll(){
        return patientDao.readAll();
    }
}

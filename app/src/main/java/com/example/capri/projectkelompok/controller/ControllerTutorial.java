package com.example.capri.projectkelompok.controller;

import android.util.Log;

import com.example.capri.projectkelompok.realm.RealmTutorial;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by capri on 5/22/2017.
 */

public class ControllerTutorial {
    private final Realm realm;
    private static ControllerTutorial instance;


    public ControllerTutorial(){realm = Realm.getDefaultInstance();}
    public Realm getRealm(){
        return realm;
    }

    public void insertData(final int idtutorial, final String judul, final String deskripsi, final String referensi){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmTutorial tutorial = realm.createObject(RealmTutorial.class, idtutorial);
                    tutorial.setJudul(judul);
                    tutorial.setDeskripsi(deskripsi);
                    tutorial.setReferensi(referensi);
                }
            });
        } catch (Exception e){
            Log.e("Error : ", e.getMessage());
        }
    }

    public void deleteData(){
        try{
            realm.beginTransaction();
            RealmResults<RealmTutorial> tutor = realm.where(RealmTutorial.class).findAll();
            tutor.deleteAllFromRealm();
            realm.commitTransaction();
        } catch (Exception e){
            Log.e("Error : ", e.getMessage());
        }
    }
}

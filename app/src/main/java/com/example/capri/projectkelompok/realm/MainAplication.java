package com.example.capri.projectkelompok.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by capri on 5/23/2017.
 */

public class MainAplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder().name("myrealm.realm").schemaVersion(1).deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(configuration);
    }
}

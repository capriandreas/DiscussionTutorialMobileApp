package com.example.capri.projectkelompok.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by capri on 5/22/2017.
 */

public class RealmTutorial extends RealmObject {

    @PrimaryKey
    private int idtutorial;

    private String judul;
    private String deskripsi;
    private String referensi;

    public RealmTutorial(){

    }

    public int getIdtutorial() {
        return idtutorial;
    }

    public void setIdtutorial(int idtutorial) {
        this.idtutorial = idtutorial;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getReferensi() {
        return referensi;
    }

    public void setReferensi(String referensi) {
        this.referensi = referensi;
    }
}

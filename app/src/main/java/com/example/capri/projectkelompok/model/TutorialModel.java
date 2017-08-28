package com.example.capri.projectkelompok.model;

/**
 * Created by capri on 5/22/2017.
 */

public class TutorialModel {

    private int idtutorial;
    private String judul;
    private String deskripsi;
    private String referensi;

    public TutorialModel(){}

    public TutorialModel(int idtutorial, String judul, String deskripsi, String referensi) {
        this.idtutorial = idtutorial;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.referensi = referensi;
    }

    public int getIdTutorial() {
        return idtutorial;
    }

    public void setIdTutorial(int idTutorial) {
        this.idtutorial = idTutorial;
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

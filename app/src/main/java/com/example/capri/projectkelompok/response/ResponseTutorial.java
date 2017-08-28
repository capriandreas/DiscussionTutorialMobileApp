package com.example.capri.projectkelompok.response;

import com.example.capri.projectkelompok.model.TutorialModel;

import java.util.List;

/**
 * Created by capri on 5/22/2017.
 */

public class ResponseTutorial {

    private String error;
    private List<TutorialModel> tutorial;
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public List<TutorialModel> getTutorial() {
        return tutorial;
    }
    public void setHasil(List<TutorialModel> tutorial) {
        this.tutorial = tutorial;
    }
}

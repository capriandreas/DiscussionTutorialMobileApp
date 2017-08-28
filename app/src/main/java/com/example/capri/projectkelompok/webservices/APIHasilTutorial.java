package com.example.capri.projectkelompok.webservices;

import com.example.capri.projectkelompok.response.ResponseTutorial;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by capri on 5/23/2017.
 */

public interface APIHasilTutorial {


    @POST("/tugaskelompok/pam/tutorial")
    Call<ResponseTutorial> getTutorial();
}

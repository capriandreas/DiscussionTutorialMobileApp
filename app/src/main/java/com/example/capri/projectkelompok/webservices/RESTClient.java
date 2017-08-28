package com.example.capri.projectkelompok.webservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by capri on 5/23/2017.
 */

public class RESTClient {
    private static APIHasilTutorial REST_CLIENT;

    static {
        setupRestClient();
    }

    private RESTClient() {
    }

    public static APIHasilTutorial get(){
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        REST_CLIENT = retrofit.create(APIHasilTutorial.class);
    }
}

package com.example.capri.projectkelompok.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.capri.projectkelompok.R;
import com.example.capri.projectkelompok.adapter.TutorialAdapter;
import com.example.capri.projectkelompok.controller.ControllerTutorial;
import com.example.capri.projectkelompok.model.TutorialModel;
import com.example.capri.projectkelompok.realm.RealmTutorial;
import com.example.capri.projectkelompok.response.ResponseTutorial;
import com.example.capri.projectkelompok.webservices.APIHasilTutorial;
import com.example.capri.projectkelompok.webservices.RESTClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TutorialActivity extends AppCompatActivity {

    private ControllerTutorial ct;
    private Realm realm;
    RecyclerView recyclerView = null;
    private TutorialAdapter tutorialAdapter;
    private List<TutorialModel> mdlTutorial;
    APIHasilTutorial apiHasilTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mdlTutorial = new ArrayList<TutorialModel>();
        ct = new ControllerTutorial();
        ct.deleteData();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        try{
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        getAllDataTutorial();
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        RealmResults<RealmTutorial> tutorial = realm.where(RealmTutorial.class).findAll();

        tutorialAdapter = new TutorialAdapter(tutorial, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tutorialAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void getAllDataTutorial(){
        Callback<ResponseTutorial> getTutorials = new Callback<ResponseTutorial>() {
            @Override
            public void onResponse(Call<ResponseTutorial> call, Response<ResponseTutorial> response) {
                if(response.isSuccessful()){
                    List<TutorialModel> dafTutorial = response.body().getTutorial();
                    int jumlah_data = response.body().getTutorial().size();
                    if(jumlah_data > 0){
                        for(int j = 0; j<jumlah_data; j++){
                            TutorialModel tutorial = dafTutorial.get(j);
                            ct.insertData(tutorial.getIdTutorial(), tutorial.getJudul(), tutorial.getDeskripsi(), tutorial.getReferensi());
                        }
                    }
                    else {
                        Toast.makeText(TutorialActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTutorial> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Akses ke server gagal "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        apiHasilTutorial = RESTClient.get();
        Call<ResponseTutorial> call = apiHasilTutorial.getTutorial();
        call.enqueue(getTutorials);
    }

}

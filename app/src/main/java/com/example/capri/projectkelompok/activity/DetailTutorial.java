package com.example.capri.projectkelompok.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capri.projectkelompok.R;

public class DetailTutorial extends AppCompatActivity {

    TextView mDetailTutorialTitle, mDetailTutorialDescription, mDetailTutorialReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tutorial);
        initializeWidgets();

        Intent intent = getIntent();


        mDetailTutorialTitle.setText(intent.getStringExtra("judul"));
        mDetailTutorialDescription.setText(intent.getStringExtra("deskripsi"));
        mDetailTutorialReference.setText("By : " + intent.getStringExtra("referensi"));
        Toast.makeText(this, "Judul : " + intent.getStringExtra("judul"), Toast.LENGTH_SHORT).show();

    }

    public void initializeWidgets(){
        mDetailTutorialTitle = (TextView)findViewById(R.id.tvJudulDetail);
        mDetailTutorialDescription = (TextView)findViewById(R.id.tvDeskripsiDetail);
        mDetailTutorialReference = (TextView)findViewById(R.id.tvReferensiDetail);
    }
}

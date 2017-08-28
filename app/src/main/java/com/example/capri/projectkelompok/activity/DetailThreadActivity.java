package com.example.capri.projectkelompok.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capri.projectkelompok.R;
import com.squareup.picasso.Picasso;

public class DetailThreadActivity extends AppCompatActivity {

    TextView mDetailThreadTitle, mDetailThreadDescription, mDetailThreadUsername;
    Context context;
    ImageView mImgButtonDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_thread);
        initializeWidgets();

        Intent intent = getIntent();

        mDetailThreadTitle.setText(intent.getStringExtra("title"));
        mDetailThreadDescription.setText(intent.getStringExtra("description"));
        mDetailThreadUsername.setText("By : " + intent.getStringExtra("username"));
        //Toast.makeText(this, "Images : " + intent.getStringExtra("image"), Toast.LENGTH_SHORT).show();

        Picasso.with(context).load(intent.getStringExtra("image")).into(mImgButtonDetail);
    }

    public void initializeWidgets(){
        mDetailThreadTitle = (TextView)findViewById(R.id.titleThreadItemDetail);
        mDetailThreadDescription = (TextView)findViewById(R.id.descriptionThreadItemDetail);
        mDetailThreadUsername = (TextView)findViewById(R.id.userThreadItemDetail);
        mImgButtonDetail = (ImageView) findViewById(R.id.imageThreadItemDetail);
    }
}

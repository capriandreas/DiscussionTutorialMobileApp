package com.example.capri.projectkelompok;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.capri.projectkelompok.activity.AnalyticsTrackers;
import com.example.capri.projectkelompok.activity.LoginActivity;
import com.example.capri.projectkelompok.activity.RegisterActivity;
import com.example.capri.projectkelompok.activity.SettingsActivity;
import com.example.capri.projectkelompok.activity.SetupActivity;
import com.example.capri.projectkelompok.activity.ThreadActivity;
import com.example.capri.projectkelompok.activity.TutorialActivity;
import com.example.capri.projectkelompok.adapter.ThreadRecyclerAdapter;
import com.example.capri.projectkelompok.firebase.Constants;
import com.example.capri.projectkelompok.fragment.ThreadFragment;
import com.example.capri.projectkelompok.model.ThreadModel;
import com.github.clans.fab.FloatingActionMenu;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = MainActivity.class
            .getSimpleName();

    private static MainActivity mInstance;

    private FirebaseDatabase database;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUsers;
    private List<ThreadModel> list;
    private RecyclerView recyclerViewThread;
    private ProgressDialog progressDialog;

    private DatabaseReference mDatabaseLike;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;

    private AdView mAdView;

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mInstance = this;

       //AnalyticsTrackers.initialize(this);
       // AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Thread");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        //mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");

        //mDatabaseLike.keepSynced(true);
        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        checkUserExist();
        mAuth.addAuthStateListener(mAuthListener);

        recyclerViewThread = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerViewThread.setHasFixedSize(true);
        recyclerViewThread.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        list = new ArrayList<>();

        progressDialog.setMessage("Sabar ya gan ... ");
        progressDialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //database = FirebaseDatabase.getInstance();
        //mDatabase = database.getReference("thread");
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("thread");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot threadSnapshot : dataSnapshot.getChildren()) {
                    ThreadModel threads = threadSnapshot.getValue(ThreadModel.class);
                    list.add(threads);
                    Log.e("Data : ", threads.toString());
                }
                adapter = new ThreadRecyclerAdapter(getApplicationContext(), list);

                recyclerViewThread.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Hello", "Failed to read value ...", databaseError.toException());
                progressDialog.dismiss();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.fab);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent threadIntent = new Intent(MainActivity.this, ThreadActivity.class);
                threadIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(threadIntent);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent tutorialIntent = new Intent(MainActivity.this, TutorialActivity.class);
                tutorialIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tutorialIntent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    public void checkUserExist() {

        if (mAuth.getCurrentUser() != null) {

            final String user_id = mAuth.getCurrentUser().getUid();

            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild(user_id)) {
                        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupIntent);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            actionLogout();
        }

        if(id == R.id.action_add){
            startActivity(new Intent(MainActivity.this, ThreadActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void actionLogout(){
        mAuth.signOut();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

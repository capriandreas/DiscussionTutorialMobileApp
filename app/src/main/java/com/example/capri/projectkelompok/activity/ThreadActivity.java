package com.example.capri.projectkelompok.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.capri.projectkelompok.MainActivity;
import com.example.capri.projectkelompok.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ThreadActivity extends AppCompatActivity {

    private ImageButton imageBtnSelect;

    private EditText editTextThreadTitle;
    private EditText editTextThreadDescription;
    private Button btnSubmitThread;

    private Uri imageUri = null;

    private static final int GALLERY_REQUEST = 1;

    private StorageReference mStorage;

    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initializeWidgets();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Thread");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mProgressDialog = new ProgressDialog(this);

        imageBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        btnSubmitThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostThread();
            }
        });
    }

    public void startPostThread(){
        mProgressDialog.setMessage("Posting your thread ...");
        mProgressDialog.show();

        final String title_value = editTextThreadTitle.getText().toString().trim();
        final String description_value = editTextThreadDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(title_value) && !TextUtils.isEmpty(description_value) && imageUri != null){
            StorageReference filepath = mStorage.child("Thread Images").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    final DatabaseReference newThread = mDatabase.push();

                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newThread.child("title").setValue(title_value);
                            newThread.child("description").setValue(description_value);
                            newThread.child("image").setValue(downloadUrl.toString());
                            newThread.child("uid").setValue(mCurrentUser.getUid());
                            newThread.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(ThreadActivity.this, MainActivity.class));
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mProgressDialog.dismiss();

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageBtnSelect.setImageURI(imageUri);
        }
    }

    public void initializeWidgets(){
        imageBtnSelect = (ImageButton)findViewById(R.id.imgSelect);
        editTextThreadTitle = (EditText)findViewById(R.id.editAddTitle);
        editTextThreadDescription = (EditText)findViewById(R.id.editAddDescription);
        btnSubmitThread = (Button)findViewById(R.id.btnAddThread);
    }
}

package com.example.capri.projectkelompok.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capri.projectkelompok.MainActivity;
import com.example.capri.projectkelompok.R;
import com.example.capri.projectkelompok.activity.DetailThreadActivity;
import com.example.capri.projectkelompok.model.ThreadModel;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by capri on 5/19/2017.
 */

public class ThreadRecyclerAdapter extends RecyclerView.Adapter<ThreadRecyclerAdapter.MyHolder>{

    List<ThreadModel> list;
    Context context;
    private boolean mProcessLike = false;
    private DatabaseReference mDatabaseLike;
    private DatabaseReference mDatabaseThread;


    public ThreadRecyclerAdapter(Context context, List<ThreadModel> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread_row, parent,false);
        MyHolder viewHolder = new MyHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        final ThreadModel myList = list.get(position);

        holder.thread_title.setText(myList.getTitle());
        holder.thread_description.setText(myList.getDescription());
        holder.thread_username.setText("By : "+myList.getUsername());
        Picasso.with(context).load(myList.getImage()).into(holder.thread_image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailThreadActivity.class);
                intent.putExtra("title", myList.getTitle());
                intent.putExtra("description", myList.getDescription());
                intent.putExtra("username", myList.getUsername());
                intent.putExtra("image", myList.getImage());
                v.getContext().startActivity(intent);
            }
        });


//        holder.mLikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mProcessLike = true;
//
//                if(mProcessLike){
//                    mDatabaseLike.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.child(post_))
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public TextView thread_title, thread_description, thread_username;
        public ImageView thread_image;
        public ImageButton mLikeBtn;
        public CardView cardView;

        public MyHolder(View itemView) {
            super(itemView);
            thread_title = (TextView) itemView.findViewById(R.id.titleThreadItem);
            thread_description = (TextView) itemView.findViewById(R.id.descriptionThreadItem);
            thread_image = (ImageView)itemView.findViewById(R.id.imageThreadItem);
            thread_username = (TextView)itemView.findViewById(R.id.userThreadItem);
            cardView = (CardView)itemView.findViewById(R.id.card_view_thread);
        }
    }

}

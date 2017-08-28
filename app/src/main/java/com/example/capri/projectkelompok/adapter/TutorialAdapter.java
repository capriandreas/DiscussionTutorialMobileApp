package com.example.capri.projectkelompok.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capri.projectkelompok.R;
import com.example.capri.projectkelompok.activity.DetailThreadActivity;
import com.example.capri.projectkelompok.activity.DetailTutorial;
import com.example.capri.projectkelompok.realm.RealmTutorial;
import io.realm.RealmRecyclerViewAdapter;

import io.realm.OrderedRealmCollection;

/**
 * Created by capri on 5/23/2017.
 */
public class TutorialAdapter extends RealmRecyclerViewAdapter<RealmTutorial, TutorialAdapter.ViewHolder> {
    Context mContext;

    public TutorialAdapter(OrderedRealmCollection<RealmTutorial> data, Context mContext) {
        super(data, true);
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutorial, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RealmTutorial obj = getItem(position);
        holder.judul.setText(obj.getJudul());
//        holder.referensi.setT
//        holder.deskripsi.setText(obj.getDeskripsi());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailTutorial.class);
                intent.putExtra("idtutorial", obj.getIdtutorial());
                intent.putExtra("judul", obj.getJudul());
                intent.putExtra("deskripsi", obj.getDeskripsi());
                intent.putExtra("referensi", obj.getReferensi());
                view.getContext().startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView card_image;
        public TextView judul, deskripsi, referensi;
        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            card_image = (ImageView) itemView.findViewById(R.id.image_background);
            judul = (TextView) itemView.findViewById(R.id.tvJudul1);
            card_view = (CardView) itemView.findViewById(R.id.card_view_pengumuman);
//            deskripsi = (TextView)itemView.findViewById(R.id.tvDeskripsi1);
//            referensi = (TextView)itemView.findViewById(R.id.tvOleh1);
        }
    }
}

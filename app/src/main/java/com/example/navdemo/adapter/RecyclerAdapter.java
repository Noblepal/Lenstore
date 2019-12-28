package com.example.navdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdemo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder> {
    private Context context;
    private ArrayList<Integer> galleryList;

    public RecyclerAdapter(Context context, ArrayList<Integer> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_recycler_item,
                viewGroup, false);
        return new MyViewholder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewholder holder, int position) {
        Picasso.get().load(galleryList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}

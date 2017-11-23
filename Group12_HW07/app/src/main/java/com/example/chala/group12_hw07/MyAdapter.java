package com.example.chala.group12_hw07;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.text.TextUtils.substring;

/**
 * Created by chala on 3/10/2017.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    public ArrayList<Podcast> mDataset= new ArrayList<Podcast>();
    private static MyClickListener myClickListener;
    static LinearLayout md;
    static ImageView v1;


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubDate;
        ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            image = (ImageView) itemView.findViewById(R.id.img);
            Log.i(LOG_TAG, "Adding Listener");
            v1=(ImageView) itemView.findViewById(R.id.playButton);

            md=(LinearLayout) MainActivity.md;
            v1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","der");
                    md.setVisibility(View.VISIBLE);

                }
            });

            //itemView.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }*/
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyAdapter(ArrayList<Podcast> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.pubDate.setText("posted: "+substring(mDataset.get(position).getPdate(),0,16));
        Picasso.with(holder.image.getContext())
                .load(mDataset.get(position).getImage())
                .into(holder.image);
    }

    public void addItem(Podcast dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
package com.example.chala.group12_hw07;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chala on 3/11/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Podcast> mData = new ArrayList<Podcast>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    static LinearLayout md;

    // data is passed into the constructor
    /*public MyRecyclerViewAdapter(Context context, ArrayList<Podcast> data) {
        this.mInflater = LayoutInflater.from(context);

    }*/

    public MyRecyclerViewAdapter(ArrayList<Podcast> myPodsArrayList) {
        this.mData = myPodsArrayList;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextView.setText(mData.get(position).getTitle());
        Picasso.with(holder.img.getContext())
                .load(mData.get(position).getImage())
                .into(holder.img);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.titleR);
            img=(ImageView) itemView.findViewById(R.id.imageR);
            itemView.setOnClickListener(this);

            md=(LinearLayout) MainActivity.md;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","der");
                    md.setVisibility(View.VISIBLE);

                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /*// convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

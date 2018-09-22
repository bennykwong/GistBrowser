package com.bkwong.gistbrowserapp.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.models.Gist;

import java.util.ArrayList;

public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CustomAdapter.onItemClickListener mListener;

    TextView textViewName;
    TextView textViewDescription;
    ImageView imageViewIcon;
    TextView textViewFileName;
    ArrayList<Gist> dataSet;

    public RecycleViewHolder(View itemView, CustomAdapter.onItemClickListener listener, ArrayList<Gist> data) {
        super(itemView);
        this.mListener = listener;
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        this.textViewFileName = (TextView) itemView.findViewById(R.id.textViewFileName);
        this.dataSet = data;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClickListener(itemView, getAdapterPosition(), dataSet.get(getAdapterPosition()));
    }
}


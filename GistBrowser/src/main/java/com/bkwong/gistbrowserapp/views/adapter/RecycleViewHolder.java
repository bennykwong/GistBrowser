package com.bkwong.gistbrowserapp.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkwong.gistbrowserapp.R;

public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private CustomAdapter.onItemClickListener mListener;

    TextView textViewName;
    TextView textViewDescription;
    ImageView imageViewIcon;
    TextView textViewFileName;


    public RecycleViewHolder(View itemView) {
        super(itemView);
        this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        this.textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        this.textViewFileName = (TextView) itemView.findViewById(R.id.textViewFileName);
    }

    @Override
    public void onClick(View v) {

    }
}


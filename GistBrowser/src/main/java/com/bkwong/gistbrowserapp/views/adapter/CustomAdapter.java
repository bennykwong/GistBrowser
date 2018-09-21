package com.bkwong.gistbrowserapp.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.models.File;
import com.bkwong.gistbrowserapp.models.Gist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Gist> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewDescription;
        ImageView imageViewIcon;
        TextView textViewFileName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.textViewFileName = (TextView) itemView.findViewById(R.id.textViewFileName);
        }
    }

    public CustomAdapter(ArrayList<Gist> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

//        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewDescription = holder.textViewDescription;
        ImageView imageView = holder.imageViewIcon;
        TextView textViewFileName = holder.textViewFileName;

        Map.Entry<String, File> next = dataSet.get(listPosition).getAdditionalProperties().entrySet().iterator().next();

        textViewName.setText(dataSet.get(listPosition).getOwner().getLogin());
        textViewDescription.setText(dataSet.get(listPosition).getDescription());
        textViewFileName.setText(next.getValue().getFileName());
        Picasso.get().load(dataSet.get(listPosition).getOwner().getAvatar_url()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

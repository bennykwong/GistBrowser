package com.bkwong.gistbrowserapp.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;
import java.util.Map;


public class CustomAdapter extends RecyclerView.Adapter<RecycleViewHolder> implements View.OnClickListener {

    //    private static final String TAG = CustomAdapter.class.getSimpleName();
    private static final String TAG = "benny";

    private static ArrayList<Gist> dataSet;
    private Context context;
    private static onItemClickListener mItemClickListener;


    //constructor
    public CustomAdapter(Context context){
        this.context = context;
        dataSet = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        RecycleViewHolder myViewHolder = new RecycleViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewDescription = holder.textViewDescription;
        ImageView imageView = holder.imageViewIcon;
        TextView textViewFileName = holder.textViewFileName;

        Map.Entry<String, File> next = dataSet.get(listPosition).getAdditionalProperties().entrySet().iterator().next();

        textViewName.setText(dataSet.get(listPosition).getOwner().getUsernme());
        textViewDescription.setText(dataSet.get(listPosition).getDescription());
        textViewFileName.setText(next.getValue().getFileName());
        Picasso.get().load(dataSet.get(listPosition).getOwner().getAvatar_url()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    /*
     * Custom methods to load/add/clear gists
     */

    public void addGist(Gist gist) {
        dataSet.add(gist);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void addAllGist(List<Gist> gistList) {
        for (Gist gist : gistList) {
            addGist(gist);
        }
    }

    public void remove(Gist city) {
        int position = dataSet.indexOf(city);
        if (position > -1) {
            dataSet.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Gist getItem(int position) {
        return dataSet.get(position);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void setOnItemClickListener(onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClickListener(View view, int position, Gist myData);
    }

}



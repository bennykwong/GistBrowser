package com.bkwong.gistbrowserapp.views.adapter;

import android.content.Context;
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
import java.util.List;
import java.util.Map;


public class CustomAdapter extends RecyclerView.Adapter<RecycleViewHolder> implements View.OnClickListener {

    private static final String TAG = CustomAdapter.class.getSimpleName();

    private static ArrayList<Gist> dataSet;
    private Context context;
    private static onItemClickListener mItemClickListener;

    public CustomAdapter(Context context, onItemClickListener listener){
        this.context = context;
        mItemClickListener = listener;
        dataSet = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        return new RecycleViewHolder(view, mItemClickListener, dataSet);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewDescription = holder.textViewDescription;
        ImageView imageView = holder.imageViewIcon;
        TextView textViewFileName = holder.textViewFileName;
        Map.Entry<String, File> next = dataSet.get(listPosition).getAdditionalProperties().entrySet().iterator().next();
        String fileNames = "";

//        for(File file : dataSet.get(listPosition).getAdditionalProperties().values()) {
//            if(fileNames.isEmpty()) {
//                fileNames = file.getFileName();
//            } else {
//                fileNames = fileNames + "\n" + file.getFileName();
//            }
//        }

        if (holder instanceof RecycleViewHolder) {
            textViewName.setText(dataSet.get(listPosition).getOwner().getUsernme());
            textViewDescription.setText(dataSet.get(listPosition).getDescription());
            textViewFileName.setText(next.getValue().getFileName());
//            textViewFileName.setText(fileNames);
            Picasso.get().load(dataSet.get(listPosition).getOwner().getAvatar_url()).into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    /*
     * Custom methods to load/add/clear gists
     */

    private void addGist(Gist gist) {
        dataSet.add(gist);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void addAllGist(List<Gist> gistList) {
        for (Gist gist : gistList) {
            addGist(gist);
        }
    }

    private void remove(Gist city) {
        int position = dataSet.indexOf(city);
        if (position > -1) {
            dataSet.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Gist getItem(int position) {
        return dataSet.get(position);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public interface onItemClickListener {
        void onItemClickListener(View view, int position, Gist data);
    }

}



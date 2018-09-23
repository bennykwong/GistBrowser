package com.bkwong.gistbrowserapp.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bkwong.gistbrowserapp.GistBrowserApplication;
import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.models.File;
import com.bkwong.gistbrowserapp.models.Gist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailScreenActivity extends BaseActivity {

    Gist gistData;
    ImageView avatar;
    TextView userName;
    TextView description;
    ListView fileListView;
    ArrayList<String> fileArray = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gistData = getIntent().getExtras().getParcelable("gistData");
        setContentView(R.layout.detail_screen_layout);

        avatar = (ImageView) findViewById(R.id.detail_screen_avatar);
        userName = (TextView) findViewById(R.id.detail_screen_username);
        description = (TextView) findViewById(R.id.detail_screen_description);
        fileListView = (ListView) findViewById(R.id.detail_screen_file_list);

        Picasso.get().load(gistData.getOwner().getAvatar_url()).into(avatar);
        userName.setText(gistData.getOwner().getUsernme());
        description.setText(gistData.getDescription());

        for (File file : gistData.getAdditionalProperties().values()) {
            fileArray.add(file.getFileName());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.detail_screen_list_view, fileArray);
        fileListView.setAdapter(adapter);

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sourceCodeActivity = new Intent(GistBrowserApplication.getAppContext(), SourceCodeActivity.class);
                sourceCodeActivity.putExtra("fileData", gistData.getAdditionalProperties().get((String) parent.getItemAtPosition(position)));
                startActivity(sourceCodeActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

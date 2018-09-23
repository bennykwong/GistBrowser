package com.bkwong.gistbrowserapp.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkwong.gistbrowserapp.R;
import com.bkwong.gistbrowserapp.models.Gist;
import com.squareup.picasso.Picasso;

public class DetailScreenActivity extends BaseActivity {

    Gist gistData;
    ImageView avatar;
    TextView userName;
    TextView description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gistData = getIntent().getExtras().getParcelable("gistData");
        setContentView(R.layout.detail_screen_layout);

        avatar = (ImageView) findViewById(R.id.detail_screen_avatar);
        userName = (TextView) findViewById(R.id.detail_screen_username);
        description = (TextView) findViewById(R.id.detail_screen_description);

        Picasso.get().load(gistData.getOwner().getAvatar_url()).into(avatar);
        userName.setText(gistData.getOwner().getUsernme());
        description.setText(gistData.getDescription());
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

package tripleh.lmh.farmerguideadmin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import tripleh.lmh.farmerguideadmin.R;
import tripleh.lmh.farmerguideadmin.model.Post;

public class postshowdetail extends AppCompatActivity {
    ImageView img;
    TextView info;
    TextView title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView((int) R.layout.post_showdetail);
        Post u = (Post) getIntent().getSerializableExtra("post");
        this.title = (TextView) findViewById(R.id.title);
        this.info = (TextView) findViewById(R.id.info);
        this.img = (ImageView) findViewById(R.id.img);
        this.title.setText(u.getTitle());
        this.info.setText(u.getInfo());
        RequestOptions placeholderRequest = new RequestOptions();
        placeholderRequest.placeholder(R.drawable.loading);
        Glide.with(getApplicationContext()).setDefaultRequestOptions(placeholderRequest).load(u.getUrl()).into(this.img);
    }
}

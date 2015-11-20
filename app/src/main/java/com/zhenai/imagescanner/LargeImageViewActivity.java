package com.zhenai.imagescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

public class LargeImageViewActivity extends AppCompatActivity {

    private String imgPath;
    private ImageView largeImg;
    private PhotoViewAttacher attacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);
        initView();
    }

    private void initView() {
        largeImg=(ImageView)findViewById(R.id.largImg);

        imgPath = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        Glide.with(this)
                .load(imgPath)
                .fitCenter()
                .into(largeImg);
        attacher=new PhotoViewAttacher(largeImg);

    }


}

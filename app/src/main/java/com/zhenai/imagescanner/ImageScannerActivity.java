package com.zhenai.imagescanner;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/10/29.
 */
public class ImageScannerActivity extends Activity{

    private String imageNameKey;

    private RecyclerView imageRecyclerView;

    private List<ImageInfo> images;
    private ImageDetailListAdapter imageAdapter;


    String [] columns=new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media.TITLE,MediaStore.Images.Media.DISPLAY_NAME};

    String [] where=null;

    private int fileColumn;
    private int titleColumn;
    private int displayColumn;

    Cursor cursor;




    ViewPager imageViewPager;
    private ImageViewPagePagerAdapter pageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);
        imageNameKey=getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        if(imageNameKey!=null){
            imageNameKey=imageNameKey.substring(0,1);
            where=new String[]{imageNameKey};
        }

        initData();
        intitView();
    }

    private void intitView() {
        imageRecyclerView = (RecyclerView)findViewById(R.id.imge_list);
        imageRecyclerView.setLayoutManager(new android.support.v7.widget.StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        imageRecyclerView.addItemDecoration(new MainActivity.SpaceItemDecoration(2));
        imageRecyclerView.setAdapter(imageAdapter);



        imageViewPager=(ViewPager)findViewById(R.id.imageViewPager);
        imageViewPager.setAdapter(pageAdapter);
    }

    private void initData() {
        cursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,columns,
                MediaStore.Images.Media.TITLE+" like '%"+imageNameKey+"%'",null,null);

        fileColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        titleColumn = cursor.getColumnIndex(MediaStore.Images.Media.TITLE);
        displayColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
        searchImages();
        imageAdapter=new ImageDetailListAdapter(this,images);
        pageAdapter=new ImageViewPagePagerAdapter(this,images);
    }
    private void searchImages(){
        images=new ArrayList<>();

        ImageInfo imageInfo;
        String title;
        String path;
        while (cursor.moveToNext()){
            title=cursor.getString(titleColumn);
            path=cursor.getString(fileColumn);
            imageInfo=new ImageInfo(title,path);
            images.add(imageInfo);
        }

        // printImages();



    }

}

package com.zhenai.imagescanner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;

import java.io.File;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by admin on 2015/10/30.
 */
public class ImageViewPagePagerAdapter extends PagerAdapter{



    private List<ImageInfo> images;
    private LayoutInflater inflater;
    private Context context;

    public ImageViewPagePagerAdapter(Activity context,List<ImageInfo> images){

        this.context=context;


            this.images=images;

        inflater= LayoutInflater.from(this.context);

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.image_viewpager_item,container,false);
        ImageView bigImg=(ImageView)view.findViewById(R.id.bigImg);
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this.context));

       /* ImageLoader.getInstance().displayImage("file://"+images.get(position).getPath(),bigImg,new DisplayImageOptions.Builder()
                .resetViewBeforeLoading().cacheOnDisc()
                .imageScaleType(ImageScaleType.NONE)
                .bitmapConfig(Bitmap.Config.RGB_565).build());*/

        Glide.with(this.context)
                .load(images.get(position).getPath())
                .fitCenter()
                .into(bigImg);
        container.addView(bigImg);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}

package com.zhenai.imagescanner;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin on 2015/10/29.
 */
public class ImageDetailViewHolder  extends RecyclerView.ViewHolder{

    public View mView;
    public ImageView imageView;
    public String imagePath;

    public ImageDetailViewHolder(View itemView) {
        super(itemView);
        mView=itemView;
        imageView = (ImageView)itemView.findViewById(R.id.image_detail);

    }
}

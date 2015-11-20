package com.zhenai.imagescanner;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 2015/10/29.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    public String imagetitle;
    public String imagePath;

    public View mView;
    public EditText title;
    public  ImageView imageView;
    public View cutLine;


    public ImageViewHolder(View itemView) {
        super(itemView);
        mView=itemView;
        title=(EditText)itemView.findViewById(R.id.title_ed);
        imageView=(ImageView)itemView.findViewById(R.id.avatar);
        cutLine=itemView.findViewById(R.id.cutLine);
    }
}

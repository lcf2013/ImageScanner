package com.zhenai.imagescanner;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v4.util.DebugUtils;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by admin on 2015/10/29.
 */
public class ImageAdapter  extends RecyclerView.Adapter<ImageViewHolder>{

    MainActivity mContext;
    List<ImageInfo> images;//图片数据

    public ImageAdapter(Context context,List<ImageInfo> list){
        this.mContext=(MainActivity)context;
        this.images=list;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_list_item,viewGroup,false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder imageViewHolder, int i) {
        imageViewHolder.imagetitle=images.get(i).getTitle();
        imageViewHolder.imagePath=images.get(i).getPath();
        imageViewHolder.title.setText(images.get(i).getTitle());

        imageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ImageScannerActivity.class);
                intent.putExtra(MainActivity.EXTRA_NAME, imageViewHolder.imagetitle);

                context.startActivity(intent);
            }
        });
        imageViewHolder.title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    System.out.println("------------focus");
                    mContext.addNewFileName(imageViewHolder.imagePath, imageViewHolder.imagetitle);
                }
            }
        });
        imageViewHolder.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContext.changeFileName(imageViewHolder.imagePath, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Glide.with(imageViewHolder.imageView.getContext())
                .load(images.get(i).getPath())
                .fitCenter()
                .into(imageViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

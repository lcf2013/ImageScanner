package com.zhenai.imagescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by admin on 2015/10/29.
 */
public class ImageDetailListAdapter  extends RecyclerView.Adapter<ImageDetailViewHolder>{

    private Context context;

    private List<ImageInfo> images;
    public ImageDetailListAdapter(Activity context,List<ImageInfo> list){

        this.context=context;
        this.images=list;
    }

    @Override
    public ImageDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater. from(parent.getContext()).inflate(R.layout.image_detail_item,parent,false);



        return new ImageDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageDetailViewHolder holder, int position) {
        holder.imagePath = images.get(position).getPath();
        Glide.with(holder.imageView.getContext())
                .load(images.get(position).getPath())
                .fitCenter()
                .into(holder.imageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LargeImageViewActivity.class);
                intent.putExtra(MainActivity.EXTRA_NAME, holder.imagePath);

                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

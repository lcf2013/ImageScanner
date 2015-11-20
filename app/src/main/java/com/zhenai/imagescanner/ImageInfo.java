package com.zhenai.imagescanner;

import android.net.Uri;

/**
 * Created by admin on 2015/10/29.
 */
public class ImageInfo {
    private String title;
    private String path;


    public ImageInfo(String title,String path){
        this.title=title;
        this.path=path;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return title+":"+path;
    }
}

package com.zhenai.imagescanner;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DebugUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    RecyclerView imageRecyclerView;

    RightBarView rightBarView;

    List<ImageInfo> images;

    private ImageAdapter imageAdapter;


    String[] columns = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DISPLAY_NAME};


    private int fileColumn;
    private int titleColumn;
    private int displayColumn;

    Cursor cursor;

    AlertDialog confirmDialog;


    FloatingActionButton floatingActionButton;

    HashMap<String, String> changedFile;//改变的文件名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initData() {

        searchImages();
        imageAdapter = new ImageAdapter(this, images);

        changedFile = new HashMap<>();
    }

    public void addNewFileName(String key, String value) {
        if (changedFile.containsKey(key))
            return;
        else
            changedFile.put(key, value);
    }

    public void changeFileName(String key, String value) {
        if (!changedFile.containsKey(key))
            return;
        else
            changedFile.put(key, value);
    }

    private void printImages() {
        for (ImageInfo image : images) {
            System.out.println(image);
        }
    }

    private void searchImages() {
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, null);

        fileColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        titleColumn = cursor.getColumnIndex(MediaStore.Images.Media.TITLE);
        displayColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);


        images = new ArrayList<>();

        ImageInfo imageInfo;
        String title;
        String path;
        while (cursor.moveToNext()) {
            title = cursor.getString(titleColumn);
            path = cursor.getString(fileColumn);
            imageInfo = new ImageInfo(title, path);
            images.add(imageInfo);
        }

        // printImages();


    }

    private void initListener() {
        rightBarView.setOnTouchingLetterChangedListener(new RightBarView.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });
    }

    private void initView() {
        imageRecyclerView = (RecyclerView) findViewById(R.id.imageRecyclerView);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(imageRecyclerView.getContext()));
        rightBarView = (RightBarView) findViewById(R.id.rightBar);

        imageRecyclerView.addItemDecoration(new SpaceItemDecoration(2));
        imageRecyclerView.setAdapter(imageAdapter);


        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatBtn);


    }

    private void showConfirmDialog() {
        if (confirmDialog == null) {
            confirmDialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("是否保存文件重命名")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            confirmDialog.dismiss();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveNewFileName();
                        }
                    })
                    .create();
            confirmDialog.show();
        } else {
            confirmDialog.show();
        }
    }

    /**
     * 保存新的文件名
     */
    private void saveNewFileName() {
        String filePath;
        String fileName;
        File file;
        File newFile;

        String fileFuffix;
        String newPathString;
        for (Map.Entry<String, String> entry : changedFile.entrySet()) {
            filePath = entry.getKey();
            fileFuffix = filePath.substring(filePath.indexOf("."), filePath.length());
            fileName = entry.getValue();
            file = new File(filePath);
            newPathString = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1)
                    + fileName + fileFuffix;
            newFile = new File(newPathString);
            System.out.println(filePath + ":" + fileFuffix + ":" + newPathString);
            if (file.exists()) {
                System.out.println("file exists");
                file.renameTo(newFile);
            } else {
                System.out.println("file not exists");
            }

        }
        changedFile.clear();
        searchImages();
        imageAdapter.notifyDataSetChanged();
    }


    /**
     * recycylerView项目间距
     */
    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }

}

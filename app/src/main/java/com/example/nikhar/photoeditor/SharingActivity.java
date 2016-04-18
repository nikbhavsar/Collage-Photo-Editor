package com.example.nikhar.photoeditor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.nikhar.photoeditor.comman.Constance;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;

public class SharingActivity extends AppCompatActivity implements OnClickListener {

    private ImageView img_done;
    private ImageView img_back;
    private RelativeLayout rl_tabbar, rl_footer;
    private LinearLayout ll_main;
    private ImageLoader imageLoader;
    private ImageView imgOrange;
    private TextView tvShareLink;
    private String path = "";
    private setImageAsyncTask mSetImageAsyncTask;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Share Image");

        initComponent();



    }

    private void initComponent() {


        img_done = (ImageView) findViewById(R.id.img_done);
        imgOrange = (ImageView) findViewById(R.id.imgOrange);
        rl_tabbar = (RelativeLayout) findViewById(R.id.rl_tabbar);
        rl_footer = (RelativeLayout) findViewById(R.id.rl_footer);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        tvShareLink = (TextView) findViewById(R.id.tvShareLink);



        tvShareLink.setOnClickListener(this);


        initImageLoader();

        Intent intent = getIntent();

        if (intent != null) {
            path = intent.getStringExtra(Constance.IMAGE_PATH);
            setImage();
        }


    }

    private void setImage() {

        if (mSetImageAsyncTask != null
                && mSetImageAsyncTask.getStatus() == AsyncTask.Status.PENDING) {
            mSetImageAsyncTask.execute();
        } else if (mSetImageAsyncTask == null
                || mSetImageAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            mSetImageAsyncTask = new setImageAsyncTask();
            mSetImageAsyncTask.execute();
        }
    }


    /****************************************************************************
     * mSetImageAsyncTask
     *
     * @CreatedDate:
     * @ModifiedBy: not yet
     * @ModifiedDate: not yet
     * @purpose:This Class Use to Create GlcCard With Perameter And Return
     * UserId
     ***************************************************************************/

    private class setImageAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            imageLoader.displayImage("file://" + path, imgOrange);


        }
    }


    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }


    // Method to share any image.
    private void shareImage()
    {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");

        // Make sure you put example png image named myImage.png in your
        // directory


        Log.d("", "PATH:" + path);

        File imageFileToShare = new File(path);
        Uri uri = Uri.fromFile(imageFileToShare);
        //share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=kailash.natural.wallpaper");
        share.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(share, "Share Image!"));
    }


    // Method to share any image.
    private void shareLink(View v)
    {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");

        // Make sure you put example png image named myImage.png in your
        // directory


        Log.d("", "PATH:" + path);

        File imageFileToShare = new File(path);
        Uri uri = Uri.fromFile(imageFileToShare);

        share.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(share, "Share Image!"));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_done:

                shareImage();
            case R.id.tvShareLink:

                shareLink(v);

                break;


            default:
                break;
        }

    }





}

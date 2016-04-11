package com.example.nikhar.photoeditor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.nikhar.photoeditor.comman.Constance;
import com.example.nikhar.photoeditor.widgets.SaturationBar;
import com.example.nikhar.photoeditor.widgets.SaturationBar.OnSaturationChangedListener;
import com.example.nikhar.photoeditor.widgets.ValueBar;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class EffectActivity extends Activity implements View.OnClickListener,
        SaturationBar.OnSaturationChangedListener {

    private ImageView img_done;
    private ImageView imgOrange;
    private ImageView imgGreyScale;
    private ImageView imgSepia;
    private ImageView imgContrast;
    private ImageView imgBlackedgeburn;
    private ImageView imfWhiteedgeburn;
    private ImageView imgBrightness;
    private ImageView imgOrignal;
    private ImageView imgFrameBlack;
    private ImageView imgFrameWhite;
    private ImageView imgBack;

    private RelativeLayout rl_image_effect;
    private RelativeLayout rl_color_effect;
    private TextView titleTv;

    private String path = "";
    private Bitmap bmp;
    private File imagePath ;

    private ImageLoader imageLoader;

    int alfa_saturation = 0;
    int alfa_value = 0;
    private SaturationBar saturationBar;
    private ValueBar valueBar;

    private setImageAsyncTask mSetImageAsyncTask;
    private ProgressDialog progress;
    private RelativeLayout rltabbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect);

        Intent intent = getIntent();

        if (intent != null) {
            path = intent.getStringExtra(Constance.IMAGE_PATH);
        }

        initComponent();
        initImageLoader();


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

    @Override
    public void onSaturationChanged(int saturation) {
        // saturationBar.setColor(alfa_value);
        imgOrange.setColorFilter(saturation, PorterDuff.Mode.MULTIPLY);
        alfa_saturation = saturation;
        // alpha = tnt.getProgress();
    }

    private void initComponent() {

        imgOrange = (ImageView) findViewById(R.id.imgOrange);
        imgGreyScale = (ImageView) findViewById(R.id.imgGreyScale);
        imgSepia = (ImageView) findViewById(R.id.imgSepia);
        imgContrast = (ImageView) findViewById(R.id.imgContrast);
        imgBlackedgeburn = (ImageView) findViewById(R.id.imgBlackedgeburn);
        imfWhiteedgeburn = (ImageView) findViewById(R.id.imfWhiteedgeburn);
        imgBrightness = (ImageView) findViewById(R.id.imgBrightness);
        imgOrignal = (ImageView) findViewById(R.id.imgOrignal);

        imgFrameBlack = (ImageView) findViewById(R.id.imgFrameBlack);
        imgFrameWhite = (ImageView) findViewById(R.id.imgFrameWhite);
        img_done = (ImageView) findViewById(R.id.img_done);
        imgBack = (ImageView) findViewById(R.id.img_back);

        saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        valueBar = (ValueBar) findViewById(R.id.valueBar);

        rl_color_effect = (RelativeLayout) findViewById(R.id.rl_color_effect);
        rl_image_effect = (RelativeLayout) findViewById(R.id.rl_image_effect);
        rltabbar= (RelativeLayout) findViewById(R.id.rltabbar);

        titleTv = (TextView) findViewById(R.id.txt_header);

        imgGreyScale.setOnClickListener(this);
        imgSepia.setOnClickListener(this);
        imgContrast.setOnClickListener(this);
        imgBlackedgeburn.setOnClickListener(this);
        imfWhiteedgeburn.setOnClickListener(this);
        imgBrightness.setOnClickListener(this);
        imgOrignal.setOnClickListener(this);
        img_done.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        titleTv.setText(getString(R.string.Add_color_Effect));

        if (path != null) {

            setImage();

        }

        // Bitmap icon =
        // BitmapFactory.decodeResource(getApplicationContext().getResources(),
        // R.drawable.myprofile_menu);

        valueBar.setColor(Color.parseColor("#B45F04"));
        valueBar.setOnValueChangedListener(new ValueBar.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                saturationBar.setColor(value);
                alfa_value = value;
                saturationBar
                        .setOnSaturationChangedListener(new OnSaturationChangedListener() {

                            @Override
                            public void onSaturationChanged(int saturation) {
                                // saturationBar.setColor(alfa_value);
                                imgOrange.setColorFilter(saturation,
                                        PorterDuff.Mode.MULTIPLY);
                                alfa_saturation = saturation;
                            }
                        });
                imgOrange.setColorFilter(value, PorterDuff.Mode.MULTIPLY);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgGreyScale:
                imgOrange.setImageBitmap(ColorFilterEffectsLib
                        .ConvertToBlackAndWhite(bmp));

                break;
            case R.id.imgSepia:



                break;
            case R.id.imgContrast:
                imgOrange.setImageBitmap(ColorFilterEffectsLib.adjustedContrast(
                        bmp, 60));

                break;
            case R.id.imgBlackedgeburn:
                              break;

            case R.id.imfWhiteedgeburn:

                break;

            case R.id.imgBrightness:
                imgOrange.setImageBitmap(ColorFilterEffectsLib
                        .doBrightness(bmp, 75));
                break;

            case R.id.imgOrignal:
                imgOrange.setImageBitmap(bmp);
                break;

            case R.id.img_done:


                break;
            case R.id.img_back:


            default:
                break;
        }

    }

    public Bitmap doWhiteEdgeBurn(Bitmap bmp_view) {
        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
                R.drawable.random_overlay_white);

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = imgFrameWhite.getLayoutParams().height = bmp_view
                .getHeight();
        int newHeight = imgFrameWhite.getLayoutParams().width = bmp_view
                .getWidth() + 5;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // createa matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleHeight, scaleWidth);
        // rotate the Bitmap

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
                height, matrix, true);

        Bitmap combineImages = ColorFilterEffectsLib.mergeImage_edgeburn(
                bmp_view, resizedBitmap);
        return combineImages;
    }

    public Bitmap doBlackEdgeBurn(Bitmap bmp_view) {

        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
                R.drawable.random_overlay);

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = imgFrameBlack.getLayoutParams().height = bmp_view
                .getHeight();
        int newHeight = imgFrameBlack.getLayoutParams().width = bmp_view
                .getWidth() + 5;

        // calculate the scale - in this case = 0.f
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // createa matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleHeight, scaleWidth);
        // rotate the Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
                height, matrix, true);

        Bitmap combineImages = ColorFilterEffectsLib.mergeImage_edgeburn(
                bmp_view, resizedBitmap);

        return combineImages;
    }

    /****************************************************************************
     *
     * @purpose:This Method use to Call mSetImageAsyncTask
     *
     ***************************************************************************/

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
     *               UserId
     *
     ***************************************************************************/

    private class setImageAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progress = new ProgressDialog(EffectActivity.this);
            progress.setMessage("Please wait...");
            progress.show();
            progress.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                bmp = BitmapFactory.decodeFile(path);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }

            // bmp = rotateImageIfRequired(getApplicationContext(), bmp,
            // Uri.fromFile(new File(path)));
            //imgOrange.setImageBitmap(bmp);

            imageLoader.displayImage("file://" +path, imgOrange);

            //imgBlackedgeburn.setImageBitmap(bmp);
            //imfWhiteedgeburn.setImageBitmap(bmp);
            //imgOrignal.setImageBitmap(bmp);


            imageLoader.displayImage("file://" +path, imgBlackedgeburn);
            imageLoader.displayImage("file://" +path, imfWhiteedgeburn);
            imageLoader.displayImage("file://" +path, imgOrignal);



            imgGreyScale.setImageBitmap(ColorFilterEffectsLib
                    .ConvertToBlackAndWhite(bmp));
            imgBrightness.setImageBitmap(ColorFilterEffectsLib
                    .doBrightness(bmp, 75));
            imgSepia.setImageBitmap(ColorFilterEffectsLib
                    .ConvertToSepia(bmp));
            imgContrast.setImageBitmap(ColorFilterEffectsLib
                    .adjustedContrast(bmp, 60));

        }
    }

    public Bitmap takeScreenshot() {
        Log.e("", "call takeScreenshot");

        imgOrange.setDrawingCacheEnabled(true);
        getWindow().getDecorView().getRootView();
        return imgOrange.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        imagePath = getOutputMediaFile();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private File getOutputMediaFile() {
        File fileDir = null;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            fileDir = new File(Environment.getExternalStorageDirectory(),
                    "OrangePhotobooth");
        } else {
            fileDir = new File(getFilesDir(), "OrangePhotobooth");
        }
        if (fileDir != null && !fileDir.exists()) {
            if (!fileDir.mkdir()) {

                return null;
            }
        }

        String time = new SimpleDateFormat("MM_dd_yyyy__HH_mm_ss_SSS")
                .format(new Date());
        File file = new File(fileDir.getPath() + File.separator + time
                + "_photo" + ".jpg");// TODO check formate is ok?
        return file;
    }


}

package com.example.nikhar.photoeditor;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatBase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhar.photoeditor.comman.Touch;
import com.example.nikhar.photoeditor.galary.Action;
import com.example.nikhar.photoeditor.galary.CustomGallery;
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
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageView img_camera;
    private ImageView img_gallery;
    private ImageView img_template;
    private ImageView img_done;
    private ImageView img_thim;

    private ImageView img_t1_1;

    private ImageView img_t2_1;
    private ImageView img_t2_2;

    private ImageView img_t3_1;
    private ImageView img_t3_2;
    private ImageView img_t3_3;

    private ImageView img_t4_1;
    private ImageView img_t4_2;
    private ImageView img_t4_3;
    private ImageView img_t4_4;

    private ImageView img_t5_1;
    private ImageView img_t5_2;
    private ImageView img_t5_3;
    private ImageView img_t5_4;
    private ImageView img_t5_5;

    private ImageView img_t6_1;
    private ImageView img_t6_2;
    private ImageView img_t6_3;
    private ImageView img_t6_4;
    private ImageView img_t6_5;
    private ImageView img_t6_6;

    private LinearLayout ll_home_one;
    private LinearLayout ll_home_two;
    private LinearLayout ll_home_three;
    private LinearLayout ll_home_four;
    private LinearLayout ll_home_five;
    private LinearLayout ll_home_six;

    private ImageLoader imageLoader;

    public static int checkTemplateNum = 1;
    static String[] all_path;
    private ProgressDialog progress;
    private RelativeLayout rltabbar;
    private RelativeLayout rlfooter;
    static ArrayList<CustomGallery> dataT;
    static ArrayList<String> CameraArry = new ArrayList<String>();

    private Bitmap bitmapOrange;
    private FrameLayout flMain;
    private File imagePath;
    private setImageAsyncTask mSetImageAsyncTask;
    private setImageCameraAsyncTask mSetImageCameraAsyncTask;

    
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        initComponent();

        img_t1_1.setOnTouchListener(new Touch());

        img_t2_1.setOnTouchListener(new Touch());
        img_t2_2.setOnTouchListener(new Touch());

        img_t3_1.setOnTouchListener(new Touch());
        img_t3_2.setOnTouchListener(new Touch());
        img_t3_3.setOnTouchListener(new Touch());

        img_t4_1.setOnTouchListener(new Touch());
        img_t4_2.setOnTouchListener(new Touch());
        img_t4_3.setOnTouchListener(new Touch());
        img_t4_4.setOnTouchListener(new Touch());

        img_t5_1.setOnTouchListener(new Touch());
        img_t5_2.setOnTouchListener(new Touch());
        img_t5_3.setOnTouchListener(new Touch());
        img_t5_4.setOnTouchListener(new Touch());
        img_t5_5.setOnTouchListener(new Touch());

        img_t6_1.setOnTouchListener(new Touch());
        img_t6_2.setOnTouchListener(new Touch());
        img_t6_3.setOnTouchListener(new Touch());
        img_t6_4.setOnTouchListener(new Touch());
        img_t6_5.setOnTouchListener(new Touch());
        img_t6_6.setOnTouchListener(new Touch());


        initImageLoader();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.Layout) {
            showTemplateDialogNew();
        }
        if (id == R.id.Gallary) {
            Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
            startActivityForResult(i, 200);

        }
        if (id == R.id.Camera) {

            MainActivity.CameraArry.clear();
            if (checkTemplateNum != 0) {
                Intent intent = new Intent(getApplicationContext(),
                        CustomCameraActivity.class);
                startActivityForResult(intent, 100);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Select no. of images.", Toast.LENGTH_SHORT).show();
            }
        }



        return super.onOptionsItemSelected(item);
    }

    private void showTemplateDialogNew() {
        final ImageView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix;
        final TextView tvCancel;
        final Dialog dialog = new Dialog(MainActivity.this, R.style.picture_dialog_style);
        dialog.setContentView(R.layout.dialog_select_template_new);
        final WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;
        wlmp.width = android.view.WindowManager.LayoutParams.FILL_PARENT;
        wlmp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(wlmp);
        dialog.show();

        tvOne = (ImageView) dialog.findViewById(R.id.imgOne);
        tvTwo = (ImageView) dialog.findViewById(R.id.imgTwo);
        tvThree = (ImageView) dialog.findViewById(R.id.imgThree);
        tvFour = (ImageView) dialog.findViewById(R.id.imgFour);
        tvFive = (ImageView) dialog.findViewById(R.id.imgFive);
        tvSix = (ImageView) dialog.findViewById(R.id.imgSix);
        tvCancel = (TextView) dialog.findViewById(R.id.dialog_delete_record_tvCancel);


        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                checkTemplateNum = 1;
                ll_home_one.setVisibility(View.VISIBLE);
                ll_home_two.setVisibility(View.GONE);
                ll_home_three.setVisibility(View.GONE);
                ll_home_four.setVisibility(View.GONE);
                ll_home_five.setVisibility(View.GONE);
                ll_home_six.setVisibility(View.GONE);

            }
        });

        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                checkTemplateNum = 2;
                ll_home_one.setVisibility(View.GONE);
                ll_home_two.setVisibility(View.VISIBLE);
                ll_home_three.setVisibility(View.GONE);
                ll_home_four.setVisibility(View.GONE);
                ll_home_five.setVisibility(View.GONE);
                ll_home_six.setVisibility(View.GONE);

            }
        });

        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                checkTemplateNum = 3;
                ll_home_one.setVisibility(View.GONE);
                ll_home_two.setVisibility(View.GONE);
                ll_home_three.setVisibility(View.VISIBLE);
                ll_home_four.setVisibility(View.GONE);
                ll_home_five.setVisibility(View.GONE);
                ll_home_six.setVisibility(View.GONE);

            }
        });


        tvFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                checkTemplateNum = 4;
                ll_home_four.setVisibility(View.VISIBLE);
                ll_home_one.setVisibility(View.GONE);
                ll_home_two.setVisibility(View.GONE);
                ll_home_three.setVisibility(View.GONE);
                ll_home_five.setVisibility(View.GONE);
                ll_home_six.setVisibility(View.GONE);

            }
        });

        tvFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

                checkTemplateNum = 5;
                ll_home_four.setVisibility(View.GONE);
                ll_home_one.setVisibility(View.GONE);
                ll_home_two.setVisibility(View.GONE);
                ll_home_three.setVisibility(View.GONE);
                ll_home_five.setVisibility(View.VISIBLE);
                ll_home_six.setVisibility(View.GONE);

            }
        });

        tvSix.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.cancel();
                checkTemplateNum = 6;
                ll_home_six.setVisibility(View.VISIBLE);
                ll_home_one.setVisibility(View.GONE);
                ll_home_two.setVisibility(View.GONE);
                ll_home_three.setVisibility(View.GONE);
                ll_home_four.setVisibility(View.GONE);
                ll_home_five.setVisibility(View.GONE);

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }
    private void initComponent()
    {
        img_done = (ImageView) findViewById(R.id.img_done);
        img_thim= (ImageView) findViewById(R.id.img_thim);

        img_t1_1 = (ImageView) findViewById(R.id.img_t1_1);

        img_t2_1 = (ImageView) findViewById(R.id.img_t2_1);
        img_t2_2 = (ImageView) findViewById(R.id.img_t2_2);

        img_t3_1 = (ImageView) findViewById(R.id.img_t3_1);
        img_t3_2 = (ImageView) findViewById(R.id.img_t3_2);
        img_t3_3 = (ImageView) findViewById(R.id.img_t3_3);

        img_t4_1 = (ImageView) findViewById(R.id.img_t4_1);
        img_t4_2 = (ImageView) findViewById(R.id.img_t4_2);
        img_t4_3 = (ImageView) findViewById(R.id.img_t4_3);
        img_t4_4 = (ImageView) findViewById(R.id.img_t4_4);

        img_t5_1 = (ImageView) findViewById(R.id.img_t5_1);
        img_t5_2 = (ImageView) findViewById(R.id.img_t5_2);
        img_t5_3 = (ImageView) findViewById(R.id.img_t5_3);
        img_t5_4 = (ImageView) findViewById(R.id.img_t5_4);
        img_t5_5 = (ImageView) findViewById(R.id.img_t5_5);

        img_t6_1 = (ImageView) findViewById(R.id.img_t6_1);
        img_t6_2 = (ImageView) findViewById(R.id.img_t6_2);
        img_t6_3 = (ImageView) findViewById(R.id.img_t6_3);
        img_t6_4 = (ImageView) findViewById(R.id.img_t6_4);
        img_t6_5 = (ImageView) findViewById(R.id.img_t6_5);
        img_t6_6 = (ImageView) findViewById(R.id.img_t6_6);

        flMain = (FrameLayout) findViewById(R.id.FramMain);

        ll_home_one = (LinearLayout) findViewById(R.id.ll_one);
        ll_home_two = (LinearLayout) findViewById(R.id.ll_two);
        ll_home_three = (LinearLayout) findViewById(R.id.ll_three);
        ll_home_four = (LinearLayout) findViewById(R.id.ll_four);
        ll_home_five = (LinearLayout) findViewById(R.id.ll_five);
        ll_home_six = (LinearLayout) findViewById(R.id.ll_six);

        rlfooter=(RelativeLayout)findViewById(R.id.rlfooter);





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        img_done.setVisibility(View.VISIBLE);

        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {

            // Return multiple path string array path

            all_path = data.getStringArrayExtra("all_path");
            dataT = new ArrayList<CustomGallery>();



            setImage();


        }

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {


            setImageCamera();
        }

    }



    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 200;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2);
    }



    private static Bitmap rotateImageIfRequired(Context context, Bitmap img,
                                                Uri selectedImage) {
        int rotation = getRotation(context, selectedImage);
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(),
                    img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        } else {
            return img;
        }
    }

    private static int getRotation(Context context, Uri selectedImage) {
        int rotation = 0;
        ContentResolver content = context.getContentResolver();

        Cursor mediaCursor = content.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {
                        "orientation", "date_added" }, null, null,
                "date_added desc");

        if (mediaCursor != null && mediaCursor.getCount() != 0) {
            while (mediaCursor.moveToNext()) {
                rotation = mediaCursor.getInt(0);
                break;
            }
        }
        mediaCursor.close();
        return rotation;
    }

    public Bitmap takeScreenshot() {
        Log.e("", "call takeScreenshot");

        flMain.setDrawingCacheEnabled(true);
        getWindow().getDecorView().getRootView();
        return flMain.getDrawingCache();
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

    private class setImageCameraAsyncTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Please wait...");
            progress.show();
            progress.setCancelable(false);
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
            if (progress != null && progress.isShowing()) {
                progress.dismiss();



                // Return multiple path string array path

                for (int i = 0; i < CameraArry.size(); i++) {

                    if (checkTemplateNum == 1)
                    {

                        Log.e("", "Path==" + CameraArry.get(i).toString());

                        String path = CameraArry.get(i).toString();
						/*Bitmap bmp = BitmapFactory.decodeFile(path);
						bmp = rotateImageIfRequired(getApplicationContext(), bmp,
								Uri.fromFile(new File(path)));
						img_t1_1.setImageBitmap(bmp);*/

                        imageLoader.displayImage("file://" +path, img_t1_1);

                    }
                    else if (checkTemplateNum == 2)
                    {

                        String path = CameraArry.get(i).toString();
						/*Bitmap bmp = BitmapFactory.decodeFile(path);
						bmp = rotateImageIfRequired(getApplicationContext(), bmp,
								Uri.fromFile(new File(path)));*/

                        if (i == 0) {
                            //img_t2_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t2_1);
                        } else if (i == 1) {
                            //img_t2_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t2_2);
                        }
                    }
                    else if (checkTemplateNum == 3)
                    {

                        String path = CameraArry.get(i).toString();
						/*Bitmap bmp = BitmapFactory.decodeFile(path);
						bmp = rotateImageIfRequired(getApplicationContext(), bmp,
								Uri.fromFile(new File(path)));*/

                        if (i == 0) {
                            //img_t3_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t3_1);
                        } else if (i == 1) {
                            //img_t3_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t3_2);
                        } else if (i == 2) {
                            //img_t3_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t3_3);
                        }

                    }
                    else if (checkTemplateNum == 4)
                    {

                        String path = CameraArry.get(i).toString();
					/*	Bitmap bmp = BitmapFactory.decodeFile(path);
						bmp = rotateImageIfRequired(getApplicationContext(), bmp,
								Uri.fromFile(new File(path)));*/

                        if (i == 0) {
                            //img_t4_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t4_1);
                        } else if (i == 1) {
                            //img_t4_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t4_2);
                        } else if (i == 2) {
                            //img_t4_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t4_3);
                        } else if (i == 3) {
                            //img_t4_4.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t4_4);
                        }

                    }
                    else if (checkTemplateNum == 5)
                    {

                        String path = CameraArry.get(i).toString();
						/*Bitmap bmp = BitmapFactory.decodeFile(path);
						bmp = rotateImageIfRequired(getApplicationContext(), bmp,
								Uri.fromFile(new File(path)));*/

                        if (i == 0) {
                            //img_t5_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t5_1);
                        } else if (i == 1) {
                            //img_t5_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t5_2);
                        } else if (i == 2) {
                            //img_t5_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t5_3);
                        } else if (i == 3) {
                            //img_t5_4.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t5_4);
                        }
                        else if (i == 4) {
                            //img_t5_5.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t5_5);
                        }

                    }
                    else if (checkTemplateNum == 6) {

                        String path = CameraArry.get(i).toString();
						/*Bitmap bmp = BitmapFactory.decodeFile(path);
						bmp = rotateImageIfRequired(getApplicationContext(), bmp,
								Uri.fromFile(new File(path)));*/

                        if (i == 0) {
                            //img_t6_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t6_1);
                        } else if (i == 1) {
                            //img_t6_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t6_2);
                        } else if (i == 2) {
                            //img_t6_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t6_3);
                        } else if (i == 3) {
                            //img_t6_4.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t6_4);
                        } else if (i == 4) {
                            //img_t6_5.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t6_5);
                        } else if (i == 5) {
                            //img_t6_6.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +path, img_t6_6);
                        }

                    }
                }


            }


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
            progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Please wait...");
            progress.show();
            progress.setCancelable(false);
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
            if (progress != null && progress.isShowing()) {
                progress.dismiss();


                if (checkTemplateNum == 1)
                {
                    for (String string : all_path)
                    {

                        CustomGallery item = new CustomGallery();
                        item.sdcardPath = string;
                        //Bitmap bmp = BitmapFactory.decodeFile(item.sdcardPath);
                        //bmp = rotateImageIfRequired(getApplicationContext(), bmp,Uri.fromFile(new File(item.sdcardPath)));
                        //ByteArrayOutputStream bos = new ByteArrayOutputStream();

                        //bmp.compress(CompressFormat.JPEG, 70, bos);
                        //img_t1_1.setImageBitmap(bmp);

                        imageLoader.displayImage("file://" +item.sdcardPath, img_t1_1);

                    }
                }
                else if (checkTemplateNum == 2)
                {

                    int i = 1;

                    for (String string : all_path) {

                        CustomGallery item = new CustomGallery();
                        item.sdcardPath = string;
//						Bitmap bmp = BitmapFactory.decodeFile(item.sdcardPath);
//
//						ByteArrayOutputStream bos = new ByteArrayOutputStream();
//						bmp.compress(CompressFormat.JPEG, 70, bos);

                        if (i == 1) {
                            //img_t2_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t2_1);
                        } else if (i == 2) {
                            //img_t2_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t2_2);
                        }

                        i = i + 1;

                    }

                }
                else if (checkTemplateNum == 3)
                {

                    int i = 1;

                    for (String string : all_path) {

                        CustomGallery item = new CustomGallery();
                        item.sdcardPath = string;
						/*Bitmap bmp = BitmapFactory.decodeFile(item.sdcardPath);

						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						bmp.compress(CompressFormat.JPEG, 70, bos);
*/
                        if (i == 1) {
                            //img_t3_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t3_1);
                        } else if (i == 2) {
                            //img_t3_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t3_2);
                        } else if (i == 3) {
                            //img_t3_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t3_3);
                        }

                        i = i + 1;

                    }

                }
                else if (checkTemplateNum == 4)
                {

                    int i = 1;

                    for (String string : all_path) {

                        CustomGallery item = new CustomGallery();
                        item.sdcardPath = string;
                        //Bitmap bmp = BitmapFactory.decodeFile(item.sdcardPath);

                        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        //bmp.compress(CompressFormat.JPEG, 70, bos);

                        if (i == 1) {
                            //img_t4_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t4_1);


                        } else if (i == 2) {
                            //img_t4_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t4_2);


                        } else if (i == 3) {
                            //img_t4_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t4_3);


                        } else if (i == 4) {
                            //img_t4_4.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t4_4);


                        }

                        i = i + 1;

                    }

                }
                else if (checkTemplateNum == 5)
                {

                    int i = 1;

                    for (String string : all_path) {

                        CustomGallery item = new CustomGallery();
                        item.sdcardPath = string;
						/*Bitmap bmp = BitmapFactory.decodeFile(item.sdcardPath);

						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						bmp.compress(CompressFormat.JPEG, 70, bos);*/

                        if (i == 1) {
                            //img_t5_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t5_1);
                        } else if (i == 2) {
                            //img_t5_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t5_2);
                        } else if (i == 3) {
                            //img_t5_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t5_3);
                        } else if (i == 4) {
                            //img_t5_4.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t5_4);
                        }else if (i == 5) {
                            //img_t5_5.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t5_5);
                        }

                        i = i + 1;

                    }

                }
                else if (checkTemplateNum == 6) {

                    int i = 1;

                    for (String string : all_path) {

                        CustomGallery item = new CustomGallery();
                        item.sdcardPath = string;
						/*Bitmap bmp = BitmapFactory.decodeFile(item.sdcardPath);
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						bmp.compress(CompressFormat.JPEG, 70, bos);*/

                        if (i == 1) {
                            //img_t6_1.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t6_1);
                        } else if (i == 2) {
                            //img_t6_2.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t6_2);
                        } else if (i == 3) {
                            //img_t6_3.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t6_3);
                        } else if (i == 4) {
                            //img_t6_4.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t6_4);
                        } else if (i == 5) {
                            //img_t6_5.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t6_5);
                        } else if (i == 6) {
                            //img_t6_6.setImageBitmap(bmp);
                            imageLoader.displayImage("file://" +item.sdcardPath, img_t6_6);
                        }

                        i = i + 1;

                    }

                }
            }


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

    private void setImageCamera() {

        if (mSetImageCameraAsyncTask != null
                && mSetImageCameraAsyncTask.getStatus() == AsyncTask.Status.PENDING) {
            mSetImageCameraAsyncTask.execute();
        } else if (mSetImageCameraAsyncTask == null
                || mSetImageCameraAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            mSetImageCameraAsyncTask = new setImageCameraAsyncTask();
            mSetImageCameraAsyncTask.execute();
        }
    }


    }




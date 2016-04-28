package com.example.nikhar.photoeditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.nikhar.photoeditor.comman.ArrayWheelAdapter;
import com.example.nikhar.photoeditor.comman.OnWheelChangedListener;
import com.example.nikhar.photoeditor.comman.OnWheelScrollListener;
import com.example.nikhar.photoeditor.comman.WheelView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class CustomCameraActivity extends Activity implements
        SurfaceHolder.Callback {
    Camera camera = null;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false, frontCamera = false;
    int flag = 0;
    int which = 0;
    int vesion = 0;
    TextView text;
    WheelView wheelview;
    final String cities[] = new String[] { "3", "4", "5", "6", "7", "8", "9",
            "10" };
    Timer timer;
    Handler timerUpdateHandler;
    private CountDownTimer countDownTimer;
    private long startTime = 4000;
    boolean wheelScrolled = false;
    private long interval = 1 * 1000;
    ImageButton cap_btn;

    static String thePic;
    static int imgNo = 0;
    static int imgNoCount = 0;
    private File sdRoot;
    private ExifInterface exif;
    ToggleButton tgb;
    private String dir;
    private String fileName;
    private int orientation;
    int pictureCounter;
    Button ibRetake, ibUse, turn;
    boolean hasFlash;
    boolean isFlashOn = true;
    RelativeLayout rl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);




        rl = (RelativeLayout) findViewById(R.id.rl);


        hasFlash = getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        text = (TextView) findViewById(R.id.timer);
        wheelview = (WheelView) findViewById(R.id.slot_1);
        initWheel(R.id.slot_1, cities);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        cap_btn = (ImageButton) findViewById(R.id.imagebutton_capture);
        tgb = (ToggleButton) findViewById(R.id.toggleButton1);
        ibRetake = (Button) findViewById(R.id.ibRetake);
        ibUse = (Button) findViewById(R.id.ibUse);
        turn = (Button) findViewById(R.id.turn);

        if (hasFlash) {
            tgb.setVisibility(View.VISIBLE);
            tgb.setChecked(true);
            tgb.setText(null);

            tgb.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (((ToggleButton) v).isChecked()) {
                        isFlashOn = true;
                        tgb.setText("");
                        tgb.setBackgroundResource(R.drawable.on_toggle);
                    } else {
                        isFlashOn = false;
                        tgb.setText("");
                        tgb.setBackgroundResource(R.drawable.off_toggle);
                    }
                    // Toast.makeText(getApplicationContext(),
                    // isFlashOn+"",Toast.LENGTH_LONG).show();

                }
            });
        } else {
            tgb.setVisibility(View.GONE);
        }

        if (Camera.getNumberOfCameras() >= 2) {
            turn.setVisibility(View.VISIBLE);
            turn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    turn();
                }
            });
        } else {
            turn.setVisibility(View.GONE);
        }

        sdRoot = Environment.getExternalStorageDirectory();
        dir = "/DCIM/Camera/";
        pictureCounter = MainActivity.checkTemplateNum;

        timerUpdateHandler = new Handler();
        timer = new Timer();



        ibRetake.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                File discardedPhoto = new File(sdRoot, dir + fileName);
                discardedPhoto.delete();


                getSharedPreferences("SavedImages", MODE_WORLD_WRITEABLE)
                        .edit().clear().commit();
                getSharedPreferences("images_count", MODE_WORLD_WRITEABLE)
                        .edit().clear().commit();

                turn.setVisibility(View.VISIBLE);
                cap_btn.setVisibility(View.VISIBLE);
                wheelview.setVisibility(View.VISIBLE);
                ibRetake.setVisibility(View.GONE);
                ibUse.setVisibility(View.GONE);
                camera.startPreview();


            }
        });


        ibUse.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                Log.e("", "uuuuuuuuuuuuuuu" + MainActivity.CameraArry.toString());

                Intent data = new Intent().putExtra("all_camera_path",MainActivity.CameraArry);
                setResult(RESULT_OK, data);
                finish();

            }
        });



        cap_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
				/* camera.takePicture(null, null, mPicture); */

                wheelview.setVisibility(View.GONE);
                timer();

                flag++;
            }
        });
    }

    public void turn() {
        // myCamera is the Camera object
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            // only for android older than gingerbread

            if (Camera.getNumberOfCameras() >= 2) {
                camera.stopPreview();
                camera.release();
                // "which" is just an integer flag
                switch (which) {
                    case 0:
                        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                        which = 1;
                        frontCamera = true;
                        break;
                    case 1:
                        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                        which = 0;
                        frontCamera = false;
                        break;
                }


                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    Camera.Parameters parameters = camera.getParameters();
                    if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                        parameters.set("orientation", "portrait");
                        parameters.setRotation(90);

                        camera.setDisplayOrientation(90);

                    }
                    if (isFlashOn) {
                        parameters
                                .setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    } else {
                        parameters
                                .setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                    camera.setParameters(parameters);
                } catch (IOException exception) {
                    camera.release();
                }
                camera.startPreview();
                vesion = 1;
            } else {
                AlertDialog.Builder ab = new AlertDialog.Builder(
                        CustomCameraActivity.this);
                ab.setMessage("Device Having Only one Camera");
                ab.setCancelable(false);
                ab.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub

                            }
                        }).show();

            }
        } else {
            AlertDialog.Builder ab1 = new AlertDialog.Builder(
                    CustomCameraActivity.this);
            ab1.setMessage("This Device Does Not Support Dual Camera Feature");
            ab1.setCancelable(false);
            ab1.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            }).show();
        }
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            startTime = Integer.parseInt(cities[wheel.getCurrentItem()]) * 1000;

            countDownTimer = new MyCountDownTimer(startTime, interval);

            // text.setText("");
            text.setText("" + String.valueOf(startTime / 1000));

        }
    };

    // Wheel changed listener
    OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {

            }
        }
    };

    private void initWheel(int id, String cities[]) {

        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                cities);

        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(1);
        wheel.setVisibleItems(2);

        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(true);
    }

    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }


    private void mixWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.scroll(-350 + (int) (Math.random() * 50), 2000);

    }

    public void timer() {
        countDownTimer.start();

    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("");
            TimerMethod();
        }

        @Override
        public void onTick(long millisUntilFinished) {

            text.setText("" + millisUntilFinished / 1000);
            String t1 = text.getText().toString();

        }
    }

    public void TimerMethod() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (hasFlash) {
                    Camera.Parameters parameters = camera.getParameters();

                    if (isFlashOn) {
                        parameters
                                .setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    } else {
                        parameters
                                .setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                    camera.setParameters(parameters);
                }

                camera.takePicture(null, null, mPicture);
                System.out.println("click.......");

            }

        });
    }

    private PictureCallback mPicture = new PictureCallback()
    {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            fileName = "orangeBooth"
                    + Long.toString(System.currentTimeMillis()) + ".jpg";

            // ///////

            if (data != null) {
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
                        (data != null) ? data.length : 0);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    System.out.println("============if looop-----------");
                    // Toast.makeText(getApplicationContext(),
                    // "if loop",Toast.LENGTH_LONG).show();
                    // Notice that width and height are reversed
                    Bitmap scaled = Bitmap.createScaledBitmap(bm, screenHeight,
                            screenWidth, true);
                    int w = scaled.getWidth();
                    int h = scaled.getHeight();

                    // Setting post rotate to 90
                    Matrix mtx = new Matrix();
                    System.out.println("frontCamera==" + frontCamera);
                    if (frontCamera) {
                        mtx.setRotate(270.0f);
                    } else {
                        mtx.postRotate(90);

                    }

                    // Rotating Bitmap

                    bm = Bitmap.createBitmap(scaled, 0, 0, w, h, mtx, true);

                    File mkDir = new File(sdRoot, dir);
                    if (!mkDir.exists()) {
                        mkDir.mkdirs();
                    }

                    File pictureFile = new File(sdRoot, dir + fileName);

                    try {

                        //thePic = "file://" + pictureFile.getAbsolutePath();
                        System.out.println("===thePic-------"+thePic);

                        File file=getOutputMediaFile();
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(data);
                        fos.close();
                     MainActivity.CameraArry.add(file.getAbsolutePath());



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    System.out.println("============else looop-----------");
                    Bitmap scaled = Bitmap.createScaledBitmap(bm, screenWidth,
                            screenHeight, true);
                    bm = scaled;

                    File mkDir = new File(sdRoot, dir);
                    if (!mkDir.exists()) {
                        mkDir.mkdirs();
                    }

                    File pictureFile = new File(sdRoot, dir + fileName);

                    try {


                        System.out.println("===thePic-------"+thePic);


                        File file=getOutputMediaFile();
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(data);
                        fos.close();
                        MainActivity.CameraArry.add(file.getAbsolutePath());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            pictureCounter--;
            imgNoCount = imgNoCount + 1;

            System.out.println("===imgNoCount-------"+imgNoCount+"==Total:-"+MainActivity.checkTemplateNum);

            if (imgNoCount == MainActivity.checkTemplateNum)
            {
                imgNoCount=0;
                turn.setVisibility(View.GONE);
                cap_btn.setVisibility(View.GONE);
                ibRetake.setVisibility(View.VISIBLE);
                ibUse.setVisibility(View.VISIBLE);


            } else {


                timer.cancel();
                camera.startPreview();

                timer();

            }

        }

    };

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (previewing) {
            camera.stopPreview();
            previewing = false;

        }

        if (camera != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);

                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (vesion == 1) {
            Camera.open(which);
        } else {
            camera = Camera.open(1);
        }

        setCameraDisplayOrientation(CustomCameraActivity.this,
                CameraInfo.CAMERA_FACING_BACK, camera);


        try {
            camera.setPreviewDisplay(holder);
            Camera.Parameters parameters = camera.getParameters();
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                parameters.setRotation(90);
                camera.setDisplayOrientation(90);
            }
            if (isFlashOn) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            }
            camera.setParameters(parameters);
        } catch (IOException exception) {
            camera.release();
        }
        camera.startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    @Override
    public void onBackPressed() {

    }

    private File getOutputMediaFile() {
        File fileDir = null;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            fileDir = new File(Environment.getExternalStorageDirectory(), "Collage Photo Editor");
        }  else {
            fileDir = new File(getFilesDir(), "Collage Photo Editor");
        }
        if(fileDir != null && !fileDir.exists()) {
            if(!fileDir.mkdir()) {

                return null;
            }
        }

        String time = new SimpleDateFormat("MM_dd_yyyy__HH_mm_ss_SSS").format(new Date());
        File file = new File(fileDir.getPath() + File.separator + time + "_photo" + ".jpg");//TODO check formate is ok?
        return file;
    }


}
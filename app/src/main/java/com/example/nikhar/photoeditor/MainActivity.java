package com.example.nikhar.photoeditor;

import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.drm.DrmStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatBase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Menu menu;
    private LinearLayout ll_home_one;
    private LinearLayout ll_home_two;
    private LinearLayout ll_home_three;
    private LinearLayout ll_home_four;
    private LinearLayout ll_home_five;
    private LinearLayout ll_home_six;
    public static int checkTemplateNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ll_home_one = (LinearLayout) findViewById(R.id.ll_one);
        ll_home_two = (LinearLayout) findViewById(R.id.ll_two);
        ll_home_three = (LinearLayout) findViewById(R.id.ll_three);
        ll_home_four = (LinearLayout) findViewById(R.id.ll_four);
        ll_home_five = (LinearLayout) findViewById(R.id.ll_five);
        ll_home_six = (LinearLayout) findViewById(R.id.ll_six);


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
            startActivity(new Intent(MainActivity.this,CustomGalleryActivity.class));
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


}

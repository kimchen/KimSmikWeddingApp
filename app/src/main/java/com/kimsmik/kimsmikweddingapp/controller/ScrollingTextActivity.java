package com.kimsmik.kimsmikweddingapp.controller;

import com.kimsmik.kimsmikweddingapp.R;
import com.kimsmik.kimsmikweddingapp.object.MarqueeTextView;
import com.kimsmik.kimsmikweddingapp.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.security.Policy;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ScrollingTextActivity extends Activity {

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    private MarqueeTextView srollingText;
    private Camera camera;
    private boolean isLightOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();

        String message = getIntent().getStringExtra("message");
        setContentView(R.layout.activity_scrolling_text);
        srollingText = (MarqueeTextView)findViewById(R.id.scrollingText);
        if(message != null && !message.equals(""))
            srollingText.setText(message);
        srollingText.scrollText(2f);

        View bgView = findViewById(R.id.bgView);
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, bgView, HIDER_FLAGS);
        mSystemUiHider.setup();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            camera = Camera.open();
        }
        if(camera != null){
            Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            isLightOn = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(camera != null && isLightOn){
            Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
            isLightOn = false;
        }
        if(camera != null){
            camera.release();
            camera = null;
        }
    }

//    private void StartScrolling(){
//        srollingText.setX(1000);
//        srollingText.animate().translationX(-1000).setDuration(3000).withEndAction(new Runnable(){
//            @Override
//            public void run() {
//                mHideHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        StartScrolling();
//                    }
//                });
//            }
//        }).start();
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };
    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}

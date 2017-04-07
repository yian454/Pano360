package com.martin.ads.pano360demo;

import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.martin.ads.vrlib.PanoPlayerActivity;
import com.martin.ads.vrlib.PanoViewWrapper;

/**
 * Created by Ads on 2016/6/25.
 */
public class DemoWithGLSurfaceView extends AppCompatActivity {
    private PanoViewWrapper panoViewWrapper;

    public static String TAG = "DemoWithGLSurfaceView";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getSupportActionBar().hide();
        setContentView(R.layout.player_layout);

        init();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        getWindow().setAttributes(params);
    }

    private void init(){

        String filePath=getIntent().getStringExtra(PanoPlayerActivity.FILE_PATH);
        GLSurfaceView glSurfaceView=(GLSurfaceView) findViewById(R.id.surface_view);
        panoViewWrapper =PanoViewWrapper.with(this)
                .setFilePath(filePath)
                .setGlSurfaceView(glSurfaceView)
                .setImageMode(false)
                .setPlaneMode(false)
                .init()
                .removeDefaultHotSpot();
        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Logger.logTouchEvent(v,event);
                return panoViewWrapper.handleTouchEvent(event);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        panoViewWrapper.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        panoViewWrapper.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        panoViewWrapper.releaseResources();
    }

}

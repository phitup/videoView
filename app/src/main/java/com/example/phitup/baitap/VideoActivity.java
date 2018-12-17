package com.example.phitup.baitap;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView mVideoView;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mVideoView = findViewById(R.id.videoView);
        mc = new MyMediaController(this, (FrameLayout) findViewById(R.id.videoViewWrapper), height);
//        mc = new MediaController(this);
        mVideoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.demo);
//
//        mVideoView.setMediaController(new MediaController(VideoActivity.this));
//        mVideoView.requestFocus();
        mVideoView.setMediaController(mc);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mc.setBackgroundColor(getResources().getColor(R.color.red));

                mVideoView.start();

                FrameLayout controllerAnchor = findViewById(R.id.videoViewWrapper);
                mc.setAnchorView(controllerAnchor);

//                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                    @Override
//                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//
//                        mc = new MediaController(VideoActivity.this);
//                        mVideoView.setMediaController(mc);
//                        mc.setAnchorView(mVideoView);
//                        ((ViewGroup) mc.getParent()).removeView(mc);
//                        ((FrameLayout) findViewById(R.id.videoViewWrapper))
//                                .addView(mc);
//                        mc.setVisibility(View.INVISIBLE);
//
//                    }
//                });
//                mVideoView.start();
//            // The End SetOnPrepared
            }
        });

//        mVideoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(mc != null){
//                    mc.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mc.setVisibility(View.INVISIBLE);
//                        }
//                    },2000);
//                }
//                return false;
//            }
//        });

    }
}




//
//        mediaController = new MediaController(this);
//
//        mediaController.setPadding(0,0,0,400);
//
//        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.demo);
//
//        mediaController.setAnchorView(videoView);
//
//        videoView.setMediaController(mediaController);
//
//        videoView.start();
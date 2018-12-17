package com.example.phitup.baitap;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class Video2Activity extends AppCompatActivity {

    SeekBar mSeekBar;
    VideoView mVideoView, mVideoView1;
    Handler mHandler = new Handler();
    Utilities utils;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);

//        txtCurrent = findViewById(R.id.textViewCurrent);
//        txtTotal = findViewById(R.id.textViewTotal);

        mSeekBar = findViewById(R.id.ProgressBar);
        mSeekBar.setProgress(0);
        mSeekBar.setMax(100);
        mSeekBar.setVisibility(View.INVISIBLE);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mVideoView = findViewById(R.id.my_video_view);
        mVideoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.demo);
        mVideoView.start();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(updateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(updateTimeTask);
                int totalDuration = mVideoView.getDuration();
                int currentPosition = utils.progressToTimer(mSeekBar.getProgress(),
                        totalDuration);

                // forward or backward to certain seconds
                mVideoView.seekTo(currentPosition);

                // update timer progress again
                updateProgressBar();
            }
        });

        utils = new Utilities();

        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setContentView(R.layout.vertical_fullscreen);
                mVideoView1 = findViewById(R.id.my_video_view1);
                mVideoView1.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.demo);
                mVideoView1.start();

                return true;
            }
        });
//        mVideoView.start();

        updateProgressBar();

    }

    private void updateProgressBar() {
        mHandler.postDelayed(updateTimeTask, 100);
    }

    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mVideoView.getDuration();
            long currentDuration = mVideoView.getCurrentPosition();
            int progress = (utils.getProgressPercentage(currentDuration,
                    totalDuration));
//            String Current = utils.milliSecondsToTimer(currentDuration);
//            String Total = utils.milliSecondsToTimer(totalDuration);
//            txtCurrent.setText(Current);
//            txtTotal.setText(Total);
            mSeekBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };
}

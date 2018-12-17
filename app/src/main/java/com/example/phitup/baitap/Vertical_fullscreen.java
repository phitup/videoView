package com.example.phitup.baitap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Vertical_fullscreen extends AppCompatActivity {

    VideoView mVideoView1;
    SeekBar mSeekBar;
    Handler mHandler = new Handler();
    Utilities utils;
    TextView txtTotal, txtCurrent;
    FrameLayout mediaController, layoutFilm;
    ImageView imgPause,imgPlay;
    int currentPosition= 0;
    int progress = 0;
    int length = 0;
    long totalDuration = 0;
    String videoUrl, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.vertical_fullscreen);

        AnhXa();

        if (savedInstanceState != null){
            Log.d("BBB", "Saved");
            int progress = savedInstanceState.getInt("videoProgress");
            length = savedInstanceState.getInt("playProgress");
            Log.d("BBB", "Saved : " + length);
            mVideoView1.seekTo(progress);
        }

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
                totalDuration = mVideoView1.getDuration();
                currentPosition = utils.progressToTimer(mSeekBar.getProgress(), (int)totalDuration);
                mVideoView1.seekTo(currentPosition);
                updateProgressBar();
            }
        });
        mVideoView1.start();

        updateProgressBar();

        imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView1.pause();
                length = mVideoView1.getCurrentPosition();
                imgPause.setVisibility(View.INVISIBLE);
                imgPlay.setVisibility(View.VISIBLE);
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPlay.setVisibility(View.INVISIBLE);
                imgPause.setVisibility(View.VISIBLE);
                mVideoView1.seekTo(length);
                mVideoView1.start();
            }
        });

        layoutFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.postDelayed(updateTimeTaskHide, 2000);
                mediaController.setVisibility(View.VISIBLE);
            }
        });

//        new videoSyncTask().execute();

    }

    private Runnable updateTimeTaskHide = new Runnable() {
        @Override
        public void run() {
            mediaController.setVisibility(View.INVISIBLE);
        }
    };

    private void AnhXa() {
        mSeekBar = findViewById(R.id.ProgressBar);
        mVideoView1 = findViewById(R.id.my_video_view1);
        Intent intent = getIntent();
        path = intent.getStringExtra("Url");
        if(path != null){
            Uri uri = Uri.parse(path);
            mVideoView1.setVideoURI(uri);
        }else{
            Log.d("BBB", "Link không tồn tại");
            finish();
        }
        mediaController = findViewById(R.id.mediaController);
        layoutFilm = findViewById(R.id.layoutFilm);

        mediaController.setVisibility(View.INVISIBLE);

        imgPause = findViewById(R.id.ImgPause);
        imgPlay = findViewById(R.id.ImgPlay);
        imgPlay.setVisibility(View.INVISIBLE);

        txtCurrent = findViewById(R.id.textViewCurrent);
        txtTotal = findViewById(R.id.textViewTotal);

        mSeekBar.setProgress(0);
        mSeekBar.setMax(100);

        utils = new Utilities();

    }

    private void updateProgressBar() {
        mHandler.postDelayed(updateTimeTask, 100);
    }

    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mVideoView1.getDuration();
            long currentDuration = mVideoView1.getCurrentPosition();
            progress = (utils.getProgressPercentage(currentDuration,
                    totalDuration));
            String Current = utils.milliSecondsToTimer(currentDuration);
            String Total = utils.milliSecondsToTimer(totalDuration);
            txtCurrent.setText(Current);
            txtTotal.setText(Total);
            mSeekBar.setProgress(progress);
            length = mVideoView1.getCurrentPosition();
            currentPosition = utils.progressToTimer(mSeekBar.getProgress(), (int)totalDuration);
            mHandler.postDelayed(this, 100);

        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("videoProgress", currentPosition);
        outState.putInt("playProgress", length);
    }

    private class videoSyncTask extends AsyncTask<Void, Void, Void>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Vertical_fullscreen.this, "", "Loading Video wait...", true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                String url = path;
                videoUrl = getUrlVideoRTSP(url);
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            mVideoView1.setVideoURI(Uri.parse(videoUrl));
            mVideoView1.start();
        }
    }

    private String getUrlVideoRTSP(String urlYoutube) {
        try
        {
            String gdy = "http://gdata.youtube.com/feeds/api/videos/";
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String id = extractYoutubeId(urlYoutube);
            URL url = new URL(gdy + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Document doc = documentBuilder.parse(connection.getInputStream());
            Element el = doc.getDocumentElement();
            NodeList list = el.getElementsByTagName("media:content");///media:content
            String cursor = urlYoutube;
            for (int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                if (node != null)
                {
                    NamedNodeMap nodeMap = node.getAttributes();
                    HashMap<String, String> maps = new HashMap<String, String>();
                    for (int j = 0; j < nodeMap.getLength(); j++)
                    {
                        Attr att = (Attr) nodeMap.item(j);
                        maps.put(att.getName(), att.getValue());
                    }
                    if (maps.containsKey("yt:format"))
                    {
                        String f = maps.get("yt:format");
                        if (maps.containsKey("url"))
                        {
                            cursor = maps.get("url");
                        }
                        if (f.equals("1"))
                            return cursor;
                    }
                }
            }
            return cursor;
        }
        catch (Exception ex)
        {
        }
        return urlYoutube;
    }

    protected static String extractYoutubeId(String url) throws MalformedURLException {        String id = null;
        try
        {
            String query = new URL(url).getQuery();
            if (query != null)
            {
                String[] param = query.split("&");
                for (String row : param)
                {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("v"))
                    {
                        id = param1[1];
                    }
                }
            }
            else
            {
                if (url.contains("embed"))
                {
                    id = url.substring(url.lastIndexOf("/") + 1);
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("Exception", ex.toString());
        }
        return id;
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            txtCurrent.setVisibility(View.VISIBLE);
//            txtTotal.setVisibility(View.VISIBLE);
//        }
//        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            txtCurrent.setVisibility(View.INVISIBLE);
//            txtTotal.setVisibility(View.INVISIBLE);
//        }

//    }
}

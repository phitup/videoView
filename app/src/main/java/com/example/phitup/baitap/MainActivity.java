package com.example.phitup.baitap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imageView, imageViewPlay;
    FrameLayout frameLayout;
    TextView txtCommentScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        AnhXa();


        ActionBar();

        final String path = "https://r8---sn-8pxuuxa-nboel.googlevideo.com/videoplayback?pl=21&id=o-ABSgT1nPpeFZNM3RqOGlLSL9BmCUrjRlCxCdEV8x1s2L&itag=22&ipbits=0&ip=80.76.187.226&fvip=14&requiressl=yes&sparams=dur,ei,expire,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&source=youtube&lmt=1526315911204321&dur=152.206&ratebypass=yes&signature=752AFDF6454FECCF9C9DA6DD1734FFFF09A739BD.4284086164FE8CB8C21775A981B4FE6A71459564&expire=1545050109&c=WEB&ei=nUMXXIWeAoT-7ASjuYLgCw&key=cms1&mime=video%2Fmp4&video_id=JOddp-nlNvQ&title=Thor+-+Trailer+%28OFFICIAL%29&rm=sn-385ou-8v1s76,sn-n8vrlez&fexp=23763603&req_id=50f1526abb64a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=116.102.0.2&mm=29&mn=sn-8pxuuxa-nboel&ms=rdu&mt=1545028441&mv=m";
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Vertical_fullscreen.class);
                intent.putExtra("Url", path);
                startActivity(intent);
            }
        });

        txtCommentScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CommentActivity.class));
            }
        });

    }

    private void AnhXa() {
        imageView = findViewById(R.id.imageView);
        imageViewPlay = findViewById(R.id.imageViewPlay);
        frameLayout = findViewById(R.id.frameLayoutToolbar);
        txtCommentScreen = findViewById(R.id.txtCommentScreen);
    }

    private void ActionBar(){
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}

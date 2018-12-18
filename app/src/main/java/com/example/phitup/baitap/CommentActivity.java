package com.example.phitup.baitap;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class CommentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText edtComment;
    String mCurrentUserId;

    ImageView imageViewSend;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    FirebaseRecyclerOptions<comment> options;
    FirebaseRecyclerAdapter<comment, RecyclerComment> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comment);

        AnhXa();

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendComment();
            }
        });

        UpdateComment();

    }

    private void AnhXa() {
        imageViewSend = findViewById(R.id.ImgSendComment);
        edtComment = findViewById(R.id.edtComment);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
    }

    private void UpdateComment() {
        options = new FirebaseRecyclerOptions.Builder<comment>()
                .setQuery(mRootRef.child("Comments"), comment.class).build();

        adapter = new FirebaseRecyclerAdapter<comment, RecyclerComment>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerComment holder, int position, @NonNull comment model) {
                holder.txtName.setText(model.getName());
                holder.txtContent.setText(model.getContent());

            }

            @NonNull
            @Override
            public RecyclerComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
                return new RecyclerComment(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    private void SendComment(){
        String Comment = edtComment.getText().toString();

        if(!Comment.isEmpty()){

            comment mComment = new comment(mAuth.getCurrentUser().getEmail(),Comment, String.valueOf(ServerValue.TIMESTAMP));

            mRootRef.child("Comments").push().setValue(mComment);
            adapter.notifyDataSetChanged();
            edtComment.setText("");

        }else{
            Toast.makeText(this, "Bình luận không thể để trống", Toast.LENGTH_SHORT).show();
        }

    }

}

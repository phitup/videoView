package com.example.phitup.baitap;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerComment extends RecyclerView.ViewHolder {

    TextView txtName, txtContent, txtTime;

    public RecyclerComment(View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.txtName);
        txtContent = itemView.findViewById(R.id.txtContent);
        txtTime = itemView.findViewById(R.id.txtTime);
    }

}

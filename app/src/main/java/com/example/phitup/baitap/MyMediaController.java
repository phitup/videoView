package com.example.phitup.baitap;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;

public class MyMediaController extends MediaController {

    private FrameLayout anchorView;
    private int height;

    public MyMediaController(Context context, FrameLayout anchorView , int height) {
        super(context);
        this.anchorView = anchorView;
        this.height = height;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) anchorView.getLayoutParams();
        lp.setMargins(0, 0, 0, height - 500);

        anchorView.setLayoutParams(lp);
        anchorView.requestLayout();

    }
}

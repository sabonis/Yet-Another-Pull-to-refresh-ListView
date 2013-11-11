package com.example.testpulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

/**
 * Created by sabonis on 11/4/13.
 */
public class LoadingView extends FrameLayout {

    private static final long ROTATION_ANIMATION_DURATION = 1200;
    private final View mInnerView;
    private final View mRefreshIcon;
    private final RotateAnimation mAnimation;

    public LoadingView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.header_view, this);
        mInnerView = findViewById(R.id.inner_view);
        mRefreshIcon = findViewById(R.id.refresh_icon);

        mAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
    }

    public int getHeaderHeight() {
        return mInnerView.getHeight();
    }

    public void setHeight(int height) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    public void refresh() {
        //mRefreshIcon.animate().rotation(100);
        mRefreshIcon.startAnimation(mAnimation);
    }
    public void stop() {
        mRefreshIcon.getAnimation().cancel();
        mRefreshIcon.getAnimation().reset();
    }

}

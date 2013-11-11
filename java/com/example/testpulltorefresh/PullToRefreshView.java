package com.example.testpulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by sabonis on 10/18/13.
 */
public class PullToRefreshView extends LinearLayout {
    private ListView mListView;
    private LoadingView mHeaderView;
    private float mLastY;
    private float mInitialY;

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListView = _createListView(context);
        mHeaderView = new LoadingView(context);
        addView(mHeaderView);
        addView(mListView);
        mHeaderView.setHeight(200);
        setPadding(getPaddingLeft(), -200, getPaddingRight(), getPaddingBottom());
        //scrollTo(0, mHeaderView.getHeaderHeight());
    }

    public ListView getListView() {
        return mListView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("test onInterceptTouchEvent", String.valueOf(ev.getActionMasked()));
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_MOVE) {
            mLastY = ev.getY();
        }
        if(action == MotionEvent.ACTION_DOWN) {
            mInitialY = ev.getY();
        }

        if(mLastY - mInitialY > 0 && getListView().getFirstVisiblePosition() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            mLastY = event.getY();
            Log.d("test", "y = " + (mInitialY - mLastY));
            scrollTo(0, (int) (mInitialY - mLastY));
        }
        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            if(mInitialY - mLastY < -100) {
                Log.d("test", "refresh executed");
                //scrollTo(0, -200);
                post(new MyRunnable(getScrollY(), -200));
                mHeaderView.refresh();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        post(new MyRunnable(getScrollY(), 0));
                        mHeaderView.stop();
                    }
                }, 1000);
                return true;
            }

            post(new MyRunnable(getScrollY(), 0));
        }

        Log.d("test", String.valueOf(event.getActionMasked()));
        return true;
    }


    private ListView _createListView(Context context) {
        ListView listView = new ListView(context);
        return listView;
    }

    private class MyRunnable implements Runnable {
        public int mCurrentY = -1;
        float marker = 0.05f;
        private int mScrollY;
        private int mEnd;

        private MyRunnable(int start, int end) {
            mScrollY = start;
            mEnd = end;
        }

        @Override
        public void run() {
            Log.d("test", "y = " + getScrollY() + ", interpolator = " + new DecelerateInterpolator().getInterpolation(marker));
            int deltaY = (int) (mScrollY * new DecelerateInterpolator().getInterpolation(marker));
            Log.d("test", "deltay = " + deltaY);
            mCurrentY = mScrollY - deltaY;
            Log.d("test", "mCurrentY = " + mCurrentY);
            if(mCurrentY > mEnd) {
                mCurrentY = mEnd;
                Log.d("test", "mCurrentY = " + mCurrentY);
            }
            scrollTo(0, mCurrentY);
            marker += .05;
            if (mCurrentY != mEnd) {
                postOnAnimation(this);
            }
        }
    }
}

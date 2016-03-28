package com.santiago.stuff;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.santiago.snapchatscrolls.R;

/**
 * Created by santiago on 27/03/16.
 */
public abstract class SnapchatViewPager extends FrameLayout {

    public enum SLIDING_MODE { HORIZONTAL, VERTICAL, UNDEFINED }

    private ViewPager horizontalViewPager;
    private VerticalViewPager verticalViewPager;
    private View mask;

    public SnapchatViewPager(Context context) {
        this(context, null);
    }

    public SnapchatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.view_pager_snapchat, this);

        horizontalViewPager = (ViewPager) findViewById(R.id.view_pager_snapchat_horizontal);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.view_pager_snapchat_vertical);
        mask = findViewById(R.id.view_pager_snapchat_mask);

        mask.setOnTouchListener(onMaskTouch);
    }

    public int getCurrentX() {
        return horizontalViewPager.getCurrentItem();
    }

    public int getCurrentY() {
        return verticalViewPager.getCurrentItem();
    }

    public void setHorizontalAdapter(PagerAdapter adapter) {
        horizontalViewPager.setAdapter(adapter);
    }

    public void setVerticalAdapter(PagerAdapter adapter) {
        verticalViewPager.setAdapter(adapter);
    }

    public void setPage(SLIDING_MODE mode, int i) {
        switch (mode) {
            case VERTICAL:
                verticalViewPager.setCurrentItem(i);
                break;

            case HORIZONTAL:
                horizontalViewPager.setCurrentItem(i);
        }
    }

    protected abstract boolean handleHorizontalTouchEvent();
    protected abstract boolean handleVerticalTouchEvent();

    private OnTouchListener onMaskTouch = new OnTouchListener() {

        SLIDING_MODE mode = SLIDING_MODE.UNDEFINED;

        float startX = 0;
        float startY = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mode = SLIDING_MODE.UNDEFINED;
                    startX = event.getX();
                    startY = event.getY();

                    verticalViewPager.onTouchEvent(event);
                    horizontalViewPager.onTouchEvent(event);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode == SLIDING_MODE.UNDEFINED)
                        mode = (Math.abs(event.getX() - startX) > Math.abs(event.getY() - startY) ? SLIDING_MODE.HORIZONTAL : SLIDING_MODE.VERTICAL);

                    switch (mode) {
                        case HORIZONTAL:
                            if (handleHorizontalTouchEvent()) {
                                horizontalViewPager.bringToFront();
                                horizontalViewPager.onTouchEvent(event);
                            }
                            break;

                        case VERTICAL:
                            if (handleVerticalTouchEvent()) {
                                verticalViewPager.bringToFront();
                                verticalViewPager.onTouchEvent(event);
                            }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    startX = 0;
                    startY = 0;
                    mode = SLIDING_MODE.UNDEFINED;

                    verticalViewPager.onTouchEvent(event);
                    horizontalViewPager.onTouchEvent(event);

                    mask.bringToFront();
            }

            return true;

        }

    };

}

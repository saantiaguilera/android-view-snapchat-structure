package com.santiago.multioriented_pager.viewpager;

import android.content.Context;
import android.graphics.Point;
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
public abstract class MultiOrientedViewPager extends FrameLayout {

    public enum SLIDE { HORIZONTAL, VERTICAL, UNDEFINED }
    public enum ORIENTATION { TOP, BOTTOM, LEFT, RIGHT }

    private ViewPager horizontalViewPager;
    private VerticalViewPager verticalViewPager;
    private View mask;

    public MultiOrientedViewPager(Context context) {
        this(context, null);
    }

    public MultiOrientedViewPager(Context context, AttributeSet attrs) {
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

    public void setPage(SLIDE mode, int i) {
        switch (mode) {
            case VERTICAL:
                verticalViewPager.setCurrentItem(i);
                break;

            case HORIZONTAL:
                horizontalViewPager.setCurrentItem(i);
        }
    }

    protected abstract boolean handleTouchEvent(Point point, MotionEvent event, SLIDE mode, ORIENTATION orientation);

    private OnTouchListener onMaskTouch = new OnTouchListener() {

        SLIDE mode = SLIDE.UNDEFINED;

        float startX = 0;
        float startY = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mode = SLIDE.UNDEFINED;
                    startX = event.getX();
                    startY = event.getY();

                    verticalViewPager.onTouchEvent(event);
                    horizontalViewPager.onTouchEvent(event);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode == SLIDE.UNDEFINED)
                        mode = (Math.abs(event.getX() - startX) > Math.abs(event.getY() - startY) ? SLIDE.HORIZONTAL : SLIDE.VERTICAL);

                    switch (mode) {
                        case HORIZONTAL:
                            if (handleTouchEvent(new Point((int) startX, (int) startY), event, SLIDE.HORIZONTAL, startX < event.getX() ? ORIENTATION.LEFT : ORIENTATION.RIGHT)) {
                                horizontalViewPager.bringToFront();
                                horizontalViewPager.onTouchEvent(event);
                            }
                            break;

                        case VERTICAL:
                            if (handleTouchEvent(new Point((int) startX, (int) startY), event, SLIDE.VERTICAL, startY > event.getY() ? ORIENTATION.BOTTOM : ORIENTATION.TOP)) {
                                verticalViewPager.bringToFront();
                                verticalViewPager.onTouchEvent(event);
                            }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    startX = 0;
                    startY = 0;
                    mode = SLIDE.UNDEFINED;

                    verticalViewPager.onTouchEvent(event);
                    horizontalViewPager.onTouchEvent(event);

                    mask.bringToFront();
            }

            return true;
        }
    };

}

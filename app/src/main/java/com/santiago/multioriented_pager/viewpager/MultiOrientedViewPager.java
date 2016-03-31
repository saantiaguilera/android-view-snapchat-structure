package com.santiago.multioriented_pager.viewpager;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.santiago.multioriented_pager.adapter.ViewAdapter;
import com.santiago.snapchatscrolls.R;

import java.util.List;

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

    public void setOnHorizontalPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        horizontalViewPager.setOnPageChangeListener(onPageChangeListener);
    }

    public void setOnVerticalPageChangeListener(ViewPager.OnPageChangeListener onVerticalPageChangeListener) {
        verticalViewPager.setOnPageChangeListener(onVerticalPageChangeListener);
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

    protected ViewPager getHorizontalViewPager() {
        return horizontalViewPager;
    }

    protected VerticalViewPager getVerticalViewPager() {
        return verticalViewPager;
    }

    protected abstract boolean handleTouchEvent(Point point, MotionEvent event, SLIDE mode, ORIENTATION orientation);

    private OnTouchListener onMaskTouch = new OnTouchListener() {

        SLIDE mode = SLIDE.UNDEFINED;

        private Point point = null;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mode = SLIDE.UNDEFINED;
                    point = new Point((int) event.getX(), (int) event.getY());

                    verticalViewPager.dispatchTouchEvent(event);
                    horizontalViewPager.dispatchTouchEvent(event);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode == SLIDE.UNDEFINED) {
                        mode = (Math.abs(event.getX() - point.x) > Math.abs(event.getY() - point.y) ? SLIDE.HORIZONTAL : SLIDE.VERTICAL);
                    }

                    switch (mode) {
                        case HORIZONTAL:
                            if (handleTouchEvent(point, event, SLIDE.HORIZONTAL, point.x < event.getX() ? ORIENTATION.LEFT : ORIENTATION.RIGHT)) {
                                horizontalViewPager.bringToFront();
                                horizontalViewPager.dispatchTouchEvent(event);
                            }
                            break;

                        case VERTICAL:
                            if (handleTouchEvent(point, event, SLIDE.VERTICAL, point.y > event.getY() ? ORIENTATION.BOTTOM : ORIENTATION.TOP)) {
                                verticalViewPager.bringToFront();
                                verticalViewPager.dispatchTouchEvent(event);
                            }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    verticalViewPager.dispatchTouchEvent(event);
                    horizontalViewPager.dispatchTouchEvent(event);
                    handleTouchEvent(point, event, mode, null);

                    point = null;
                    mode = SLIDE.UNDEFINED;

                    mask.bringToFront();
            }

            return true;
        }
    };

}

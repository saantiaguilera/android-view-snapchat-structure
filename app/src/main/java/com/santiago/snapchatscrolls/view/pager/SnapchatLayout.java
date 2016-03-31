package com.santiago.snapchatscrolls.view.pager;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;

/**
 * Created by santiago on 28/03/16.
 */
public class SnapchatLayout extends LinearLayout {

    private SnapchatLayoutListener listener;

    public SnapchatLayout(Context context) {
        this(context, null);
    }

    public SnapchatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListener(SnapchatLayoutListener listener) {
        this.listener = listener;
    }

    public boolean canScroll(Point point, MotionEvent event, MultiOrientedViewPager.SLIDE mode, MultiOrientedViewPager.ORIENTATION orientation) {
        if (listener != null)
            return listener.canScroll(point, event, mode, orientation);
        return true;
    }

    public interface SnapchatLayoutListener {
        boolean canScroll(Point point, MotionEvent event, MultiOrientedViewPager.SLIDE mode, MultiOrientedViewPager.ORIENTATION orientation);
    }

}

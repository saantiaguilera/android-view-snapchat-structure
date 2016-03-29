package com.santiago.snapchatscrolls.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;
import com.santiago.snapchatscrolls.pager.SnapchatLayout;

/**
 * Created by santiago on 28/03/16.
 */
public class EmptyView extends SnapchatLayout {

    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canScroll(Point point, MotionEvent event, MultiOrientedViewPager.SLIDE mode, MultiOrientedViewPager.ORIENTATION orientation) {
        return true;
    }

}

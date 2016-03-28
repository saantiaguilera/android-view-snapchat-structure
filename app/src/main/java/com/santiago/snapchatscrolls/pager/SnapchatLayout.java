package com.santiago.snapchatscrolls.pager;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;

/**
 * Created by santiago on 28/03/16.
 */
public abstract class SnapchatLayout extends LinearLayout {

    public SnapchatLayout(Context context) {
        this(context, null);
    }

    public SnapchatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract boolean canScroll(Point point, MultiOrientedViewPager.SLIDING_MODE mode, boolean toLeft);

}

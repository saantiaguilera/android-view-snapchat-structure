package com.santiago.snapchatscrolls;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;

import com.santiago.stuff.SnapchatViewPager;
import com.santiago.stuff.ViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 28/03/16.
 */
public class MockSVP extends SnapchatViewPager {

    public MockSVP(Context context) {
        this(context, null);
    }

    public MockSVP(Context context, AttributeSet attrs) {
        super(context, attrs);

        initHorizontalViewPager();
        initVerticalViewPager();

        setPage(SLIDING_MODE.VERTICAL, 1);
        setPage(SLIDING_MODE.HORIZONTAL, 1);
    }

    private void initVerticalViewPager() {
        List<View> verticalViews = new ArrayList<>();

        View view0 = new View(getContext());
        view0.setBackgroundColor(getResources().getColor(R.color.transparent));

        View view1 = new View(getContext());
        view1.setBackgroundColor(getResources().getColor(R.color.color5));

        verticalViews.add(view1);
        verticalViews.add(view0);
        setVerticalAdapter(new ViewAdapter(verticalViews));
    }

    private void initHorizontalViewPager() {
        List<View> horizontalViews = new ArrayList<>();
        View view1 = new View(getContext());
        view1.setBackgroundColor(getResources().getColor(R.color.color1));

        View view2 = new View(getContext());
        view2.setBackgroundColor(getResources().getColor(R.color.color2));

        View view3 = new View(getContext());
        view3.setBackgroundColor(getResources().getColor(R.color.color3));

        View view4 = new View(getContext());
        view4.setBackgroundColor(getResources().getColor(R.color.color4));

        horizontalViews.add(view1);
        horizontalViews.add(view2);
        horizontalViews.add(view3);
        horizontalViews.add(view4);
        setHorizontalAdapter(new ViewAdapter(horizontalViews));
    }

    @Override
    protected boolean handleHorizontalTouchEvent() {
        return getCurrentY() == 1;
    }

    @Override
    protected boolean handleVerticalTouchEvent() {
        return getCurrentX() == 1;
    }
}

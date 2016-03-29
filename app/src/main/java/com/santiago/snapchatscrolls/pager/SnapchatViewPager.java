package com.santiago.snapchatscrolls.pager;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;
import com.santiago.multioriented_pager.adapter.ViewAdapter;
import com.santiago.snapchatscrolls.R;
import com.santiago.snapchatscrolls.view.ContactListView;
import com.santiago.snapchatscrolls.view.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 28/03/16.
 */
public class SnapchatViewPager extends MultiOrientedViewPager {

    private List<SnapchatLayout> horizontalViews;

    public SnapchatViewPager(Context context) {
        this(context, null);
    }

    public SnapchatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        initHorizontalViewPager();
        initVerticalViewPager();

        setPage(SLIDE.VERTICAL, 1);
        setPage(SLIDE.HORIZONTAL, 2);
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
        horizontalViews = new ArrayList<>();
        SnapchatLayout view0 = new EmptyView(getContext());
        view0.setBackgroundColor(getResources().getColor(R.color.black));

        SnapchatLayout view1 = new ContactListView(getContext());
        view1.setBackgroundColor(getResources().getColor(R.color.color1));

        SnapchatLayout view2 = new EmptyView(getContext());
        view2.setBackgroundColor(getResources().getColor(R.color.color2));

        SnapchatLayout view3 = new EmptyView(getContext());
        view3.setBackgroundColor(getResources().getColor(R.color.color3));

        SnapchatLayout view4 = new EmptyView(getContext());
        view4.setBackgroundColor(getResources().getColor(R.color.color4));

        horizontalViews.add(view0);
        horizontalViews.add(view1);
        horizontalViews.add(view2);
        horizontalViews.add(view3);
        horizontalViews.add(view4);
        setHorizontalAdapter(new ViewAdapter(horizontalViews));
    }

    @Override
    protected boolean handleTouchEvent(Point point, MotionEvent event, SLIDE mode, ORIENTATION orientation) {
         switch (mode) {
             case VERTICAL:
                 return getCurrentX() == 2;

             case HORIZONTAL:
                 if(getCurrentY() == 0)
                     return false;

                 return horizontalViews.get(getCurrentX()).canScroll(point, event, mode, orientation);
         }
        
        return true;
    }

}

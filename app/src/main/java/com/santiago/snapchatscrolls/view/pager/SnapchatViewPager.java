package com.santiago.snapchatscrolls.view.pager;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
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
    private List<SnapchatLayout> verticalViews;

    public SnapchatViewPager(Context context) {
        this(context, null);
    }

    public SnapchatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //TODO REFACTOR THIS. WE SHOULD SIMPLY ADD TO THE ADAPTER NOT DO THIS

    public void setHorizontalViews(List<SnapchatLayout> horizontalViews) {
        this.horizontalViews = horizontalViews;
        setHorizontalAdapter(new ViewAdapter(horizontalViews));
    }

    public void setVerticalViews(List<SnapchatLayout> verticalViews) {
        this.verticalViews = verticalViews;
        setVerticalAdapter(new ViewAdapter(verticalViews));
    }

    @Override
    protected boolean handleTouchEvent(Point point, MotionEvent event, SLIDE mode, ORIENTATION orientation) {
         switch (mode) {
             case UNDEFINED:
                 //Just notify
                 verticalViews.get(getCurrentY()).canScroll(point, event, mode, orientation);
                 horizontalViews.get(getCurrentX()).canScroll(point, event, mode, orientation);
                 return true;

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

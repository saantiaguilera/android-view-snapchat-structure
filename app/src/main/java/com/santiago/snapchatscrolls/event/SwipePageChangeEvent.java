package com.santiago.snapchatscrolls.event;

import com.santiago.event.Event;
import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;

/**
 * Created by santiago on 30/03/16.
 */
public class SwipePageChangeEvent extends Event {

    private int position;
    private MultiOrientedViewPager.SLIDE slide;

    public SwipePageChangeEvent(int position, MultiOrientedViewPager.SLIDE slide) {
        this.position = position;
        this.slide = slide;
    }

    public MultiOrientedViewPager.SLIDE getSlide() {
        return slide;
    }

    public int getPosition() {
        return position;
    }
}

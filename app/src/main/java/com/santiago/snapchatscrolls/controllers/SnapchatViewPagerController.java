package com.santiago.snapchatscrolls.controllers;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.santiago.controllers.BaseController;
import com.santiago.event.EventManager;
import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;
import com.santiago.snapchatscrolls.R;
import com.santiago.snapchatscrolls.event.SwipePageChangeEvent;
import com.santiago.snapchatscrolls.view.ContactListView;
import com.santiago.snapchatscrolls.view.EmptyView;
import com.santiago.snapchatscrolls.view.pager.SnapchatLayout;
import com.santiago.snapchatscrolls.view.pager.SnapchatViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 30/03/16.
 */
public class SnapchatViewPagerController extends BaseController<SnapchatViewPager> {

    private ContactListController contactsController;
    private CameraController cameraController;

    public SnapchatViewPagerController(Context context) {
        super(context);
    }

    public SnapchatViewPagerController(Context context, SnapchatViewPager viewPager) {
        super(context);
        attachElement(viewPager);
    }

    @Override
    protected void onElementAttached(SnapchatViewPager snapchatViewPager) {
        initVerticalControllers();
        initHorizontalControllers();

        snapchatViewPager.setPage(MultiOrientedViewPager.SLIDE.VERTICAL, 1);
        snapchatViewPager.setPage(MultiOrientedViewPager.SLIDE.HORIZONTAL, 2);

        snapchatViewPager.setOnHorizontalPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                broadcastEvent(new SwipePageChangeEvent(position, MultiOrientedViewPager.SLIDE.HORIZONTAL));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        snapchatViewPager.setOnVerticalPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                broadcastEvent(new SwipePageChangeEvent(position, MultiOrientedViewPager.SLIDE.VERTICAL));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setEventHandlerListener(EventManager eventManager) {
        super.setEventHandlerListener(eventManager);
        contactsController.setEventHandlerListener(eventManager);
        cameraController.setEventHandlerListener(eventManager);
    }

    private void initHorizontalControllers() {
        //Using views because we dont have controllers yet hehe
        List<SnapchatLayout> horizontalViews = new ArrayList<>();
        SnapchatLayout view0 = new EmptyView(getContext());
        view0.setBackgroundColor(getContext().getResources().getColor(R.color.black));

        contactsController = new ContactListController(getContext(), new ContactListView(getContext()));
        contactsController.getElement().setBackgroundColor(getContext().getResources().getColor(R.color.white));

        cameraController = new CameraController(getContext());

        SnapchatLayout view3 = new EmptyView(getContext());
        view3.setBackgroundColor(getContext().getResources().getColor(R.color.color3));

        SnapchatLayout view4 = new EmptyView(getContext());
        view4.setBackgroundColor(getContext().getResources().getColor(R.color.color4));

        horizontalViews.add(view0);
        horizontalViews.add(contactsController.getElement());
        horizontalViews.add(cameraController.getElement());
        horizontalViews.add(view3);
        horizontalViews.add(view4);
        getElement().setHorizontalViews(horizontalViews);
    }

    private void initVerticalControllers() {
        //Using views because there are no controllers yet hehe
        List<SnapchatLayout> verticalViews = new ArrayList<>();

        EmptyView view0 = new EmptyView(getContext());
        view0.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));

        EmptyView view1 = new EmptyView(getContext());
        view1.setBackgroundColor(getContext().getResources().getColor(R.color.color5));

        verticalViews.add(view1);
        verticalViews.add(view0);

        getElement().setVerticalViews(verticalViews);
    }

}

package com.santiago.snapchatscrolls.controllers;

import android.content.Context;

import com.santiago.controllers.BaseController;
import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;
import com.santiago.snapchatscrolls.R;
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
    }

    private void initHorizontalControllers() {
        //Using views because we dont have controllers yet hehe
        List<SnapchatLayout> horizontalViews = new ArrayList<>();
        SnapchatLayout view0 = new EmptyView(getContext());
        view0.setBackgroundColor(getContext().getResources().getColor(R.color.black));

        contactsController = new ContactListController(getContext(), new ContactListView(getContext()));
        contactsController.getElement().setBackgroundColor(getContext().getResources().getColor(R.color.white));

        SnapchatLayout view2 = new EmptyView(getContext());
        view2.setBackgroundColor(getContext().getResources().getColor(R.color.color2));

        SnapchatLayout view3 = new EmptyView(getContext());
        view3.setBackgroundColor(getContext().getResources().getColor(R.color.color3));

        SnapchatLayout view4 = new EmptyView(getContext());
        view4.setBackgroundColor(getContext().getResources().getColor(R.color.color4));

        horizontalViews.add(view0);
        horizontalViews.add(contactsController.getElement());
        horizontalViews.add(view2);
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

package com.santiago.snapchatscrolls.controllers;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;

import com.santiago.controllers.BaseController;
import com.santiago.event.anotation.EventMethod;
import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;
import com.santiago.snapchatscrolls.controllers.adapter.ContactListAdapter;
import com.santiago.snapchatscrolls.event.SwipePageChangeEvent;
import com.santiago.snapchatscrolls.view.pager.SnapchatLayout;
import com.santiago.snapchatscrolls.view.ContactListView;
import com.santiago.snapchatscrolls.view.recycler_views.ContactView;

/**
 * Created by santiago on 30/03/16.
 */
public class ContactListController extends BaseController<ContactListView> implements SnapchatLayout.SnapchatLayoutListener {

    private ContactListAdapter adapter;
    private LinearLayoutManager layoutManager;

    public ContactListController(Context context) {
        this(context, new ContactListView(context));
    }

    public ContactListController(Context context, ContactListView view) {
        super(context);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new ContactListAdapter(getContext());

        attachElement(view);
    }

    @Override
    protected void onElementAttached(ContactListView contactListView) {
        contactListView.setLayoutManager(layoutManager);
        contactListView.setAdapter(adapter.getElement()); //Also here. This reference should be kept in a variable. But we dont have backend so meh
        contactListView.setListener(this);
    }

    @EventMethod(SwipePageChangeEvent.class)
    private void onPageChangeEvent(SwipePageChangeEvent event) {
        for (int i = layoutManager.findFirstVisibleItemPosition() ; i <= layoutManager.findLastVisibleItemPosition() ; i++)
            ((ContactView) layoutManager.getChildAt(i)).resetBack();
    }

    @Override
    public boolean canScroll(Point point, MotionEvent event, MultiOrientedViewPager.SLIDE mode, MultiOrientedViewPager.ORIENTATION orientation) {
        for (int i = layoutManager.findFirstVisibleItemPosition() ; i <= layoutManager.findLastVisibleItemPosition() ; i++) {
            ContactView view = (ContactView) layoutManager.getChildAt(i);

            if (point.y < (view.getHeight() * (i + 1)))
                return view.handleMove(point, event);

        }

        return orientation != MultiOrientedViewPager.ORIENTATION.LEFT;
    }

}

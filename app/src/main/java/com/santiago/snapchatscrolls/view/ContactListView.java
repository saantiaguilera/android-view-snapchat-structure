package com.santiago.snapchatscrolls.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.santiago.snapchatscrolls.R;
import com.santiago.snapchatscrolls.view.pager.SnapchatLayout;

/**
 * Since my only purpose is to mimic the snapchat thing
 * I wont care about doing things slow but good.
 * This means hardcodes and things reaaaally bad
 *
 * Created by santiago on 28/03/16.
 */
public class ContactListView extends SnapchatLayout {

    private RecyclerView recyclerView;

    public ContactListView(Context context) {
        this(context, null);
    }

    public ContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.view_contact_list, this);

        setOrientation(VERTICAL);

        recyclerView = (RecyclerView) findViewById(R.id.view_contact_list_recyclerview);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

}

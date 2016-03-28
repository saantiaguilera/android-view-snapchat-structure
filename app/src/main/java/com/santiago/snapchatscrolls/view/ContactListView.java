package com.santiago.snapchatscrolls.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santiago.multioriented_pager.viewpager.MultiOrientedViewPager;
import com.santiago.snapchatscrolls.R;
import com.santiago.snapchatscrolls.pager.SnapchatLayout;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        recyclerView.setAdapter(new ContactsAdapter());
    }

    //TODO REFACTOR
    //We should just check against the "Contact View" or maybe use the recycler to know if that rect is of him
    @Override
    public boolean canScroll(Point point, MultiOrientedViewPager.SLIDING_MODE mode, boolean toLeft) {
        return isInsideView(point) || !toLeft;
    }

    private boolean isInsideView(Point point) {
        int[] l = new int[2];
        recyclerView.getChildAt(recyclerView.getChildCount() - 1).getLocationOnScreen(l);
        int y = l[1];

        if (point.y < y)
            return true;

        return false;
    }

    public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

        public ContactsAdapter() {}

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = new ViewHolder(new TextView(getContext()));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
            ((TextView) viewHolder.itemView).setText("CONTACT");
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}

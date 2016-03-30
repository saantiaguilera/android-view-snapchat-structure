package com.santiago.snapchatscrolls.controllers.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.santiago.controllers.BaseController;
import com.santiago.controllers.recycler_stuff.BaseRecyclerAdapter;
import com.santiago.snapchatscrolls.R;
import com.santiago.snapchatscrolls.entity.Contact;
import com.santiago.snapchatscrolls.view.recycler_views.ContactView;

import java.util.LinkedList;

/**
 * Created by santiago on 30/03/16.
 */
public class ContactListAdapter extends BaseController<BaseRecyclerAdapter<ContactView, Contact>> {

    private LinkedList<Contact> contacts;

    public ContactListAdapter(Context context) {
        super(context);

        contacts = new LinkedList<>();

        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        Contact contact3 = new Contact();

        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);

        attachElement(new BaseRecyclerAdapter<ContactView, Contact>(contacts) {
            @Override
            protected ContactView createView(ViewGroup parent, int viewType) {
                ContactView view = new ContactView(getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getContext().getResources().getDimension(R.dimen.view_contact_height)));
                return view;
            }

            @Override
            protected void bindView(ContactView contactView, Contact contact) {
                contactView.setName("Hardcoded Example :)");
            }
        });
    }

    @Override
    protected void onElementAttached(BaseRecyclerAdapter<ContactView, Contact> contactViewContactBaseRecyclerAdapter) {}

}

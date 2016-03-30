package com.santiago.snapchatscrolls;

import android.app.Activity;
import android.os.Bundle;

import com.santiago.snapchatscrolls.controllers.SnapchatViewPagerController;
import com.santiago.snapchatscrolls.view.pager.SnapchatViewPager;

/**
 * Created by santiago on 28/03/16.
 */
public class HomeActivity extends Activity {

    private SnapchatViewPagerController viewPagerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        viewPagerController = new SnapchatViewPagerController(this, (SnapchatViewPager) findViewById(R.id.activity_home_snapchat_view_pager));
    }
}

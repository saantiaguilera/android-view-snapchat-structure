package com.santiago.snapchatscrolls.view.recycler_views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiago.snapchatscrolls.R;

/**
 * Created by santiago on 30/03/16.
 */
public class ContactView extends FrameLayout {

    private TextView nameView;
    private LinearLayout informationContainer;
    private FrameLayout bgContainer;

    public ContactView(Context context) {
        this(context, null);
    }

    public ContactView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.view_contact, this);

        nameView = (TextView) findViewById(R.id.view_contact_name);
        informationContainer = (LinearLayout) findViewById(R.id.view_contact_container);
        bgContainer = (FrameLayout) findViewById(R.id.view_contact_bg_container);
    }

    public void setName(String text) {
        nameView.setText(text);
    }

    public boolean handleMove(Point start, MotionEvent event) {
        int distance = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                distance = (int) (event.getX() - start.x);

                if (distance > 0 && distance <= bgContainer.getWidth())
                    informationContainer.setPadding(distance, 0, 0, 0);

                break;

            case MotionEvent.ACTION_UP:
                ValueAnimator animator = ValueAnimator.ofInt(informationContainer.getPaddingLeft(), 0);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator){
                        informationContainer.setPadding((Integer) valueAnimator.getAnimatedValue(), 0, 0, 0);
                    }
                });
                animator.setDuration(400);
                animator.start();
        }

        return distance >= bgContainer.getWidth();
    }

}

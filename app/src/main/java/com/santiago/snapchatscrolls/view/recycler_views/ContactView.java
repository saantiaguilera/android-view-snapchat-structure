package com.santiago.snapchatscrolls.view.recycler_views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        informationContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animateBounce();
            }
        });

        informationContainer.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (informationContainer.getPaddingLeft() != 0)
                    return true;

                Toast.makeText(getContext(), "Close, but I wont do this :)", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    public void setName(String text) {
        nameView.setText(text);
    }

    private void animateBounce() {
        if (informationContainer.getPaddingLeft() != 0)
            return;

        ValueAnimator animator = ValueAnimator.ofInt(0, bgContainer.getWidth(), 10, 20, 5, 10, 2, 5, 0, 2, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                informationContainer.setPadding((Integer) animation.getAnimatedValue(), 0, 0, 0);
            }
        });
        animator.setDuration(900);
        animator.start();
    }

    public void resetBack() {
        if (informationContainer.getPaddingLeft() == 0)
            return;

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

    public boolean handleMove(Point start, MotionEvent event) {
        int distance = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                distance = (int) (event.getX() - start.x);

                if (distance > 0) {
                    if (distance <= bgContainer.getWidth())
                        informationContainer.setPadding(distance, 0, 0, 0);
                }

                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_BUTTON_RELEASE:
            case MotionEvent.ACTION_UP:
                resetBack();
        }

        return distance <= 0 || distance >= bgContainer.getWidth();
    }

}

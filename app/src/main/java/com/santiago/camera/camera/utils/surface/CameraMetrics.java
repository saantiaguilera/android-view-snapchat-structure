package com.santiago.camera.camera.utils.surface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Method;

/**
 * Created by santiago on 23/03/16.
 */
public class CameraMetrics {

    /**
     * Gets the screen size of the android phone.
     * Note that is much more complex than the simple
     * getDisplay().getHeight/getWidth thing we know about (because this doesnt take into account notificationBar and globalButtonsLayout)
     * @param context
     * @return
     */
    public static Point getAndroidScreenSize(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        int realWidth;
        int realHeight;

        if (Build.VERSION.SDK_INT >= 17) {
            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;

        } else {
            if (Build.VERSION.SDK_INT >= 14) {
                //reflection for this weird in-between time
                try {
                    Method mGetRawH = Display.class.getMethod("getRawHeight");
                    Method mGetRawW = Display.class.getMethod("getRawWidth");
                    realWidth = (Integer) mGetRawW.invoke(display);
                    realHeight = (Integer) mGetRawH.invoke(display);
                } catch (Exception e) {
                    //this may not be 100% accurate, but it's all we've got
                    realWidth = display.getWidth();
                    realHeight = display.getHeight();
                }

            } else {
                //This should be close, as lower API devices should not have window navigation bars
                realWidth = display.getWidth();
                realHeight = display.getHeight();
            }
        }

        return new Point(realWidth, realHeight);
    }

}

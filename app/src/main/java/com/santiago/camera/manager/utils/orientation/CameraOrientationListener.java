package com.santiago.camera.manager.utils.orientation;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

import com.santiago.camera.event.camera.orientation.CameraOrientationDetermineDisplayOrientationEvent;
import com.santiago.event.listener.EventListener;

public class CameraOrientationListener extends OrientationEventListener {

    private EventListener eventListener;

    private int mCurrentNormalizedOrientation;
    private int mRememberedNormalOrientation;

    public CameraOrientationListener(Context context, EventListener eventListener) {
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
        this.eventListener = eventListener;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation != ORIENTATION_UNKNOWN) {
            if (mCurrentNormalizedOrientation == normalize(orientation))
                return;

            eventListener.broadcastEvent(new CameraOrientationDetermineDisplayOrientationEvent());

            final int newOrientation = normalize(orientation);

            int delta = getShortestAngle(mCurrentNormalizedOrientation, newOrientation);

            mCurrentNormalizedOrientation = newOrientation;
        }
    }

    private int getShortestAngle(int start, int target) {
        int res = target - start;
        if (res > 180) {
            return res - 360;
        } else if (res <= -180) {
            return res + 360;
        }

        return res;
    }

    private int normalize(int degrees) {
        if (degrees > 315 || degrees <= 45) {
            return 0;
        }

        if (degrees > 45 && degrees <= 135) {
            return 90;
        }

        if (degrees > 135 && degrees <= 225) {
            return 180;
        }

        if (degrees > 225 && degrees <= 315) {
            return 270;
        }

        throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
    }

    /**
     * Must be called before taking the picture
     */
    public void rememberOrientation() {
        mRememberedNormalOrientation = mCurrentNormalizedOrientation;
    }

    /**
     * Use it to retreive the normal orientation after taking the pic
     * @return
     */
    public int getRememberedNormalOrientation() {
        return mRememberedNormalOrientation;
    }

}
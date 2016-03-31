package com.santiago.camera.manager.orientation;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.view.Surface;

import com.santiago.camera.event.camera.orientation.CameraOrientationDetermineDisplayOrientationEvent;
import com.santiago.camera.event.camera.type.OnCameraTypeChangeEvent;
import com.santiago.camera.manager.type.CameraType;
import com.santiago.camera.manager.utils.orientation.CameraOrientationListener;
import com.santiago.event.EventManager;
import com.santiago.event.anotation.EventMethod;

/**
 * Created by santiago on 10/03/16.
 */
public class CameraOrientationManager {

    private Context context;
    private EventManager eventManager;

    private CameraOrientationListener orientationListener;

    private CameraType currentCamera;

    private int mDisplayOrientation;
    private int mLayoutOrientation;

    public CameraOrientationManager(Context context, EventManager eventManager) {
        this.context = context;
        this.eventManager = eventManager;
        eventManager.addListener(this);

        orientationListener = new CameraOrientationListener(context, eventManager);
        orientationListener.enable();
    }

    public void prepareForPicture() {
        orientationListener.rememberOrientation();
    }

    public int getNormalOrientation() {
        return orientationListener.getRememberedNormalOrientation();
    }

    public int getDisplayOrientation() {
        determineDisplayOrientation();
        return mDisplayOrientation;
    }

    public int getLayoutOrientation() {
        return mLayoutOrientation;
    }

    @EventMethod(OnCameraTypeChangeEvent.class)
    private void onCameraTypeChange(OnCameraTypeChangeEvent event) {
        currentCamera = event.getCameraType();
        determineDisplayOrientation();
    }

    @EventMethod(CameraOrientationDetermineDisplayOrientationEvent.class)
    private void determineDisplayOrientation() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(currentCamera.getCameraType(), cameraInfo);

        refreshLayoutOrientation();

        int displayOrientation;

        // Camera direction
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayOrientation = (cameraInfo.orientation + mLayoutOrientation) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
            mDisplayOrientation = (displayOrientation - mLayoutOrientation + 360) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - mLayoutOrientation + 360) % 360;
            mDisplayOrientation = (cameraInfo.orientation - mLayoutOrientation + 360) % 360;
        } //see http://developer.android.com/reference/android/hardware/Camera.html#setDisplayOrientation(int)
    }

    private void refreshLayoutOrientation() {
        int rotation = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: {
                degrees = 0;
                break;
            }
            case Surface.ROTATION_90: {
                degrees = 90;
                break;
            }
            case Surface.ROTATION_180: {
                degrees = 180;
                break;
            }
            case Surface.ROTATION_270: {
                degrees = 270;
                break;
            }
        }

        mLayoutOrientation = degrees;
    }

}

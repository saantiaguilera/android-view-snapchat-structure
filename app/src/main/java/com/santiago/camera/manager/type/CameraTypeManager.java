package com.santiago.camera.manager.type;

import android.content.Context;
import android.content.pm.PackageManager;

import com.santiago.camera.event.camera.type.OnCameraTypeChangeEvent;
import com.santiago.event.EventManager;

/**
 * Class in charge of handling which camera is currently being used
 *
 * Created by santiago on 10/03/16.
 */
public class CameraTypeManager {

    private Context context;
    private EventManager eventManager;

    private CameraType currentCamera;

    public CameraTypeManager(Context context, EventManager eventManager) {
        this.context = context;
        this.eventManager = eventManager;
        eventManager.addListener(this);
    }

    public void setCamera(CameraType camera) {
        if (camera == CameraType.FRONT)
            currentCamera = getFrontCamera();
        else currentCamera = getBackCamera();

        eventManager.broadcastEvent(new OnCameraTypeChangeEvent(currentCamera));
    }

    public CameraType getCurrentCamera() { return currentCamera; }

    private CameraType getFrontCamera() {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))
            return CameraType.FRONT;

        return getBackCamera();
    }

    private CameraType getBackCamera() {
        return CameraType.BACK;
    }

    public boolean hasFrontCamera() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }

    public boolean hasCamera() { return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY); }

}

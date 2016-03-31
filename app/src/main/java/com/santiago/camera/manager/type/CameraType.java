package com.santiago.camera.manager.type;

import android.hardware.Camera;

/**
 * Created by santiago on 10/03/16.
 */
public enum CameraType {

    FRONT(Camera.CameraInfo.CAMERA_FACING_FRONT),
    BACK(Camera.CameraInfo.CAMERA_FACING_BACK);

    private int cameraType;

    CameraType(int cameraType) {
        this.cameraType = cameraType;
    }

    public int getCameraType() {
        return cameraType;
    }

}

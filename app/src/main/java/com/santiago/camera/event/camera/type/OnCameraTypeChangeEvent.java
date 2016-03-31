package com.santiago.camera.event.camera.type;

import com.santiago.camera.manager.type.CameraType;
import com.santiago.event.Event;

/**
 * Created by santiago on 16/03/16.
 */
public class OnCameraTypeChangeEvent extends Event {

    private CameraType cameraType;

    public OnCameraTypeChangeEvent(CameraType cameraType) {
        this.cameraType = cameraType;
    }

    public CameraType getCameraType() {
        return cameraType;
    }

}

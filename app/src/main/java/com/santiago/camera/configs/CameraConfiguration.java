package com.santiago.camera.configs;

import android.hardware.Camera;

/**
 * Created by santiago on 13/03/16.
 */
public interface CameraConfiguration {

    Camera.Parameters applyConfiguration(Camera.Parameters params);

}

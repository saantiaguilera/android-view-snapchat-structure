package com.santiago.camera.configs.flashlight;

import android.content.Context;
import android.hardware.Camera;

import com.santiago.camera.configs.CameraConfiguration;

/**
 * Created by santiago on 13/03/16.
 */
public class CameraFlashlightConfiguration implements CameraConfiguration {

    private Context context;

    private String currentMode = Camera.Parameters.FLASH_MODE_AUTO;

    public CameraFlashlightConfiguration(Context context) {
        this.context = context;
    }

    public void setFlashlight(String mode) {
        currentMode = mode;
    }

    @Override
    public Camera.Parameters applyConfiguration(Camera.Parameters params) {
        if(params==null)
            return null;

        if (params.getSupportedFlashModes()!=null && params.getSupportedFlashModes().contains(currentMode))
            params.setFlashMode(currentMode);

        return params;
    }

}

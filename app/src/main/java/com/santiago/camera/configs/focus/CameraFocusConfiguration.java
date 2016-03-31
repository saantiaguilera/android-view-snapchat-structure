package com.santiago.camera.configs.focus;

import android.content.Context;
import android.hardware.Camera;

import com.santiago.camera.configs.CameraConfiguration;

/**
 * Created by santiago on 13/03/16.
 */
public class CameraFocusConfiguration implements CameraConfiguration {

    private Context context;

    private String currentFocusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;

    public CameraFocusConfiguration(Context context) {
        this.context = context;
    }

    public void setFocus(String focusMode) {
        currentFocusMode = focusMode;
    }

    @Override
    public Camera.Parameters applyConfiguration(Camera.Parameters params) {
        if(params==null)
            return null;

        if (params.getSupportedFocusModes().contains(currentFocusMode))
            params.setFocusMode(currentFocusMode);

        return params;
    }

}

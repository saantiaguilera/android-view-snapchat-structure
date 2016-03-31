package com.santiago.snapchatscrolls.controllers;

import android.content.Context;

import com.santiago.camera.camera.controller.AspectRatioCameraController;
import com.santiago.camera.camera.view.AspectRatioCameraView;
import com.santiago.camera.configs.focus.CameraFocusConfiguration;
import com.santiago.controllers.BaseController;
import com.santiago.snapchatscrolls.view.pager.SnapchatLayout;

/**
 * Created by santiago on 31/03/16.
 */
public class CameraController extends BaseController<SnapchatLayout> {

    private AspectRatioCameraController cameraController;

    public CameraController(Context context) {
        super(context);

        cameraController = new AspectRatioCameraController.Builder(getContext())
                .addConfiguration(new CameraFocusConfiguration(getContext()))
                .setAspectRatio(AspectRatioCameraController.ASPECT_RATIO_FULLSCREEN)
                .build();

        attachElement(new SnapchatLayout(context));
    }

    @Override
    protected void onElementAttached(SnapchatLayout snapchatLayout) {
        snapchatLayout.addView(cameraController.getElement());
    }

}

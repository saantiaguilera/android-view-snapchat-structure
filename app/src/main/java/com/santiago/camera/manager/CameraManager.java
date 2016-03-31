package com.santiago.camera.manager;

import android.content.Context;
import android.hardware.Camera;

import com.santiago.camera.configs.CameraConfiguration;
import com.santiago.camera.manager.orientation.CameraOrientationManager;
import com.santiago.camera.manager.type.CameraType;
import com.santiago.camera.manager.type.CameraTypeManager;
import com.santiago.event.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 10/03/16.
 */
public class CameraManager {

    private EventManager eventManager;

    private CameraTypeManager cameraTypeManager;
    private CameraOrientationManager cameraOrientationManager;

    private List<CameraConfiguration> configurations = new ArrayList<>();

    public CameraManager(Context context) {
        //Create an internal eventmanager we will use to broadcast our stuff
        eventManager = new EventManager(context);
        eventManager.addListener(this);

        //Initialize our managers
        cameraTypeManager = new CameraTypeManager(context, eventManager);
        cameraOrientationManager = new CameraOrientationManager(context, eventManager);

        //Set default vars
        cameraTypeManager.setCamera(CameraType.BACK);
    }

    public void addConfiguration(CameraConfiguration configuration) {
        if(configurations.contains(configuration))
            configurations.remove(configuration);

        configurations.add(configuration);
    }

    public boolean removeConfiguration(CameraConfiguration configuration) {
        return configurations.remove(configuration);
    }

    public void clearConfigurations() {
        configurations.clear();
    }

    public CameraOrientationManager getCameraOrientationManager() {
        return cameraOrientationManager;
    }

    public CameraTypeManager getCameraTypeManager() {
        return cameraTypeManager;
    }

    public void prepareForPicture() {
        cameraOrientationManager.prepareForPicture();
    }

    public Camera createNewCamera() {
        //Get the current type of camera (Front/Back). If the user has override the method for implementing a custom one, use theirs as long as it exists. If not ours
        CameraType currentCamera = getCameraTypeManager().getCurrentCamera();

        //Create it facing the desired place
        Camera camera = Camera.open(currentCamera.getCameraType());

        //Since the display orientation behaves like shit in android, we have a config that interacts with it and makes it work as intended
        camera.setDisplayOrientation(getCameraOrientationManager().getDisplayOrientation());

        //Apply the configurations
        for(CameraConfiguration configuration : configurations)
            camera.setParameters(configuration.applyConfiguration(camera.getParameters()));

        return camera;
    }

}

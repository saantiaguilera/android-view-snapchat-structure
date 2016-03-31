package com.santiago.camera.camera.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.View;

import com.santiago.camera.camera.utils.picture.CameraPictureCallback;
import com.santiago.camera.camera.utils.picture.CameraPictureUtilities;
import com.santiago.camera.camera.utils.surface.CameraSurfaceHolder;
import com.santiago.camera.manager.CameraManager;
import com.santiago.camera.manager.orientation.CameraOrientationManager;
import com.santiago.controllers.BaseController;
import com.santiago.event.EventManager;

import java.io.IOException;
import java.util.List;

/**
 * Controller for a really basic camera
 *
 * Created by santiago on 09/03/16.
 */
public abstract class BaseCameraController<T extends View & CameraSurfaceHolder & CameraPictureCallback> extends BaseController<T> {

    private SurfaceHolder surfaceHolder;
    private BaseCameraSurfaceCallback surfaceCallback;

    private CameraManager cameraManager;

    private boolean surfaceActive = false;

    private Camera camera;

    public BaseCameraController(Context context) {
        super(context);

        cameraManager = new CameraManager(context);
    }

    /*-----------------Class overrides-----------------*/

    @Override
    protected void onElementAttached(T t) {
        surfaceHolder = t.getSurfaceHolder();

        //In case they dont set us an EventHandler, we do it on our own because we will need to broadcast things internally. If they do then dont mind this
        setEventHandlerListener(new EventManager(getContext()));

        //As soon as we are setting him a callback, process will start and we will eventually be notified (in the cameraSurfaceHandler class about its creation)
        surfaceHolder.addCallback(surfaceCallback = new BaseCameraSurfaceCallback());
    }

    /*----------------------Getters & Setters-------------------------*/

    public @NonNull CameraManager getCameraManager() {
        return cameraManager;
    }

    /**
     * If the camera is showing or not
     * @return
     */
    public boolean isCameraActive() { return surfaceActive; }

    /**
     * If the camera exists sets the surfaceholder in it
     */
    private void setPreviewDisplay() {
        if(camera==null)
            return;

        try {
            //Since the surface is created, set the camera in it
            camera.setPreviewDisplay(surfaceHolder);

            surfaceCallback.onDataChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Camera getCamera() {
        return camera;
    }

    /*---------------------------Methods---------------------------------*/

    /**
     * Take a picture and show it in the view
     */
    public void takePicture() {
        cameraManager.prepareForPicture();

        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                //We should process the bitmap an a separate thread, (but UI things should still be handled in the main thread)
                new PictureTakenTask().execute(data);

                //Stop the camera since it wont be used while the picture is showing
                stopCamera();
            }
        });
    }

    /**
     * Override if you will do a transformation prior to me handling the bitmap
     * @note: EXIF parameters wont be always setted, so avoid using it. Front camera for example dont have. Samsung mobiles also
     * @note: Front camera mirroring + Rotations for leaving it as it should be are done by default, so no need to do them.
     * @param source bitmap
     * @return Transformed bitmap
     * @Async Beware this is not executed in the UI thread, so dont do UI changes.
     */
    protected Bitmap transform(Bitmap source) { return source; }

    /**
     * Call for starting the camera
     */
    public void startCamera() {
        //Hide the picture if its visible
        getElement().onPictureVisibilityChanged(View.GONE);

        //If the camera is already running, stop it
        if(camera!=null)
            stopCamera();

        camera = cameraManager.createNewCamera();

        //Set the preview display again
        setPreviewDisplay();

        camera.startPreview();
    }

    /**
     * Call for stopping the camera
     * <strong> When taking a picture and showing it its higly recommended to stop the camera to free memory </strong>
     * <strong> Always call this when you finished using the camera to make it available to others </strong>
     */
    public void stopCamera() {
        if(camera==null)
            return;

        if (surfaceActive)
            camera.stopPreview();

        camera.release();
        camera = null;

        surfaceActive = false;
    }

    /*-------------------------Abstracty methods---------------------------*/

    protected abstract void onPictureGenerated(Bitmap bitmap);

    /*----------------------Surface Callback-------------------------------*/

    protected void updateSurface(Camera.Parameters parameters) {
        //Update data
        camera.setParameters(parameters);
        camera.startPreview();

        surfaceActive = true;
    }

    protected void refreshSurface(int width, int height) {
        //If we dont track of the width/height of our surface, we cant do this operation
        if(width == BaseCameraSurfaceCallback.NO_VALUE && height == BaseCameraSurfaceCallback.NO_VALUE)
            return;

        //Data will be using
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size previewSize;
        Camera.Size pictureSize;

        //Get the best size for this surface and if exists, set it and calculate the one for the picture (with the ratio setted for the preview)
        previewSize = approximateToBestCameraSize(width, height, parameters.getSupportedPreviewSizes());

        if(previewSize!=null) {
            parameters.setPreviewSize(previewSize.width, previewSize.height);

            //Get the best picture size for this surface, in relation with the setted preview size and set it
            pictureSize = approximateToBestCameraSize(previewSize.height, previewSize.width, parameters.getSupportedPictureSizes());
            if (pictureSize != null)
                parameters.setPictureSize(pictureSize.width, pictureSize.height);
        }

        updateSurface(parameters);
    }

    /**
     * Get the best size resembling the provided aspect ratio from the list of sizes
     *
     * @note <strong>width its the height and height its the width, since physical camera
     * is installed in landscape mode (this means, x is y and y is x.</strong>
     *
     * @note TODO We could (im really doubting it, but its worth a try) compare when we are in preview sizes and check if
     * the aspect ratio exists in the picture sizes. If it does we continue the operation if not we dont use that size
     * That way we can be sure that our picture and preview will match.
     *
     * @param width
     * @param height
     * @param sizes
     * @return Best Size that fits the given parameters
     */
    protected Camera.Size approximateToBestCameraSize(int width, int height, List<Camera.Size> sizes) {
        if (sizes == null)
            return null;

        double targetRatio = (double) height / width;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;

            if (Math.abs(ratio - targetRatio) > BaseCameraSurfaceCallback.ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - height) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - height);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;

            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - height) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - height);
                }
            }
        }

        return optimalSize;
    }

    protected class BaseCameraSurfaceCallback implements SurfaceHolder.Callback {

        public static final double ASPECT_TOLERANCE = 0.05D;
        public static final int NO_VALUE = -1;

        private int width = NO_VALUE;
        private int height = NO_VALUE;

        public BaseCameraSurfaceCallback() {
        }

        /**
         * Called when the surface has being created
         * @param holder
         */
        public void surfaceCreated(SurfaceHolder holder) {
            //Start the camera when the surface is created
            startCamera();
        }

        /**
         * Called when the surface suffers some change (rotation, picture taken, etc)
         * @param holder
         * @param format
         * @param width
         * @param height
         */
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if(camera==null)
                return;

            //Refresh our internal values to keep track of them in case we refresh the surface (when the camera suffers changes)
            this.width = width;
            this.height = height;

            refreshSurface(width, height);
        }

        public void surfaceDestroyed(SurfaceHolder holder) { }

        public void onDataChanged() {
            refreshSurface(width, height);
        }

    }

    /*-------------------------------------------------------Threading------------------------------------------------------------------*/

    public class PictureTakenTask extends AsyncTask<byte[], Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(byte[]... params) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(params[0], 0, params[0].length);

            bitmap = transform(bitmap);

            boolean isFrontCamera = cameraManager.getCameraTypeManager().getCurrentCamera().getCameraType() == Camera.CameraInfo.CAMERA_FACING_FRONT;
            CameraOrientationManager orientationManager = cameraManager.getCameraOrientationManager();

            int rotation = CameraPictureUtilities.getRotation(orientationManager.getDisplayOrientation(), orientationManager.getNormalOrientation(), orientationManager.getLayoutOrientation(), isFrontCamera);

            bitmap = CameraPictureUtilities.rotatePicture(rotation, bitmap);

            if (isFrontCamera)
                bitmap = CameraPictureUtilities.mirrorImage(bitmap);

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //Set the picture
            getElement().onPictureTaken(bitmap);
            getElement().onPictureVisibilityChanged(View.VISIBLE);

            //Notify
            onPictureGenerated(bitmap);
        }

    }

}
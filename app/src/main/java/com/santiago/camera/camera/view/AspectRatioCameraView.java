package com.santiago.camera.camera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.santiago.camera.camera.controller.AspectRatioCameraController;
import com.santiago.camera.camera.utils.picture.CameraPictureCallback;
import com.santiago.camera.camera.utils.surface.CameraSurfaceHolder;
import com.santiago.snapchatscrolls.R;

/**
 * View that adapts to a given aspect ratio.
 *
 * Created by santiago on 21/03/16.
 */
public class AspectRatioCameraView extends FrameLayout implements CameraSurfaceHolder, CameraPictureCallback {

    private SurfaceView surfaceView;
    private ImageView pictureView;
    private FrameLayout cameraContainer;
    private View blockView;

    public AspectRatioCameraView (Context context) {
        this(context, null);
    }

    public AspectRatioCameraView (Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.view_aspect_ratio_camera, this);

        surfaceView = (SurfaceView) findViewById(R.id.view_aspect_ratio_camera_surface_view);
        pictureView = (ImageView) findViewById(R.id.view_aspect_ratio_camera_image_view);
        cameraContainer = (FrameLayout) findViewById(R.id.view_aspect_ratio_camera_container);
        blockView = findViewById(R.id.view_aspect_ratio_block_view);
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceView.getHolder();
    }

    @Override
    public void onPictureTaken(@NonNull Bitmap picture) {
        pictureView.setImageBitmap(picture);
    }

    /**
     * Sets a given aspectRatio
     * @param virtualRatio ratio of the surfaceview the user will be seeing
     * @param realRatio ratio of the surfaceview it will be hidden to the user (to let the user see exactly the ratio he prompted. Since camera.size isnt always exactly to our ratio we want)
     * @param screenWidth width of the screen . TODO REFACTOR IT and use the width of this view in MATCH_PARENT state
     */
    public void setAspectRatio(double virtualRatio, double realRatio, int screenWidth) {
        LayoutParams containerParams;
        /**
         * If its undefined the virtual ratio. This means we should try to fill entirely
         * It should be false by all means. Since aspectRatio is expected to change from undefined to the best camera.size obtained
         */
        if (virtualRatio == AspectRatioCameraController.ASPECT_RATIO_FULLSCREEN) {
            containerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            LayoutParams pictureParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pictureParams.gravity = Gravity.TOP;
            pictureView.setLayoutParams(pictureParams);

            blockView.setVisibility(View.GONE);
        } else {
            //Else set the container ("what the user sees") to the aspect ratio we give
            containerParams = new LayoutParams(screenWidth, (int) (screenWidth * realRatio));

            LayoutParams pictureParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenWidth * virtualRatio));
            pictureParams.gravity = Gravity.TOP;
            pictureView.setLayoutParams(pictureParams);

            //And move a "hiding view" with size of the "error" ratio we got
            LayoutParams blackBorderParams = new LayoutParams(screenWidth, (int) (screenWidth * (realRatio - virtualRatio)));
            blackBorderParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            blockView.setVisibility(View.VISIBLE);
            blockView.setLayoutParams(blackBorderParams);


        }

        containerParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        cameraContainer.setLayoutParams(containerParams);
    }
    @Override
    public void onPictureVisibilityChanged(int visibility) {
        pictureView.setVisibility(visibility);
        surfaceView.setVisibility(visibility == VISIBLE ? INVISIBLE : VISIBLE);
    }

}
package com.santiago.camera.camera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.santiago.camera.camera.utils.picture.CameraPictureCallback;
import com.santiago.camera.camera.utils.surface.CameraSurfaceHolder;
import com.santiago.snapchatscrolls.R;

/**
 * View representing the basic camera
 *
 * Created by santiago on 09/03/16.
 */
public class BaseCameraView extends FrameLayout implements CameraSurfaceHolder, CameraPictureCallback {

    private SurfaceView surfaceView;
    private ImageView pictureView;

    public BaseCameraView(Context context) {
        this(context, null);
    }

    public BaseCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.view_base_camera, this);

        surfaceView = (SurfaceView) findViewById(R.id.view_base_camera_surface_view);
        pictureView = (ImageView) findViewById(R.id.view_base_camera_picture);
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceView.getHolder();
    }

    @Override
    public void onPictureTaken(@NonNull Bitmap picture) {
        pictureView.setImageBitmap(picture);
    }

    @Override
    public void onPictureVisibilityChanged(int visibility) {
        pictureView.setVisibility(visibility);
        surfaceView.setVisibility(visibility==VISIBLE ? INVISIBLE : VISIBLE);
    }

}
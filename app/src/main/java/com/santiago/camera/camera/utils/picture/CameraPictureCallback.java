package com.santiago.camera.camera.utils.picture;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by santiago on 13/03/16.
 */
public interface CameraPictureCallback {

    void onPictureTaken(@NonNull Bitmap picture);
    void onPictureVisibilityChanged(int visibility);

}

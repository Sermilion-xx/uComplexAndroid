package org.ucomplex.ucomplex.Utility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class FacadeMedia {

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type, String directory, String fileName) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                directory), "UComplex");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("UComplex", "failed to create directory");
            }
        }
        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    fileName);
        } else {
            return null;
        }
        return mediaFile;
    }

    public static int getPowerOfTwoForSampleRatio(Double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0)
            return 1;
        else
            return k;
    }

    public static Bitmap getThumbnail(Uri uri, Activity activity, int ...thumbnail_size) throws IOException {
        if(thumbnail_size[0]==0){
            thumbnail_size[0] = 640;
        }
        InputStream input = activity.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if (onlyBoundsOptions.outWidth == -1 || onlyBoundsOptions.outHeight == -1)
            return null;
        int originalSize;
        if (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth)
            originalSize = onlyBoundsOptions.outHeight;
        else
            originalSize = onlyBoundsOptions.outWidth;

        Double ratio;
        if (originalSize > thumbnail_size[0])
            ratio = (double) originalSize / thumbnail_size[0];
        else
            ratio = 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        input = activity.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        if (input != null) {
            input.close();
        }
        return bitmap;
    }

}

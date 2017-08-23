package com.wolf.nniroula.creditrecorder.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wolf.nniroula.creditrecorder.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by nniroula on 10/27/16.
 */
public class DbBitmapUtility {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(bitmap != null)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        if(image != null)
        return BitmapFactory.decodeByteArray(image, 0, image.length);
        else return BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.ic_photo_blue);
    }
}

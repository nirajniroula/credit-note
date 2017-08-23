package com.wolf.nniroula.creditrecorder.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by nniroula on 11/3/16.
 */
public class ImageHandler {
    private Context Context;
    private String path_name;
    private static final String root = Environment.getExternalStorageDirectory().toString() + "/Mill Record";
    private static final File myDir = new File(root + "/items_images");

    public ImageHandler(Context mContext) {
        this.Context = mContext;
    }

    public String saveToInternalStorage(Bitmap bitmapImage, String name) {
        this.path_name = name;

        ContextWrapper cw = new ContextWrapper(Context);

        // Create imageDir
        File myPath = new File(myDir, path_name + ".png");

        Log.e("Save", myPath.toString());
        FileOutputStream fos = null;
        if (bitmapImage != null) {
            try {
                fos = new FileOutputStream(myPath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 80, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return myDir.getAbsolutePath();
        } else
            return null;
    }

    public Bitmap loadImageFromStorage(String name) {
        Bitmap b = null;
        try {
            File f = new File(myDir.toString(), name + ".png");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return b;
    }
}


package com.suwonsmartapp.abl;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by sol on 2015-04-07.
 */
public interface ImageCache {
    public void addBitmap(String key, Bitmap bitmap);

    public void addBitmap(String key, File bitmapFile);

    public Bitmap getBitmap(String key);

    public void clear();
}

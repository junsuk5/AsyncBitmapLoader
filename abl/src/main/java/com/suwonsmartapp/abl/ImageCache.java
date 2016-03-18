
package com.suwonsmartapp.abl;

import android.graphics.Bitmap;

import java.io.File;

public interface ImageCache {
    void addBitmap(String key, Bitmap bitmap);

    void addBitmap(String key, File bitmapFile);

    Bitmap getBitmap(String key);

    void clear();
}

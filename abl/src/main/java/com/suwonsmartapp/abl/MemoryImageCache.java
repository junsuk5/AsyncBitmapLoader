
package com.suwonsmartapp.abl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import java.io.File;

/**
 * 메모리 캐시
 */
public class MemoryImageCache implements ImageCache {
    private LruCache<String, Bitmap> lruCache;

    public MemoryImageCache(int maxSize) {
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 캐시에 담겨진 크기는 bitmap 사이즈로
                return value.getByteCount();
            }
        };
    }

    @Override
    public void addBitmap(String key, Bitmap bitmap) {
        if (bitmap == null)
            return;
        lruCache.put(key, bitmap);
    }

    @Override
    public void addBitmap(String key, File bitmapFile) {
        if (bitmapFile == null)
            return;
        if (!bitmapFile.exists())
            return;

        Bitmap bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
        lruCache.put(key, bitmap);
    }

    @Override
    public Bitmap getBitmap(String key) {
        return lruCache.get(key);
    }

    @Override
    public void clear() {
        lruCache.evictAll();
    }

}

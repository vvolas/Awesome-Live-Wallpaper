package com.wolas.awesomewallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by wolas on 16.2.18.
 */
public class BitmapHolder {
    private HashMap<String, Bitmap> hm;
    private Context context;

    private static BitmapHolder instance=null;

    public static BitmapHolder getInstance() {
        if (instance == null) {
            instance = new BitmapHolder();
        }
        return instance;
    }

    private BitmapHolder() {
        hm = new HashMap<>();
        context = Utils.getInstance().getContext();
    }

    public void putBitmap (String fileName, Bitmap bitmap) {
        hm.put(fileName,bitmap);
    }

    public Bitmap getBitmap(String fileName) {
        return hm.get(fileName);
    }
}

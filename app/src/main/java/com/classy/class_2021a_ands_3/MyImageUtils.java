package com.classy.class_2021a_ands_3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MyImageUtils {

    private static MyImageUtils instance;
    private static Context appContext;

    public static MyImageUtils getInstance() {
        return instance;
    }

    private MyImageUtils(Context context) {
        appContext = context;
    }

    public static MyImageUtils initHelper(Context context) {
        if (instance == null)
            instance = new MyImageUtils(context);
        return instance;
    }

    public void loadImageFromAssets(String pathWithExtension, ImageView imageView) {
        try {
            InputStream ims = appContext.getAssets().open(pathWithExtension);
            Drawable d = Drawable.createFromStream(ims, null);
            loadImage(d, imageView);

            ims.close();
        } catch(IOException ex) {
            return;
        }
    }

    public void loadImage(Drawable drawable, ImageView imageView) {
        Glide
                .with(appContext)
                .load(drawable)
                .into(imageView);
    }

    public void loadImage(int drawableId, ImageView imageView) {
        Glide
                .with(appContext)
                .load(drawableId)
                .into(imageView);
    }

    public void loadImage(String url, ImageView imageView) {
        Glide
                .with(appContext)
                .load(url)
                .into(imageView);
    }

    public void loadImageByDrawableName(String drawableName, ImageView imageView) {
        Resources resources = appContext.getResources();
        final int resourceId = resources.getIdentifier(drawableName, "drawable", appContext.getPackageName());
        loadImage(resourceId, imageView);
    }

}

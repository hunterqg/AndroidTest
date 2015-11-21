package com.example.qingao;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by qingao on 15-11-16.
 */
public class AppVolley {

    private static Context mContext;
    private RequestQueue mRequestQueue;

    private ImageLoader imageLoader;


    private static class Holder {
        public static AppVolley instance = new AppVolley();
    }
    private AppVolley() {

    }

    public static AppVolley getInstance(Context context) {
        mContext = context;
        return Holder.instance;
    }

    private void initRequestQueue() {
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
    }

    public RequestQueue getRequestQueue() {
        if(mContext == null) {
            throw new RuntimeException("Context not initialized!");
        }
        initRequestQueue();
        return mRequestQueue;
    }

    private void initImageLoader(){
        initRequestQueue();
        if(imageLoader == null) {
            imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
                private final LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>(20);
                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url,bitmap);
                }
            });
        }
    }

    public ImageLoader getImageLoader() {
        if(mContext == null) {
            throw  new RuntimeException("Context not inialized!");
        }
        initImageLoader();
        return imageLoader;
    }



    public <T> void addRequest(Request<T> request) {
        getRequestQueue().add(request);
    }

}

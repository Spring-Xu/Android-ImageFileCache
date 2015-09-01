package cjstar.com.filecachelibrary.download;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import cjstar.com.filecachelibrary.cache.LRUFileCache;
import cjstar.com.filecachelibrary.image.ImageLoaderUtils;
import cjstar.com.ui.R;

/**
 * @author  Created by CJstar on 15/8/24.
 * usually, subclasses of AsyncTask are declared inside the activity class.
 * that way, you can easily modify the UI thread from here
 */
public class DownloadTask extends AsyncTask<String, Integer, Bitmap> {

    WeakReference<ImageView> weakReference;
    public DownloadTask(ImageView imageView) {
        imageView.setImageResource(R.drawable.dd);
        weakReference = new WeakReference(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... sUrl) {
        Bitmap bitmap = null;
        if(LRUFileCache.getInstance().getDiskFile(sUrl[0])!=null){
            bitmap = ImageLoaderUtils.loadHugeBitmapFromSDCard(LRUFileCache.getInstance().getFilePathByKey(sUrl[0]),0,0);
            return bitmap;
        }

        InputStream input = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            LRUFileCache.getInstance().addDiskFile(url.toString(), input);
            bitmap = ImageLoaderUtils.loadHugeBitmapFromSDCard(LRUFileCache.getInstance().getFilePathByKey(url.toString()),0,0);
        }catch (Throwable t){
            t.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ImageView imageView = weakReference.get();
        if(imageView!=null&&bitmap!=null&&!bitmap.isRecycled()){
            imageView.setImageBitmap(bitmap);
        }
    }
}
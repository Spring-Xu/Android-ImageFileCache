package cjstar.com.download;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cjstar.com.cache.LRUFileCache;

/**
 * @author  Created by xuchun on 15/8/24.
 * usually, subclasses of AsyncTask are declared inside the activity class.
 * that way, you can easily modify the UI thread from here
 */
public class DownloadTask extends AsyncTask<String, Integer, String> {

    public DownloadTask() {
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            LRUFileCache.getInstance().addDiskFile(url.toString(), input);
        }catch (Throwable t){
            t.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
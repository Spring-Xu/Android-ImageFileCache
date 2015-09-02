package cjstar.com.filecachelibrary.image;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;

import cjstar.com.filecachelibrary.download.DownloadTask;

/**
 * Created by CJstar on 15/8/24.
 */
public class ImageHelper {
    private static ImageHelper mImageHelper;

    public static ImageHelper getInstance(){
        if (mImageHelper == null) {
            synchronized (ImageHelper.class){
                if (mImageHelper == null) {
                    mImageHelper = new ImageHelper();
                }
            }
        }

        return mImageHelper;
    }

    public void showImage(ImageView imgView,String url,int defaultRes){
        imgView.setImageResource(defaultRes);
        DownloadTask task = new DownloadTask(imgView,defaultRes);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
        }else{
            task.execute(url);
        }
    }
}

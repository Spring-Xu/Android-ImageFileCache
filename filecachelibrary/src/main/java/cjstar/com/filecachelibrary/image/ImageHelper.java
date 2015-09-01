package cjstar.com.filecachelibrary.image;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;

import cjstar.com.filecachelibrary.download.DownloadTask;
import cjstar.com.ui.R;

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

    public void showImage(ImageView imgView,String url){
        imgView.setImageResource(R.drawable.dd);
        DownloadTask task = new DownloadTask(imgView);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
        }else{
            task.execute(url);
        }
    }
}

package cjstar.com.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

import cjstar.com.filecachelibrary.cache.FileCacheOptions;
import cjstar.com.filecachelibrary.cache.LRUFileCache;
import cjstar.com.filecachelibrary.image.ImageHelper;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    String urls[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String cachePath = Environment.getExternalStorageDirectory()+"/ImageFileCache/cache";
        if(!new File(cachePath).exists()){
            new File(cachePath).mkdirs();
        }

        //config the file cache
        LRUFileCache.getInstance().setFileLoadOptions(new FileCacheOptions.Builder()
                .setMaxFileCount(5)
                .setMaxCacheSize(5 * 1024 * 1024)
                .setIsUseFileCache(true)
                .setCacheRootPath(cachePath)
                .builder());

        gridView = (GridView) findViewById(R.id.gridView);
        urls = new String[]{
                "http://car3.autoimg.cn/cardfs/product/g9/M06/4F/5E/u_autohomecar__wKjBzlXW5s-AXYuRAAUcg03JGAo090.jpg",
                "http://car3.autoimg.cn/cardfs/product/g12/M08/4F/2C/u_autohomecar__wKjBy1XW5sqAD5GfAAUBLJqnpQY253.jpg",
                "http://car2.autoimg.cn/cardfs/product/g9/M13/4F/5D/u_autohomecar__wKjBzlXW5saASUqpAASY8ReT8Vc844.jpg",
                "http://car2.autoimg.cn/cardfs/product/g9/M0F/4F/5D/u_autohomecar__wKjBzlXW5sGAOBTuAAUYoMS5moI420.jpg",
                "http://car2.autoimg.cn/cardfs/product/g12/M13/4F/2B/u_autohomecar__wKjBy1XW5ryAFll0AAVKnQ46a2I297.jpg",
                "http://car0.autoimg.cn/car/upload/2015/8/13/u_20150813103316875-1112.jpg",
                "http://car2.autoimg.cn/cardfs/product/g7/M08/4F/68/u_autohomecar__wKgH3VXW5raAWVYPAAV1rthA4EQ064.jpg"
        };
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public int getCount() {
                return urls.length;
            }

            @Override
            public String getItem(int position) {
                return urls[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder hodler;
                if (convertView == null) {
                    hodler = new ViewHolder();
                    convertView = getLayoutInflater().inflate(R.layout.girdview_item, null);
                    hodler.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                    convertView.setTag(hodler);
                } else {
                    hodler = (ViewHolder) convertView.getTag();
                }

                ImageHelper.getInstance().showImage(hodler.imageView, getItem(position),R.drawable.dd);
                return convertView;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            class ViewHolder {
                ImageView imageView;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

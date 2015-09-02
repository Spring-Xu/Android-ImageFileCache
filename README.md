# Android-ImageFileCache
Android image files cache in local SDCard, and reuse image. This project used LRU cache. 

#Contact me

* Email: hquspring@gmail.com
* Blog: http://blog.csdn.net/cj_star

#How to use this API?
* Initialize the LRUFileCache's config
```Java
//config the file cache
LRUFileCache.getInstance().setFileLoadOptions(new FileCacheOptions.Builder()
        .setMaxFileCount(5)
        .setMaxCacheSize(5 * 1024 * 1024)
        .setIsUseFileCache(true)
        .setCacheRootPath(cachePath)
        .builder());
```

* Show the image demo
```Java
ImageHelper.getInstance().showImage(hodler.imageView, getItem(position),R.drawable.dd);
```

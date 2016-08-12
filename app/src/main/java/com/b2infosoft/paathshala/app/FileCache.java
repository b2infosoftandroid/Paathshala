package com.b2infosoft.paathshala.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.LruBitmapCache;
import com.b2infosoft.paathshala.volly.MySingleton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by rajesh on 8/5/2016.
 */
public class FileCache {
    private File cacheDir;
    private Context context;

    public FileCache(Context context) {
        this.context = context;
        //Find the dir at SDCARD to save cached images

        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            //if SDCARD is mounted (SDCARD is present on device and mounted)
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(), "paathshala");
        } else {
            // if checking on simulator the create cache dir in your application context
            cacheDir = context.getCacheDir();
        }

        if (!cacheDir.exists()) {
            // create cache dir in your application context
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url) {
        //Identify images by hashcode or encode by URLEncoder.encode.
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
    }

    public void setFile(Bitmap bitmap) {

    }

    public void clear() {
        // list all files inside cache directory
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        //delete all cache directory files
        for (File f : files)
            f.delete();
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap bitmap_image = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap_image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap getBitmap(String string) {
        if (string != null && string.length() > 0) {
            byte[] b = Base64.decode(string, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(b);
            return BitmapFactory.decodeStream(is);
        }
        return null;
    }

    public static void loadCacheImage(final CircularImageView imageView, String imageUrl, Context context) {
        imageUrl = imageUrl.replaceAll(" ", "%20");
        Cache cache = MySingleton.getInstance(context).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(imageUrl);
        if (entry != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR", e.getMessage());
            }
        } else {
            Network network = Network.getInstance(context);
            if (!network.isInternetAvailable()) {
                return;
            } else {
                cache.invalidate(imageUrl,true);
                ImageRequest request = new ImageRequest(imageUrl,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Log.e("ERROR VOLLY 1", error.getMessage() + "");
                                imageView.setImageResource(R.drawable.user);
                            }
                        });
                // Access the RequestQueue through your singleton class.
                MySingleton.getInstance(context).addToRequestQueue(request);
            }
        }
    }

}

package com.ricky.plistreader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ricky on 2015/7/6.
 */
public class ImageLoaders extends AsyncTask<String, Integer, Bitmap> {
    private final String TAG = ImageLoaders.class.getSimpleName();

    String url = null;
    ImageView imgView;

    public ImageLoaders(ImageView imgView) {
        this.imgView = imgView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        url = (String) urls[0];

        if (url == null)
            return null;

        while (!isCancelled()) {

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            HttpResponse response = null;
            try {
                response = client.execute(get);
            } catch (ClientProtocolException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            if (response == null) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            InputStream is;
            try {
                is = entity.getContent();

                long total = entity.getContentLength();
                int count = 0;
                int length = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                while ((length = is.read(buf)) != -1 && !isCancelled()) {
                    baos.write(buf, 0, length);
                    count += length;
                    if ((count * 100 / total) < 0)
                        return null;
                    publishProgress((int) (count * 100 / total));

                }
                is.close();
                baos.close();

                byte[] bitmapdata = baos.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);


                return bitmap;
            } catch (IllegalStateException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return null;
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return null;
            }
        }

        return null;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        int d = values[0];
        Log.i(TAG, " Loading... " + d + " %");
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null){
           this.imgView.setImageBitmap(bitmap);
        }
    }
}

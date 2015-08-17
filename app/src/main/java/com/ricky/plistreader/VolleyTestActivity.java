package com.ricky.plistreader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ricky on 2015/7/28.
 */
public class VolleyTestActivity extends Activity {

    private final  String TAG = VolleyTestActivity.class.getSimpleName();
    private static String APIURL = "https://nabiweb.fuhu.com/nabiweb/url/?deviceType=n&deviceEdition=n&nabiFirmwareVersion=4.1.1&pageNumber=2&pageSize=20&showWhitelist=true";
    private static String APIURL2 ="http://api.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&cluster=yes";
    private static String APIKEY = "059cd504-3461-53b9-ae6d-b400bfbb8254";

    private String TestUrl = "https://fbcdn-sphotos-a-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/p180x540/423852_349248718440219_425270433_n.jpg?oh=85396a0804c26597082ea83ced808610&oe=565AECF8&__gda__=1444051623_481cc99e7d5a09350b5c31aada3abe17";
    ImageView imgView01;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plist_reader_demo);

        imgView01 = (ImageView) findViewById(R.id.imageView01);

        mQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w(TAG,"response: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"error response: " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("APIKey", APIKEY);
                return headers;
            }
        };

        ImageRequest imageRequest = new ImageRequest(TestUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgView01.setImageBitmap(response);

            }
        },0,0, ImageView.ScaleType.CENTER , Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        //mQueue.add(imageRequest);

        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });


        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imgView01, R.drawable.icon, R.drawable.cloud01);

        imageLoader.get(TestUrl,listener);


    }


    @Override
    protected void onResume() {
        super.onResume();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(APIURL2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.w(TAG,"response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"error response: " + error + "  " + error.getMessage());
            }
        });


        mQueue.add(jsonObjectRequest);
    }
}

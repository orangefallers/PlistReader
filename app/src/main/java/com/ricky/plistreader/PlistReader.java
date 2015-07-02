package com.ricky.plistreader;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.XMLPropertyListParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by ricky on 2015/7/2.
 */
public class PlistReader {

    private final String TAG = "PlistMananger";
    Context mCtx;

    private String plist_filename;
    private String folder_path = null;

    NSDictionary rootNSDict;
    NSDictionary framesNSDict;

    private BitmapRegionDecoder mDecoder;
    Rect keyrect = new Rect();

    String[] numberKeyString = {"zero.png", "one.png", "two.png", "three.png", "four.png", "five.png", "six.png", "seven.png", "eight.png", "nine.png"};

    public PlistReader(Context context, String filename) throws IOException,
            ParserConfigurationException, SAXException,
            PropertyListFormatException, ParseException {

        this.mCtx = context;
        this.plist_filename = filename;

        String png = filename + ".png";
        String plist = filename + ".plist";

        readPlistFormat(plist);
        readBitmapFromAssets(png);

    }

    public PlistReader(Context context, String filename, String foldername)
            throws IOException, ParserConfigurationException, SAXException,
            PropertyListFormatException, ParseException {

        this.mCtx = context;
        this.plist_filename = filename;
        this.folder_path = foldername;

        String png = filename + ".png";
        String plist = filename + ".plist";

        readPlistFormat(plist);
        readBitmapFromAssets(png);

    }


    public PlistReader(Context context, String filename, String foldername, String[] keyarray)
            throws IOException, ParserConfigurationException, SAXException,
            PropertyListFormatException, ParseException {

        this.mCtx = context;
        this.plist_filename = filename;
        this.folder_path = foldername;
        this.numberKeyString = keyarray;

        String png = filename + ".png";
        String plist = filename + ".plist";

        readPlistFormat(plist);
        readBitmapFromAssets(png);

    }

    private void readPlistFormat(String filename) {
        InputStream in = null;

        String foldername;

        if (folder_path == null || folder_path.equals("")) {
            foldername = filename;
        } else {
            foldername = folder_path + "/" + filename;
        }

        try {
            in = mCtx.getResources().getAssets().open(foldername);
            rootNSDict = (NSDictionary) XMLPropertyListParser.parse(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        framesNSDict = (NSDictionary) rootNSDict.get("frames");

    }

    private void readBitmapFromAssets(String filename) {

        String foldername;

        if (folder_path == null || folder_path.equals("")) {
            foldername = filename;
        } else {
            foldername = folder_path + "/" + filename;
        }

        try {
            AssetManager am = mCtx.getAssets();
            InputStream is = am.open(foldername);
            mDecoder = BitmapRegionDecoder.newInstance(is, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageRegion(Rect rect) {

        int x = rect.left;
        int y = rect.top;
        int w = rect.right;
        int h = rect.bottom;
        Rect srcRect = new Rect(x, y, (x + w), (y + h));
        //Log.w(TAG, "bmp rect:" + x + " " + y + " " + w + " " + h + " ");
        return mDecoder.decodeRegion(srcRect, null);

    }

    public Bitmap getBitmapByKey(String key) {

        NSDictionary keyDict = (NSDictionary) framesNSDict.get(key);
        NSObject rectobj = keyDict.get("textureRect");
        Rect rect = getRect(rectobj.toString());
        Bitmap bmp = getImageRegion(rect);

        return bmp;
    }

    public Drawable getDrawableByKey(String key) {

        Drawable drawable = new BitmapDrawable(getBitmapByKey(key));
        return drawable;
    }


    public Bitmap getNumberBitmap(int num, int length) {

        String tmpStr = "" + Math.abs(num);
        int bmpW = 0;
        int x = 0;
        int[] bmpWarray = new int[length];

        for (int i = 0; i < tmpStr.length(); i++) {
            int nm = Integer.parseInt(tmpStr.charAt(i) + "");

            NSDictionary keyDict = (NSDictionary) framesNSDict.get(numberKeyString[nm]);
            NSObject rectobj = keyDict.get("textureRect");
            Rect rect = getRect(rectobj.toString());
            bmpW = bmpW + rect.right;
            bmpWarray[i] = rect.right;
        }

        //Log.w(TAG, "number bmp width = " + bmpW);
        Bitmap numbmp = Bitmap.createBitmap(bmpW, 125, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(numbmp);

        for (int i = 0; i < tmpStr.length(); i++) {
            int nm = Integer.parseInt(tmpStr.charAt(i) + "");

            Bitmap bmp = getBitmapByKey(numberKeyString[nm]);

            if (i > 0){
                x = x + bmpWarray[i];
                canvas.drawBitmap(bmp, x, 0, new Paint());
            }
            else
                canvas.drawBitmap(bmp, 0, 0, new Paint());
        }

        return numbmp;
    }


    public void setFolderName(String foldername) {
        this.folder_path = foldername;
    }

    public String XMLfileString() {
        return rootNSDict.toXMLPropertyList();
    }

    private Rect getRect(String arrayString) {
        String[] items = arrayString.replaceAll("\\{", "")
                .replaceAll("\\}", "").split(",");

        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            results[i] = (int) Float.parseFloat(items[i].trim());
        }

        Rect keyrect = new Rect(results[0], results[1], results[2], results[3]);

//		for (int element : results) {
//			Log.w(TAG, "num:" + element);
//		}
        return keyrect;

    }



}

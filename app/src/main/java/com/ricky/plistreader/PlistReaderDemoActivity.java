package com.ricky.plistreader;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.dd.plist.PropertyListFormatException;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;


public class PlistReaderDemoActivity extends ActionBarActivity {

    private String TestUrl = "https://fbcdn-sphotos-a-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/p180x540/423852_349248718440219_425270433_n.jpg?oh=85396a0804c26597082ea83ced808610&oe=565AECF8&__gda__=1444051623_481cc99e7d5a09350b5c31aada3abe17";

    ImageView imgView01;
    ImageView imgView02;

    PlistReader pReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plist_reader_demo);

        imgView01 = (ImageView) findViewById(R.id.imageView01);
        imgView02 = (ImageView) findViewById(R.id.imageview02);
        try {
            //pManager = new PlistManager(this, "objectsSpritesheet");
            //pReader = new PlistReader(this, "Untitled", "1280x800");
            pReader = new PlistReader(this, "number_default");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Bitmap pBmp = pReader.getBitmapByKey("planet.png");
//        //Log.w("size", "pbmp :"+pBmp.getWidth() + " " + pBmp.getHeight());
//        imgView01.setImageBitmap(pBmp);
//
//        Drawable pdrawable = pReader.getDrawableByKey("cloud03.png");
//        imgView02.setImageDrawable(pdrawable);

//        Drawable pdrawable = pReader.getDrawableByKey("eight.png");
//        imgView01.setImageDrawable(pdrawable);

        imgView01.setImageBitmap(pReader.getNumberBitmap(523, 3));

        new ImageLoader(imgView02).execute(TestUrl);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plist_reader_demo, menu);
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

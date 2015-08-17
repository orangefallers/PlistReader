package com.ricky.plistreader;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by ricky on 2015/7/8.
 */
public class DrawerActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listView_LeftMenu;
    private String[] list_name = {"Plist Reader", "WaterFall Demo", "QR code Demo", "Volley Function"};
    private ArrayAdapter arrayAdapter;
    private ImageView ivRunningMan;
    private AnimationDrawable mAnimationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_demo_layout);

        findViews();

        toolbar.setTitle("Toolbar");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setSubtitle("By orangefaller");
        //toolbar.setLogo(R.drawable.icon);
        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list_name);
        listView_LeftMenu.setAdapter(arrayAdapter);
        listView_LeftMenu.setOnItemClickListener(itemClickListener);

    }

    private void findViews() {
        ivRunningMan = (ImageView) findViewById(R.id.iv_main);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        listView_LeftMenu = (ListView) findViewById(R.id.lv_left_menu);
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            switch (position){
                case 0:
                    Intent intent = new Intent(DrawerActivity.this, PlistReaderDemoActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(DrawerActivity.this, WaterFallActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(DrawerActivity.this, QRscanActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(DrawerActivity.this, VolleyTestActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };
}

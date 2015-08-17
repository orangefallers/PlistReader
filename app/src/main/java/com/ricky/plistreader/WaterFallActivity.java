package com.ricky.plistreader;

import android.app.Activity;
import android.os.Bundle;

import com.origamilabs.library.views.StaggeredGridView;
import com.ricky.plistreader.waterfall.StaggeredAdapter;

/**
 * Created by ricky on 2015/7/27.
 */
public class WaterFallActivity extends Activity{

    private String urls[] = {
            "http://farm7.staticflickr.com/6101/6853156632_6374976d38_c.jpg",
            "http://farm8.staticflickr.com/7232/6913504132_a0fce67a0e_c.jpg",
            "http://farm5.staticflickr.com/4133/5096108108_df62764fcc_b.jpg",
            "http://farm5.staticflickr.com/4074/4789681330_2e30dfcacb_b.jpg",
            "http://farm9.staticflickr.com/8208/8219397252_a04e2184b2.jpg",
            "http://farm9.staticflickr.com/8483/8218023445_02037c8fda.jpg",
            "http://farm9.staticflickr.com/8335/8144074340_38a4c622ab.jpg",
            "http://farm9.staticflickr.com/8060/8173387478_a117990661.jpg",
            "http://farm9.staticflickr.com/8056/8144042175_28c3564cd3.jpg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waterfall_layout);
        StaggeredGridView gridView = (StaggeredGridView) this.findViewById(R.id.staggeredGridView1);

        int margin = getResources().getDimensionPixelSize(R.dimen.margin);

        gridView.setItemMargin(margin); // set the GridView margin

        gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well

        StaggeredAdapter adapter = new StaggeredAdapter(WaterFallActivity.this, R.id.imageView01, urls);

        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

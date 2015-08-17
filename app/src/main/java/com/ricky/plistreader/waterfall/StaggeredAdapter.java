package com.ricky.plistreader.waterfall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ricky.plistreader.ImageLoaders;
import com.ricky.plistreader.R;

/**
 * Created by ricky on 2015/8/4.
 */
public class StaggeredAdapter extends ArrayAdapter<String> {
    public StaggeredAdapter(Context context, int resource) {
        super(context, resource);
    }

    public StaggeredAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        //mLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(getContext());
            convertView = layoutInflator.inflate(R.layout.item_waterfall_view, null);
            holder = new ViewHolder();
            holder.imageView = (ScaleImageView) convertView.findViewById(R.id.imageView01);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();

        //mLoader.DisplayImage(getItem(position), holder.imageView);
        new ImageLoaders(holder.imageView).execute(getItem(position));

        return convertView;
    }

    public static class ViewHolder {
        ScaleImageView imageView;
    }
}

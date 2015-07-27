package com.sam_chordas.android.androidflavors;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sam_chordas on 7/23/15.
 */
public class FlavorAdapter extends ArrayAdapter<Flavor> {

    private static final String LOG_TAG = FlavorAdapter.class.getSimpleName();

    public FlavorAdapter(Activity context, List<Flavor> flavors){
        super(context, 0, flavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Flavor flavor = getItem(position);


        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.flavor_item, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.flavor_image);
        iconView.setImageResource(flavor.image);

        TextView textView = (TextView) convertView.findViewById(R.id.flavor_text);
        textView.setText(flavor.name);

        return convertView;
    }

}

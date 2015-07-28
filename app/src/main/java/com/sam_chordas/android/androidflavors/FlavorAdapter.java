package com.sam_chordas.android.androidflavors;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sam_chordas.android.androidflavors.data.FlavorsContract;

/**
 * Created by sam_chordas on 7/23/15.
 */
public class FlavorAdapter extends CursorAdapter {

    private static final String LOG_TAG = FlavorAdapter.class.getSimpleName();
    private Context mContext;
    private static int sLoaderID;

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView textView;

        public ViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.flavor_image);
            textView = (TextView) view.findViewById(R.id.flavor_text);
        }
    }

    public FlavorAdapter(Context context, Cursor c, int flags, int loaderID){
        super(context, c, flags);
        mContext = context;
        sLoaderID = loaderID;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        int layoutId = R.layout.flavor_item;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int versionIndex = cursor.getColumnIndex(FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME);
        final String versionName = cursor.getString(versionIndex);
        viewHolder.textView.setText(versionName);

        int imageIndex = cursor.getColumnIndex(FlavorsContract.FlavorEntry.COLUMN_ICON);
        byte[] bytes = cursor.getBlob(imageIndex);
        Bitmap map = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        viewHolder.imageView.setImageBitmap(map);

    }

}

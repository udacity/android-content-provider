package com.sam_chordas.android.androidflavors;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.sam_chordas.android.androidflavors.data.FlavorsContract;

import java.nio.ByteBuffer;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private FlavorAdapter mFlavorAdapter;

    private static final int CURSOR_LOADER_ID = 0;

    Flavor[] flavors = {
            new Flavor("Cupcake", R.drawable.cupcake),
            new Flavor("Donut", R.drawable.donut),
            new Flavor("Eclair", R.drawable.eclair),
            new Flavor("Froyo", R.drawable.froyo),
            new Flavor("GingerBread", R.drawable.gingerbread),
            new Flavor("Honeycomb", R.drawable.honeycomb),
            new Flavor("Ice Cream Sandwich", R.drawable.icecream),
            new Flavor("Jelly Bean", R.drawable.jellybean),
            new Flavor("KitKat", R.drawable.kitkat),
            new Flavor("Lollipop", R.drawable.lollipop)
    };

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mFlavorAdapter = new FlavorAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);

        GridView gridView = (GridView) rootView.findViewById(R.id.flavors_grid);
        gridView.setAdapter(mFlavorAdapter);

        return rootView;
    }

    public void insertData(){
        ContentValues[] flavorValuesArr = new ContentValues[flavors.length];
        for(int i = 0; i < flavors.length; i++){

            // Convert image to byte array to store in SQL blob
            Bitmap map = BitmapFactory.decodeResource(getResources(), flavors[i].image);
            int bytes = map.getRowBytes() * map.getHeight();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes);
            map.copyPixelsToBuffer(byteBuffer);
            byte[] bytesArr = byteBuffer.array();

            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_ICON,
                    bytesArr);

            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME,
                    flavors[i].name);
        }

        getActivity().getContentResolver().bulkInsert(FlavorsContract.FlavorEntry.CONTENT_URI,
                flavorValuesArr);
    }
}

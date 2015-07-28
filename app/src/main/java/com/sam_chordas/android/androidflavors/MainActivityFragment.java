package com.sam_chordas.android.androidflavors;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.sam_chordas.android.androidflavors.data.FlavorsContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private FlavorAdapter mFlavorAdapter;
    private GridView mGridView;

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
    public void onActivityCreated(Bundle savedInstanceState){
        getActivity().getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        insertData();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mFlavorAdapter = new FlavorAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);
        mGridView = (GridView) rootView.findViewById(R.id.flavors_grid);
        mGridView.setAdapter(mFlavorAdapter);

        return rootView;
    }

    public void insertData(){
        ContentValues[] flavorValuesArr = new ContentValues[flavors.length];
        for(int i = 0; i < flavors.length; i++){
            flavorValuesArr[i] = new ContentValues();
            Log.i(LOG_TAG, "Integer reference to drawable: " + flavors[i].image);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_ICON, flavors[i].image);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME,
                    flavors[i].name);
        }
        Log.d(LOG_TAG, "Uri: )" + FlavorsContract.FlavorEntry.CONTENT_URI.toString());

        getActivity().getContentResolver().bulkInsert(FlavorsContract.FlavorEntry.CONTENT_URI,
                flavorValuesArr);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(getActivity(),
                FlavorsContract.FlavorEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFlavorAdapter.changeCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mFlavorAdapter.changeCursor(null);
    }
}

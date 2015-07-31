package com.sam_chordas.android.androidflavors;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    // Static values for our flavors
    // content credit https://www.android.com/intl/en_us/history/
    Flavor[] flavors = {
            new Flavor("Cupcake", "The first release of Android", R.drawable.cupcake),
            new Flavor("Donut", "The world's information is at your fingertips – " +
                    "search the web, get driving directions... or just watch cat videos.",
                    R.drawable.donut),
            new Flavor("Eclair", "Make your home screen just how you want it. Arrange apps " +
                    "and widgets across multiple screens and in folders. Stunning live wallpapers " +
                    "respond to your touch.", R.drawable.eclair),
            new Flavor("Froyo", "Voice Typing lets you input text, and Voice Actions let " +
                    "you control your phone, just by speaking.", R.drawable.froyo),
            new Flavor("GingerBread", "New sensors make Android great for gaming - so " +
                    "you can touch, tap, tilt, and play away.", R.drawable.gingerbread),
            new Flavor("Honeycomb", "Optimized for tablets, this release opens up new " +
                    "horizons wherever you are.", R.drawable.honeycomb),
            new Flavor("Ice Cream Sandwich", "Android comes of age with a new, refined design. " +
                    "Simple, beautiful and beyond smart.", R.drawable.icecream),
            new Flavor("Jelly Bean", "Android is fast and smooth with buttery graphics. " +
                    "With Google Now, you get just the right information at the right time.",
                    R.drawable.jellybean),
            new Flavor("KitKat", "Smart, simple, and truly yours. A more polished design, " +
                    "improved performance, and new features.", R.drawable.kitkat),
            new Flavor("Lollipop", "A sweet new take on Android. Get the smarts of Android on" +
                    " screens big and small – with the right information at the right moment.",
                    R.drawable.lollipop)
    };

    public MainActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Cursor c =
            getActivity().getContentResolver().query(FlavorsContract.FlavorEntry.CONTENT_URI,
            new String[]{FlavorsContract.FlavorEntry._ID},
                    null,
                    null,
                    null);
        if (c.getCount() == 0){
            insertData();
        }
        // initialize loader
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate fragment_main layout
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        // initialize our FlavorAdapter
        mFlavorAdapter = new FlavorAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);
        // initialize mGridView to the GridView in fragment_main.xml
        mGridView = (GridView) rootView.findViewById(R.id.flavors_grid);
        // set mGridView adapter to our CursorAdapter
        mGridView.setAdapter(mFlavorAdapter);

        // make each item clickable
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // increment the position to match Database Ids indexed starting at 1
                int uriId = position + 1;
                // append Id to uri
                Uri uri = ContentUris.withAppendedId(FlavorsContract.FlavorEntry.CONTENT_URI,
                        uriId);
                // create fragment
                DetailFragment detailFragment = DetailFragment.newInstance(uriId, uri);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null).commit();
            }
        });


        return rootView;
    }

    // insert data into database
    public void insertData(){
        ContentValues[] flavorValuesArr = new ContentValues[flavors.length];
        // Loop through static array of Flavors, add each to an instance of ContentValues
        // in the array of ContentValues
        for(int i = 0; i < flavors.length; i++){
            flavorValuesArr[i] = new ContentValues();
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_ICON, flavors[i].image);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME,
                    flavors[i].name);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_DESCRIPTION,
                    flavors[i].description);
        }

        // bulkInsert our ContentValues array
        getActivity().getContentResolver().bulkInsert(FlavorsContract.FlavorEntry.CONTENT_URI,
                flavorValuesArr);
    }

    // Attach loader to our flavors database query
    // run when loader is initialized
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

    // Set the cursor in our CursorAdapter once the Cursor is loaded
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mFlavorAdapter.swapCursor(data);


    }

    // reset CursorAdapter on Loader Reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mFlavorAdapter.swapCursor(null);
    }
}

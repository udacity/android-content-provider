package com.sam_chordas.android.androidflavors;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sam_chordas.android.androidflavors.data.FlavorsContract;



public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private Cursor mDetailCursor;
    private View mRootView;
    private int mPosition;
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mUriText;
    private Uri mUri;
    private static final int CURSOR_LOADER_ID = 0;


    public static DetailFragment newInstance(int position, Uri uri) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.mPosition = position;
        fragment.mUri = uri;
        args.putInt("id", position);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mImageView = (ImageView) rootView.findViewById(R.id.flavor_icon);
        mTextView = (TextView) rootView.findViewById(R.id.version_description);
        mUriText = (TextView) rootView.findViewById(R.id.uri);
        Bundle args = this.getArguments();
        getLoaderManager().initLoader(CURSOR_LOADER_ID, args, DetailFragment.this);

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        String selection = null;
        String [] selectionArgs = null;
        if (args != null){
            selection = FlavorsContract.FlavorEntry._ID;
            selectionArgs = new String[]{String.valueOf(mPosition)};
        }
        return new CursorLoader(getActivity(),
                mUri,
                null,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    // Set the cursor in our CursorAdapter once the Cursor is loaded
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mDetailCursor = data;
        mDetailCursor.moveToFirst();
        DatabaseUtils.dumpCursor(data);
        mImageView.setImageResource(mDetailCursor.getInt(3));
        mTextView.setText(mDetailCursor.getString(2));
        // set Uri to be displayed
        mUriText.setText(mUri.toString());
    }

    // reset CursorAdapter on Loader Reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mDetailCursor = null;
    }

}

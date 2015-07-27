package com.sam_chordas.android.androidflavors;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private FlavorAdapter mFlavorAdapter;

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
        mFlavorAdapter = new FlavorAdapter(getActivity(), Arrays.asList(flavors));

        GridView gridView = (GridView) rootView.findViewById(R.id.flavors_grid);
        gridView.setAdapter(mFlavorAdapter);

        return rootView;
    }
}

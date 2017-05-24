package com.fxlc.zklm.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxlc.zklm.R;

/**
 * Created by cyd on 2017/3/21.
 */

public class GoodsFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TAG = GoodsFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String[] PLANETS = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};

    public GoodsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GoodsFragment newInstance(int sectionNumber) {
        GoodsFragment fragment = new GoodsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        int section = getArguments().getInt(ARG_SECTION_NUMBER);

        rootView = inflater.inflate(R.layout.fragment_goods, container, false);


        return rootView;
    }
}
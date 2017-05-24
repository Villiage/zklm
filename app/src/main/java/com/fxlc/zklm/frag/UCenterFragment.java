package com.fxlc.zklm.frag;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fxlc.zklm.test.CarNoActivity;
import com.fxlc.zklm.test.ContactActivity;
import com.fxlc.zklm.test.IDcardTestActivity;
import com.fxlc.zklm.R;
import com.fxlc.zklm.activity.LoginActivity;
import com.fxlc.zklm.test.PlaceTestActivity;
import com.fxlc.zklm.test.TestActivity;

/**
 * Created by cyd on 2017/3/21.
 */

public class UCenterFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TAG = UCenterFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String[] PLANETS = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};

    public UCenterFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UCenterFragment newInstance(int sectionNumber) {
        UCenterFragment fragment = new UCenterFragment();
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

        rootView = inflater.inflate(R.layout.fragment_ucenter, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getActivity(), IDcardTestActivity.class);
                it.putExtra("id", "001");
                startActivity(it);
            }
        });
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getActivity(), LoginActivity.class);
                it.putExtra("id", "001");
                startActivity(it);
            }
        });
        view.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getActivity(), TestActivity.class);
                it.putExtra("id", "001");
                startActivity(it);
            }
        });
        view.findViewById(R.id.place).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getActivity(), PlaceTestActivity.class);
                it.putExtra("id", "001");
                startActivity(it);
            }
        });
        view.findViewById(R.id.carno).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getActivity(), CarNoActivity.class);

                startActivity(it);
            }
        });
        view.findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getActivity(), ContactActivity.class);

                startActivity(it);
            }
        });

        view.findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayMetrics metrics = new DisplayMetrics();
                 getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();

                Rect rectangle= new Rect();
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
                Log.d("hello", "metrics" + rectangle.top);
            }
        });

    }

}

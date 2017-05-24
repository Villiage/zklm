package com.fxlc.zklm;

import android.support.v4.app.Fragment;

import com.fxlc.zklm.frag.CarsFragment;
import com.fxlc.zklm.frag.GoodsFragment;
import com.fxlc.zklm.frag.HomeFragment;
import com.fxlc.zklm.frag.UCenterFragment;

/**
 * Created by cyd on 2017/3/21.
 */

public class FragmentHelper {


    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(0);

            case 1:
                return CarsFragment.newInstance(1);

            case 2:

                return GoodsFragment.newInstance(2);
            case 3:
                return UCenterFragment.newInstance(3);

        }

        return null;
    }


}

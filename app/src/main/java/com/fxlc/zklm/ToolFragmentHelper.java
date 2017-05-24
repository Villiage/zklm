package com.fxlc.zklm;

import android.support.v4.app.Fragment;

import com.fxlc.zklm.frag.ToolCarFrag;
import com.fxlc.zklm.frag.ToolFinanceFrag;
import com.fxlc.zklm.frag.ToolTransformFrag;

/**
 * Created by cyd on 2017/3/21.
 */

public class ToolFragmentHelper {


    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ToolFinanceFrag.newInstance();

            case 1:
                return ToolTransformFrag.newInstance();

            case 2:
                return ToolCarFrag.newInstance();


        }

        return null;
    }






}

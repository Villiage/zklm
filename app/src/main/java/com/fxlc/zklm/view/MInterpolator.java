package com.fxlc.zklm.view;

import android.view.animation.Interpolator;

/**
 * Created by cyd on 2017/3/27.
 */

public class MInterpolator implements Interpolator{
    @Override
    public float getInterpolation(float x) {
        if (x < 0.5)
            return 0.5f * a(x * 2.0f);
        else
            return 0.5f * (o(x * 2.0f - 2.0f) + 2.0f);
    }
    private float a(float t) {

        return t * t * t;
    }

    private float o(float t) {

        return t * t * t;
    }
}

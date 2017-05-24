package com.fxlc.zklm.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.io.File;

/**
 * Created by cyd on 2017/4/18.
 */

public class FileUtil {



    @Nullable
    public static File getPhotoCacheDir(Context context, String cacheName) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File result = new File(cacheDir, cacheName);
            if (!result.mkdirs() && (!result.exists() || !result.isDirectory())) {
                // File wasn't able to create a directory, or the result exists but not a directory
                return null;
            }
            return result;
        }

        return null;
    }

    private static String pkgName = "com.zklm";

    public static File getSaveFile(Context context,String fileName) {
        File parentFile = null;
        File pkgFile = null;
        if (isSDcard()) {
            parentFile = Environment.getExternalStorageDirectory();

        } else {
            parentFile = context.getCacheDir();
        }
        pkgFile = new File(parentFile, pkgName);
        if (!pkgFile.exists())
            pkgFile.mkdir();
        return new File(pkgFile, fileName);

    }

    public static boolean isSDcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}

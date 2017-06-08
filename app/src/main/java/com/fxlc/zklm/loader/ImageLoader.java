package com.fxlc.zklm.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.fxlc.zklm.bean.MediaStoreData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyd on 2017/5/27.
 */

public class ImageLoader {



    public static ArrayList<MediaStoreData> loadAllImg(Context context) {
        ArrayList<MediaStoreData> data = new ArrayList<>();
        ContentResolver mContentResolver = context
                .getContentResolver();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null, null, null,
                MediaStore.Images.Media.DATE_MODIFIED + " DESC");
        while (mCursor.moveToNext()) {
            // 获取图片的路径

            long id = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            Uri uri = Uri.fromFile(new File(path));
            String mime = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE));
            long datetoken = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));
            long dateModify = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED));
            int oritation = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            MediaStoreData img = new MediaStoreData(id, uri, mime, datetoken, dateModify, oritation, MediaStoreData.Type.IMAGE,false);

            data.add(img);
        }
        mCursor.close();
        return data;
    }
}

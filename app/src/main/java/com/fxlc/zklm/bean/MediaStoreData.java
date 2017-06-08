package com.fxlc.zklm.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cyd on 2017/5/27.
 */

public class MediaStoreData implements Parcelable{
    public static final Parcelable.Creator<MediaStoreData> CREATOR = new Parcelable.Creator<MediaStoreData>() {
        @Override
        public MediaStoreData createFromParcel(Parcel parcel) {
            return new MediaStoreData(parcel);
        }

        @Override
        public MediaStoreData[] newArray(int i) {
            return new MediaStoreData[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public final long rowId;
    public final Uri uri;
    public final String mimeType;
    public final long dateModified;
    public final int orientation;
    public final Type type;
    public final long dateTaken;
    public  boolean statu;

    public MediaStoreData(long rowId, Uri uri, String mimeType, long dateTaken, long dateModified,
                          int orientation, Type type,boolean statu) {
        this.rowId = rowId;
        this.uri = uri;
        this.dateModified = dateModified;
        this.mimeType = mimeType;
        this.orientation = orientation;
        this.type = type;
        this.dateTaken = dateTaken;
        this.statu = statu;
    }

    MediaStoreData(Parcel in) {
        rowId = in.readLong();
        uri = Uri.parse(in.readString());
        mimeType = in.readString();
        dateTaken = in.readLong();
        dateModified = in.readLong();
        orientation = in.readInt();
        type = Type.valueOf(in.readString());
        statu = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(rowId);
        parcel.writeString(uri.toString());
        parcel.writeString(mimeType);
        parcel.writeLong(dateTaken);
        parcel.writeLong(dateModified);
        parcel.writeInt(orientation);
        parcel.writeString(type.name());
        parcel.writeByte((byte)(statu?1:0) );
    }

    @Override
    public String toString() {
        return "MediaStoreData{"
                + "rowId=" + rowId
                + ", uri=" + uri
                + ", mimeType='" + mimeType + '\''
                + ", dateModified=" + dateModified
                + ", orientation=" + orientation
                + ", type=" + type
                + ", dateTaken=" + dateTaken
                + '}';
    }

    /**
     * The type of data.
     */
    public enum Type {
        VIDEO,
        IMAGE,
    }
}

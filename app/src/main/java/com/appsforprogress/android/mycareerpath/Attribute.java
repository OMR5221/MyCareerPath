package com.appsforprogress.android.mycareerpath;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oswald on 3/12/2016.
 */
public class Attribute implements Parcelable
{
    private static final int DETAILS_COUNT = 5;
    private final String mAttrName;
    private final String mCareerTitle;
    private final String mElementId;
    private final String mElementName;

    public Attribute(String attrName, String carTitle, String elemId, String elemName)
    {
        mAttrName = attrName;
        mCareerTitle = carTitle;
        mElementId = elemId;
        mElementName = elemName;
    }

    public String getAttributeName() {
        return mAttrName;
    }

    public String getCareerName() {
        return mCareerTitle;
    }

    public String getElementId() {
        return mElementId;
    }

    public String getElementName() {
        return mElementName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.mAttrName);
        dest.writeString(this.mCareerTitle);
        dest.writeString(this.mElementId);
        dest.writeString(this.mElementName);
    }

    private Attribute(Parcel in)
    {
        this.mAttrName = in.readString();
        this.mCareerTitle = in.readString();
        this.mElementId = in.readString();
        this.mElementName = in.readString();
    }

    public static final Parcelable.Creator<Attribute> CREATOR = new Parcelable.Creator<Attribute>()
    {
        public Attribute createFromParcel(Parcel source)
        {
            return new Attribute(source);
        }

        public Attribute[] newArray(int size)
        {
            return new Attribute[size];
        }
    };
}

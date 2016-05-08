package com.appsforprogress.android.mycareerpath;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Oswald on 3/12/2016.
 */
public class Attribute //implements Parcelable
{
    // Define the fields for Skill Model
    private UUID mId;
    private String mCareerName;
    private String mONetCode;
    private String mElementId;
    private String mElementName;
    private String mScaleId;
    private String mScaleName;
    private String mDataValue;
    private String mDateAdded;


    public Attribute()
    {
        // Call the secondary constructor below with a random UUID:
        this(UUID.randomUUID());
    }

    public Attribute(UUID id)
    {
        mId = id;
    }

    // Getter for mId
    public UUID getId()
    {
        return mId;
    }

    public String getCareerName()
    {
        return mCareerName;
    }

    public void setCareerName(String career)
    {
        mCareerName = career;
    }

    public String getONetCode() {
        return mONetCode;
    }

    public String getElementId() {
        return mElementId;
    }

    public String getElementName() {
        return mElementName;
    }

    public String getScaleId() {
        return mScaleId;
    }

    public String getDataValue() {
        return mDataValue;
    }

    public String getDateAdded() {
        return mDateAdded;
    }

    public void setONetCode(String ONetCode) {
        mONetCode = ONetCode;
    }

    public void setElementId(String elementId) {
        mElementId = elementId;
    }

    public void setElementName(String elementName) {
        mElementName = elementName;
    }

    public void setScaleId(String scaleId) {
        mScaleId = scaleId;
    }

    public void setDataValue(String dataValue) {
        mDataValue = dataValue;
    }

    public void setDateAdded(String dateAdded) {
        mDateAdded = dateAdded;
    }

    public String getScaleName() {
        return mScaleName;
    }

    public void setScaleName(String scaleName) {
        mScaleName = scaleName;
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.mONetCode);
        dest.writeString(this.mElementId);
        dest.writeString(this.mElementName);
        dest.writeString(this.mScaleId);
    }

    private Attribute(Parcel in)
    {
        this.mONetCode = in.readString();
        this.mElementId = in.readString();
        this.mElementName = in.readString();
        this.mScaleId = in.readString();
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
    */
}

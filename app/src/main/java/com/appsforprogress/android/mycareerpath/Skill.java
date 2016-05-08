package com.appsforprogress.android.mycareerpath;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Oswald on 1/3/2016.
 */
public class Skill extends Attribute
{
    // DB Specific Fields:
    private String mN;
    private String mStandardError;
    private String mLowerCIBound;
    private String mUpperCIBound;
    private String mRecommendSuppressStr;
    private Boolean mRecommendSuppressBool;
    private String mNotRelevantStr; // Should be converted into a boolean
    private Boolean mNotRelevantBool;

    // App Specific Fields:
    private Integer mProficiency;
    // String for name of peer providing endorsement:
    private String mPeerName;

    // Define default Constructor:
    public Skill()
    {
        super();
    }

    // Getter and setters:
    public String getN()
    {
        return mN;
    }

    public void setN(String n) {
        mN = n;
    }

    public String getStandardError() {
        return mStandardError;
    }

    public void setStandardError(String standardError) {
        mStandardError = standardError;
    }

    public String getLowerCIBound() {
        return mLowerCIBound;
    }

    public void setLowerCIBound(String lowerCIBound) {
        mLowerCIBound = lowerCIBound;
    }

    public String getUpperCIBound() {
        return mUpperCIBound;
    }

    public void setUpperCIBound(String upperCIBound) {
        mUpperCIBound = upperCIBound;
    }

    public String getRecommendSuppressStr() {
        return mRecommendSuppressStr;
    }

    public void setRecommendSuppressStr(String recommendSuppressStr) {
        mRecommendSuppressStr = recommendSuppressStr;
    }

    public Boolean getRecommendSuppressBool() {
        return mRecommendSuppressBool;
    }

    public void setRecommendSuppressBool(Boolean recommendSuppressBool) {
        mRecommendSuppressBool = recommendSuppressBool;
    }

    public String getNotRelevantStr() {
        return mNotRelevantStr;
    }

    public void setNotRelevantStr(String notRelevantStr) {
        mNotRelevantStr = notRelevantStr;
    }

    public Boolean getNotRelevantBool() {
        return mNotRelevantBool;
    }

    public void setNotRelevantBool(Boolean notRelevantBool) {
        mNotRelevantBool = notRelevantBool;
    }

    public Integer getProficiency() {
        return mProficiency;
    }

    public void setProficiency(Integer proficiency) {
        mProficiency = proficiency;
    }

    public String getPeerName() {
        return mPeerName;
    }

    public void setPeerName(String peerName) {
        mPeerName = peerName;
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.mRecommendSuppressStr);
        dest.writeString(this.mNotRelevantStr);
        dest.writeString(this.mPeerName);
    }

    private Skill(Parcel in)
    {

        this.mRecommendSuppressStr = in.readString();
        this.mNotRelevantStr = in.readString();
    }

    public static final Parcelable.Creator<Attribute> CREATOR = new Parcelable.Creator<Attribute>()
    {
        public Attribute createFromParcel(Parcel source)
        {

            return new Skill(source);
        }

        public Attribute[] newArray(int size)
        {
            return new Attribute[size];
        }
    };
    */
}

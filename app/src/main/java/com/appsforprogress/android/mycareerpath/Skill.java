package com.appsforprogress.android.mycareerpath;

import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Oswald on 1/3/2016.
 */
public class Skill
{
    // Define the fields for Skill Model
    private UUID mId;
    private String mTitle;
    private Date mAddedDate;
    private boolean mExperienced;
    // String for name of peer providing endorsement:
    private String mPeer;

    public Integer getIndex()
    {
        return mIndex;
    }

    public void setIndex(Integer index) {
        mIndex = index;
    }

    private Integer mIndex;

    // Define constructor for the Skill Model
    public Skill()
    {
        /* OLD: Generate a unique identifier
        mId = UUID.randomUUID();
        mAddedDate = new Date(); */

        // Call the secondary constructor below with a random UUID:
        this(UUID.randomUUID());
    }

    public Skill(UUID id)
    {
        mId = id;
        mAddedDate = new Date();
    }

    // Getter for mId
    public UUID getId() {
        return mId;
    }

    // Getter and setter for the Skill Title
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    // Getter and setter for Date

    public Date getAddedDate() {
        return mAddedDate;
    }

    public void setAddedDate(Date addedDate) {
        mAddedDate = addedDate;
    }

    // Getter and setter for Experienced boolean

    public boolean isExperienced() {
        return mExperienced;
    }

    public void setExperienced(boolean experienced) {
        mExperienced = experienced;
    }

    public void setPeer(String peerName)
    {
        mPeer = peerName;
    }

    public String getPeer()
    {
        return mPeer;
    }
}

package com.appsforprogress.android.mycareerpath;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Oswald on 1/3/2016.
 */
public class User
{
    // Define the fields for Skill Model
    private UUID mUserId;
    private String mFirstName;
    private String mLastName;
    private Date mBirthDate;
    private String mName;
    private String mEmail;
    private String mFaceBookID;
    private Date mSignUpDate;

    // Define constructor for the Skill Model
    public User()
    {
        // Call the secondary constructor below with a random UUID:
        this(UUID.randomUUID());
    }

    public User(UUID id)
    {
        mUserId = id;
        mSignUpDate = new Date();
    }

    // Getter for mId
    public UUID getUserId()
    {
        return mUserId;
    }

    // Getter and setters


    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFaceBookID() {
        return mFaceBookID;
    }

    public void setFaceBookID(String faceBookID) {
        mFaceBookID = faceBookID;
    }

    public Date getSignUpDate()
    {
        return mSignUpDate;
    }

    public void setSignUpDate(Date signUpDate)
    {
        mSignUpDate = signUpDate;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }
}

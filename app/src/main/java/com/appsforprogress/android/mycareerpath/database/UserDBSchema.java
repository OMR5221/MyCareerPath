package com.appsforprogress.android.mycareerpath.database;

/**
 * Created by Oswald on 3/5/2016.
 */
public class UserDBSchema
{
    public static final class UserTable
    {
        public static final String TABLE_NAME = "user";

        // Define the Skill Table's columns:
        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String BIRTH_DATE = "birth_date";
            public static final String SIGNUP_DATE = "signup_date";
        }
    }
}

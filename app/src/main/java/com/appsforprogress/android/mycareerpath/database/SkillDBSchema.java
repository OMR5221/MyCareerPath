package com.appsforprogress.android.mycareerpath.database;

/**
 * Created by Oswald on 2/18/2016.
 */
public class SkillDBSchema
{
    public static final class SkillTable
    {
        public static final String TABLE_NAME = "skills";

        // Define the Skill Table's columns:
        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String EXPERIENCE = "experience";
        }
    }
}

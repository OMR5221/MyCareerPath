package com.appsforprogress.android.mycareerpath.database;

import java.lang.reflect.Field;

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
            public static final String ONET_CODE = "onet_code";
            public static final String CAREER_NAME = "career_name";
            public static final String ELEMENT_ID = "element_id";
            public static final String ELEMENT_NAME = "element_name";
            public static final String SCALE_ID = "scale_id";
            public static final String SCALE_NAME = "scale_name";
            public static final String DATA_VALUE = "data_value";
            public static final String N_VALUE = "n_value";
            public static final String STANDARD_ERROR = "standard_error";
            public static final String LOWER_CI_BOUND = "low_ci_bound";
            public static final String UPPER_CI_BOUND= "upper_ci_bound";
            public static final String RECOMMEND_SUPPRESS = "recommend_suppress";
            public static final String NOT_RELEVANT = "not_relevant";
            public static final String DATE_ADDED = "date_added";
            public static final String PROFICIENCY = "proficiency";
            public static final String PEER_NAME = "peer_name";
        }
    }


}

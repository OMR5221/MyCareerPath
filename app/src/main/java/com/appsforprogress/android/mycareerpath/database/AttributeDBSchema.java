package com.appsforprogress.android.mycareerpath.database;

/**
 * Created by Oswald on 2/18/2016.
 */
public class AttributeDBSchema
{
    public static final class AttributeTable
    {
        public static final String TABLE_NAME = "attributes";

        // Define the Attribute Table's columns:
        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String ATTRIBUTE_TYPE = "attribute_type";
            public static final String ONET_CODE = "onet_code";
            public static final String ELEMENT_ID = "element_id";
            public static final String ELEMENT_NAME = "element_name";
            public static final String SCALE_ID = "scale_id";
            public static final String DATA_VALUE = "data_value";
        }
    }
}

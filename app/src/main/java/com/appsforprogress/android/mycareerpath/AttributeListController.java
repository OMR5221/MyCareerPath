package com.appsforprogress.android.mycareerpath;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appsforprogress.android.mycareerpath.database.AttributeCursorWrapper;
import com.appsforprogress.android.mycareerpath.database.AttributeDBHelper;
import com.appsforprogress.android.mycareerpath.database.AttributeDBSchema;
import com.appsforprogress.android.mycareerpath.database.AttributeDBSchema.AttributeTable;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema;

import org.w3c.dom.Attr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.PublicKey;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Oswald on 1/10/2016.
**/
public class AttributeListController<T>
{
    // Create a Hash Table to hold each of the Attribute Tabs Lists:
    private static Hashtable mAttributeLists;

    // Holds static singleton to list the various skills within
    // private static AttributeList sAttributeList;
    // private List<Skill> mSkills;

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mAttributesDatabase;
    private final String fPackageName = "com.appsforprogress.android.mycareerpath.";
    private static final String[] fAttributeList =
    {
        "SkillList"
    };


    // Specific Constructor to read all Attribute Type Data into the Database:
    public AttributeListController(Context context)
    {
        // Setup and open a Writable DB:
        mContext = context.getApplicationContext();
        mAttributesDatabase = new AttributeDBHelper(mContext).getWritableDatabase();
        mAttributeLists = new Hashtable();

        // Call each attribute Types List constructor functions to populate DB:
        for (String attr : fAttributeList)
        {
            String className = fPackageName + attr;

            try {

                // Get the Class Object:
                Class<?> c = Class.forName(className);

                Constructor<?> ct;

                Method[] allMethods = c.getDeclaredMethods();

                Object t = c.newInstance();

                for (Method m : allMethods)
                {
                    String mname = m.getName();

                    if (!mname.equals("get")) //)|| (m.getGenericReturnType() != boolean.class))
                    {
                        continue;
                    }

                    Type[] pType = m.getGenericParameterTypes();

                    if ((pType.length != 1) || Locale.class.isAssignableFrom(pType[0].getClass()))
                    {
                        continue;
                    }

                    m.setAccessible(true);

                    try
                    {
                        // Create a new Instance of the Class
                        Object o = m.invoke(t);

                        // c.toString() AttrSubList = (c.toString()) o;

                        // Add created Objects to the Hash (Key: Attribute Name, Value: AttributeList Subclass Object)
                        mAttributeLists.put(attr, o);
                    }
                    catch (IllegalAccessException ia)
                    {
                        System.err.println("No access to create an instance of this Class.");
                        System.exit(0);
                    }
                    catch (InvocationTargetException iv)
                    {
                        System.err.println("AttributeList: Issue invoking instance of Object.");
                        System.exit(0);
                    }
                }
            }
            catch (ClassNotFoundException e)
            {
                System.err.println("No applicable Class found.");
                System.exit(0);
            }
                catch (InstantiationException e)
                {
                e.printStackTrace();
            } catch (IllegalAccessException e)
                {
                e.printStackTrace();
            }
        }

        // Close DB Transaction:
        mAttributesDatabase.setTransactionSuccessful();
        mAttributesDatabase.endTransaction();


    }

    public static Hashtable getAttributeLists() {
        return mAttributeLists;
    }

    public static <T extends AttributeList> T getAttributeListsElement(String attrName) {
        return (T) mAttributeLists.get(attrName);
    }

    public static void setAttributeLists(Hashtable mAttributeLists) {
        AttributeListController.mAttributeLists = mAttributeLists;
    }

    // get the AttributeList object in use otherwise create a new one:
    public static <T extends AttributeList> T get(Context context, String attrName)
    {
        T out = null;

        Class<?>c = mAttributeLists.get(attrName).getClass();

        try
        {
            c = mAttributeLists.get(attrName).getClass();

            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("get", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = (T) m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        return out;
        // return c.cast(out);
    }


    /*
    public <T extends Attribute> T getSubClass(T attrType)
    {
        // Get the Class Object:
        Class<?> c = attrType.getClass();

        Constructor<?> ct;

        Object o = null;

        try {

            ct = c.getConstructor();

            try {
                // Create a new Instance of the Class
                o = ct.newInstance();
            }
            catch (InstantiationException is)
            {
                System.err.println("Cannot create an instance of the Class.");
                System.exit(0);
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("No applicable Constructor found.");
            System.exit(0);
        }

        return o;
    }



    // Use the CursorWrapper to retrieve properly formed database record values from the tables in the DB:
    public AttributeCursorWrapper selectRawRecords(String attrName)
    {
        Object out = new Object();

        try
        {
            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("selectRawRecords", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        return (AttributeCursorWrapper) out;
    }

    // Getter to format records selected:
    public List<T> selectFormattedRecords(String attrName)
    {
        List<T> out = null;

        try
        {
            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("selectFormattedRecords", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = (List<T>) m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        return out;
    }

    // getter to return a single skill from mSkills
    public Attribute formatRecord(String attrName, UUID id)
    {
        Object out = new Object();

        try
        {
            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("formatRecord", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        return (Attribute) out;
    }

    public void insertRecord(String attrName, Attribute newAttribute)
    {
        Object out = new Object();

        try
        {
            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("insertRecord", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }
    }

    // Delete a skill from the database:
    public void removeRecord(String attrName, Attribute removeAttribute)
    {
        Object out = new Object();

        try
        {
            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("removeRecord", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }
    }

    public void updateRecord(String attrName, Attribute updateAttribute)
    {
        Object out = new Object();

        try
        {
            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("selectFormattedRecords", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }
    }

    // WRITE to the columns in our DB:
    public ContentValues getContentValues(String attrName, Attribute attr)
    {
        Object out = new Object();

        try
        {
            // GET the method to be invoked:
            Method m = mAttributeLists.get(attrName).getClass().getMethod("selectFormattedRecords", String.class);

            try
            {
                // Attempt to invoke the method found:
                out = m.invoke(mAttributeLists.get(attrName));
            }
            catch (IllegalAccessException ia)
            {
                System.err.println("No access to create an instance of this Class.");
                System.exit(0);
            }
            catch (InvocationTargetException iv)
            {
                System.err.println("AttributeList: Issue invoking instance of Object.");
                System.exit(0);
            }
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("The method specified does not exist.");
            System.exit(0);
        }

        return (ContentValues) out;
    }
    */
}

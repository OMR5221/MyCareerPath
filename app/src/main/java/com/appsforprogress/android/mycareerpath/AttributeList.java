package com.appsforprogress.android.mycareerpath;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appsforprogress.android.mycareerpath.database.AttributeCursorWrapper;
import com.appsforprogress.android.mycareerpath.database.AttributeDBHelper;
import com.appsforprogress.android.mycareerpath.database.SkillCursorWrapper;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Oswald on 1/10/2016.
 */

public class AttributeList<T extends Attribute>
{

    // Holds static singleton to list the various skills within
    private static AttributeList sAttributeList;
    private List<? extends Attribute> mAttributes;

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mAttributesDatabase;
    private AttributeDBHelper mDBHelper;

    // Tab which created the AttributeList
    private String mAttributeTabName;

    // get the AttributeList object in use
    public static AttributeList get(Context context, String attrType)
    {
        if (sAttributeList == null)
        {
            sAttributeList = new AttributeList(context, attrType);
        }

        return sAttributeList;
    }

    private AttributeList(Context context, String attrType)
    {

        // Need to pull records into this object from DB:



        /*
        mContext = context.getApplicationContext();

        mDBHelper = new AttributeDBHelper(mContext);

        // Create the db and its tables:

        if (mDBHelper.checkDBExists())
        {
            // DB Exists then just OPEN
            mAttributesDatabase = mDBHelper.openDataBase();
        }
        else {
            // create the DB:
            mAttributesDatabase = mDBHelper.getWritableDatabase();
        }

        mAttributeTabName = attrType;

        String fileName = mAttributeTabName + ".csv";
        AssetManager manager = context.getAssets();

        // Get Dirs for files:
        File fileDir = context.getFilesDir();

        Integer lineNum = 0;
        Integer numColumns = 0;
        String dbColHeaders[] = null;
        String row[] = null;

        InputStream inStream = null;

        try {
            inStream = manager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tableName = "";

        switch (mAttributeTabName)
        {
            case "SKILLS":
                tableName = SkillTable.TABLE_NAME;
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

        mAttributesDatabase.delete(tableName, null, null);

        mAttributesDatabase.beginTransaction();

        try
        {
            // reader = new BufferedReader(new InputStreamReader(context.getAssets().open(mSkillsFileName)));

            // do reading, usually loop until end of file reading
            String line;

            // while ((line = reader.readLine()) != null)
            while (lineNum <= 50)
            {
                line = reader.readLine();

                if (lineNum == 0)
                {
                    // Get Column Headers:
                    dbColHeaders = line.split(",");

                    numColumns = dbColHeaders.length;

                    lineNum += 1;

                }
                else
                {
                    row = line.split(",");

                    T newAttribute = (T) new Attribute();

                    // Add a new Skill to the SkillList object
                    // this.insertRecord(newSkill);

                    // Create a new db record entry:
                    ContentValues values = new ContentValues(15);

                    values.put(SkillTable.Cols.UUID, newAttribute.getId().toString());

                    for (int j = 0; j < numColumns; j++)
                    {
                        values.put(dbColHeaders[j], row[j].trim());
                    }


                    lineNum += 1;

                    // Insert formatted raw record into the DB:try
                    try {
                        mAttributesDatabase.insertOrThrow(SkillTable.TABLE_NAME, null, values);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            mAttributesDatabase.setTransactionSuccessful();
            mAttributesDatabase.endTransaction();

        }
        catch (IOException e)
        {
            //log the exception
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    //log the exception
                    e.printStackTrace();
                }
            }
        }
        */


    }

    // Use the CursorWrapper to retrieve properly formed database record values
    //public abstract <C> C selectRawRecords(String whereClause, String[] whereArgs);

    // Getter for skills List
    //public abstract List<A> selectFormattedRecords();

    // getter to return a single skill from mSkills
    //public abstract <A extends Attribute> A selectRecord(UUID id);

    // public abstract void insertRecord(A attr);

    //public abstract void removeRecord(A attr);

    //public abstract void updateRecord(A attr);

}

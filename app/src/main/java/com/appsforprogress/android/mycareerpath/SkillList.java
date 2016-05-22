package com.appsforprogress.android.mycareerpath;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;

import com.appsforprogress.android.mycareerpath.database.AttributeDBHelper;
import com.appsforprogress.android.mycareerpath.database.SkillCursorWrapper;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

/**
 * Created by Oswald on 1/10/2016.
 */

public class SkillList extends AttributeList_OLD
{
    // Holds static singleton to list the various skills within
    private static SkillList sSkillList;
    // private List<Skill> mSkills;

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mAttributesDatabase;

    // get the SkillList object in use
    public static SkillList get(Context context)
    {
        if (sSkillList == null)
        {
            sSkillList = new SkillList(context);
        }

        return sSkillList;
    }

    public SkillList()
    {

    }

    // Specific Constructor
    private SkillList(Context context)
    {
        mContext = context.getApplicationContext();

        // Just need a reference to the DB Table
        mAttributesDatabase = AttributeDBHelper.get();

        /*
        mAttributesDatabase = new AttributeDBHelper(mContext).getWritableDatabase();

        String mSkillsFileName = "Skills.csv";
        AssetManager manager = context.getAssets();

        // Get Dirs for files:
        File fileDir = context.getFilesDir();
        String strNewFileName = "Skills.csv";
        String strFileContents = "Write to the Skills file.";

        File newFile = new File(fileDir, strNewFileName);

        try
        {
            newFile.createNewFile();
        }
        catch (IOException e)
        {
            System.out.print("File IO Error encountered.");
        }

        try {
            FileOutputStream fo = new FileOutputStream(newFile.getAbsolutePath());

            try
            {
                fo.write(strFileContents.getBytes());

                fo.close();
            }
            catch (IOException e)
            {
                System.out.print("File IO Error encountered.");
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.print("File Not Found Error encountered.");
        }



        String[] fileList = fileDir.list();

        for (int i = 0; i < fileList.length; i++)
        {
            System.out.print("File :" + i + " Name => " + fileList[i]);
        }

        // To get names of all files inside the "Files" folder
        try
        {
            String[] files = manager.list("");

            for (int i = 0; i < files.length; i++)
            {
                System.out.print("File :" + i + " Name => " + files[i]);
            }
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        InputStream inStream = null;

        // ClassLoader classLoader = getClass().getClassLoader();
        // File file = new File(classLoader.getResource("db-datafiles/Skills.csv").getFile());

        try
        {
            inStream = manager.open(mSkillsFileName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inStream));

        Integer lineNum = 0;
        Integer numColumns = 0;
        String dbColHeaders[] = null;
        String row[] = null;


        String dbCol = "";
        String dbCol0 = "";
        String dbCol1 = "";
        String dbCol2 = "";
        String dbCol3 = "";
        String dbCol4 = "";
        String dbCol5 = "";
        String dbCol6 = "";
        String dbCol7 = "";
        String dbCol8 = "";
        String dbCol9 = "";
        String dbCol10 = "";
        String dbCol11 = "";
        String dbCol12 = "";
        String dbCol13 = "";
        String dbCol14 = "";

        InputStream inStream = null;

        try {
            inStream = manager.open(mSkillsFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

        mAttributesDatabase.delete(SkillTable.TABLE_NAME, null, null);

        mAttributesDatabase.beginTransaction();

        try
        {
            //reader = new BufferedReader(new InputStreamReader(context.getAssets().open(mSkillsFileName)));


            // do reading, usually loop until end of file reading
            String line;

            //while ((line = reader.readLine()) != null)
            while (lineNum <= 50)
            {
                line = reader.readLine();

                if (lineNum == 0)
                {
                    // Get Column Headers:
                    dbColHeaders = line.split(",");

                    numColumns = dbColHeaders.length;

                    lineNum += 1;

                    /*
                    dbCol0 = columns[0].trim();
                    dbCol1 = columns[1].trim();
                    dbCol2 = columns[2].trim();
                    dbCol3 = columns[3].trim();
                    dbCol4 = columns[4].trim();
                    dbCol5 = columns[5].trim();
                    dbCol6 = columns[6].trim();
                    dbCol7 = columns[7].trim();
                    dbCol8 = columns[8].trim();
                    dbCol9 = columns[9].trim();
                    dbCol10 = columns[10].trim();
                    dbCol11 = columns[11].trim();
                    dbCol12 = columns[12].trim();
                    dbCol13 = columns[13].trim();
                    dbCol14 = columns[14].trim();
                }
                else
                {
                    row = line.split(",");

                    Skill newSkill = new Skill();

                    // Add a new Skill to the SkillList object
                    // this.insertRecord(newSkill);

                    // Create a new db record entry:
                    ContentValues values = new ContentValues(15);

                    values.put(SkillTable.Cols.UUID, newSkill.getId().toString());

                    /*
                    if (columns.length != 14)
                    {
                        Log.d("CSVParser" , "Skipping Bad CSV Row");
                        continue;
                    }

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

            List<Skill> skills = this.selectFormattedRecords();

            Integer skillCount = skills.size();

            for (int i = 0; i < skillCount; i++)
            {
                System.out.println(skills.get(i));
            }

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

        try
        {
            while ((line = buffer.readLine()) != null)
            {
                String[] columns = line.split(",");

                ContentValues cv = new ContentValues(14);

                /*
                if (columns.length != 14)
                {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }

                // Header Line: Get column names
                if (lineNum == 0)
                {
                    numColumns = columns.length;

                    dbCol0 = columns[0].trim();
                    dbCol1 = columns[1].trim();
                    dbCol2 = columns[2].trim();
                    dbCol3 = columns[3].trim();
                    dbCol4 = columns[4].trim();
                    dbCol5 = columns[5].trim();
                    dbCol6 = columns[6].trim();
                    dbCol7 = columns[7].trim();
                    dbCol8 = columns[8].trim();
                    dbCol9 = columns[9].trim();
                    dbCol10 = columns[10].trim();
                    dbCol11 = columns[11].trim();
                    dbCol12 = columns[12].trim();
                    dbCol13 = columns[13].trim();
                    dbCol14 = columns[14].trim();
                }

                // Load the data in other rows by column

                cv.put(dbCol0, columns[0].trim());
                cv.put(dbCol1, columns[1].trim());
                cv.put(dbCol2, columns[2].trim());
                cv.put(dbCol3, columns[3].trim());
                cv.put(dbCol4, columns[4].trim());
                cv.put(dbCol0, columns[5].trim());
                cv.put(dbCol1, columns[6].trim());
                cv.put(dbCol2, columns[7].trim());
                cv.put(dbCol3, columns[8].trim());
                cv.put(dbCol4, columns[9].trim());
                cv.put(dbCol0, columns[10].trim());
                cv.put(dbCol1, columns[11].trim());
                cv.put(dbCol2, columns[12].trim());
                cv.put(dbCol3, columns[13].trim());

                mAttributesDatabase.insert(SkillTable.TABLE_NAME, null, cv);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Empty List of Skills
        // mSkills = new ArrayList<>();

        /* Populate the mSkills List with 100 Skill Objects
        for (int i = 0; i < 100; i++)
        {
            Skill skill = new Skill();
            skill.setIndex(i);
            skill.setTitle("Skill #" + i);
            skill.setExperienced(i % 2 == 0);
            mSkills.add(skill);
        }
        */

    }


    // Use the CursorWrapper to retrieve properly formed database record values
    public SkillCursorWrapper selectRawRecords(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mAttributesDatabase.query
        (
            SkillTable.TABLE_NAME,
            null, // All columns
            whereClause,
            whereArgs,
            null,  // group by
            null, // having
            null // limit
        );

        return new SkillCursorWrapper(cursor);
    }

    // Getter for skills List
    public List<Skill> selectFormattedRecords()
    {
        //return new ArrayList<>();
        // return mSkills;

        // Retrieve all skill records from DB:
        List<Skill> skills = new ArrayList<>();

        // Use custom cursor to retrieve rows
        SkillCursorWrapper cursor =
                selectRawRecords(null, null);

        try
        {
            cursor.moveToFirst();

            while (!cursor.isAfterLast())
            {
                // Format the retrieved skill row
                skills.add(cursor.getSkill());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return skills;
    }

    private static ContentValues getContentValues(Skill skill)
    {
        ContentValues values = new ContentValues();

        values.put(SkillTable.Cols.UUID, skill.getId().toString());
        values.put(SkillTable.Cols.CAREER_NAME, skill.getCareerName());
        values.put(SkillTable.Cols.ONET_CODE, skill.getONetCode());
        values.put(SkillTable.Cols.ELEMENT_ID, skill.getElementId());
        values.put(SkillTable.Cols.ELEMENT_NAME, skill.getElementName());
        values.put(SkillTable.Cols.SCALE_ID, skill.getScaleId());
        values.put(SkillTable.Cols.DATA_VALUE, skill.getDataValue());
        values.put(SkillTable.Cols.N_VALUE, skill.getN());
        values.put(SkillTable.Cols.STANDARD_ERROR, skill.getStandardError());
        values.put(SkillTable.Cols.LOWER_CI_BOUND, skill.getLowerCIBound());
        values.put(SkillTable.Cols.UPPER_CI_BOUND, skill.getUpperCIBound());
        values.put(SkillTable.Cols.RECOMMEND_SUPPRESS, skill.getRecommendSuppressStr());
        values.put(SkillTable.Cols.NOT_RELEVANT, skill.getNotRelevantStr());
        values.put(SkillTable.Cols.PROFICIENCY, skill.getProficiency());
        values.put(SkillTable.Cols.PEER_NAME, skill.getPeerName());
        values.put(SkillTable.Cols.DATE_ADDED, skill.getDateAdded());

        return values;
    }


    // getter to return a single skill from mSkills
    public Skill selectRecord(UUID id)
    {
        // Go through the skills listed in the skills List
        /*for (Skill skill : mSkills)
        {
            // If it exists then return it
            if (skill.getId().equals(id))
            {
                return skill;
            }
        }
        */

        // return new ArrayList<>();
        // return mSkills;


        // Use custom cursor to retrieve row by UUID
        SkillCursorWrapper cursor = selectRawRecords(SkillTable.Cols.UUID + " = ?", new String[]{id.toString()});

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();

            // Return the requested Skill row formatted
            return cursor.getSkill();

        }

        finally
        {
            cursor.close();
        }

    }

    public void insertRecord(Skill skill)
    {
        // Skill skill = (Skill) o;
        // mSkills.add(newSkill);

        // Add new ContentValue to the DB:
        ContentValues values = getContentValues(skill);

        // Arg1: Table to insert value into
        // Arg2: fail to insert if the record is null
        // Arg3: hash for skill to add
        mAttributesDatabase.insert(SkillTable.TABLE_NAME, null, values);
    }

    public void removeRecord(Object o)
    {
        // Old: for non DB usage:
        // mSkills.remove(remSkill);

        Skill remSkill = (Skill) o;

        // Get the UUID of the Skill to be removed:
        String uuidString = remSkill.getId().toString();

        // Arg1 (Table): Define the table to be updated
        // Arg2 (whereClause): Define the values to be set in the record to be updated
        // Arg3 (whereArgs): Where clause to define which record is to be updated (based on UUID)
        mAttributesDatabase.delete
                (
                        SkillTable.TABLE_NAME,
                        SkillTable.Cols.UUID + " = ?",
                        new String[] { uuidString }
                );
    }

    public void updateRecord(Object o)
    {
        Skill upSkill = (Skill) o;

        String uuidString = upSkill.getId().toString();

        // Create a hash for the Skill record
        ContentValues values = getContentValues(upSkill);

        // Arg1: Define the table to be updated
        // Arg2: Define the values to be set in the record to be updated
        // Arg3: Where clause to define which record is to be updated (based on UUID)
        mAttributesDatabase.update
        (
            SkillTable.TABLE_NAME,
            values,
            SkillTable.Cols.UUID + " = ?",
            new String[] { uuidString }
        );
    }

    /*
    private Cursor querySkills(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query
        (
            SkillTable.TABLE_NAME,
            null, // null: All Columns
            whereClause,
            whereArgs,
            null, // groupBy
            null, // having
            null // OrderBy
        );

        return cursor;
    }
    */
}

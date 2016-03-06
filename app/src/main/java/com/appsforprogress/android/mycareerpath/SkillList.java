package com.appsforprogress.android.mycareerpath;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.appsforprogress.android.mycareerpath.database.SkillDBHelper;
import com.appsforprogress.android.mycareerpath.database.SkillCursorWrapper;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

/**
 * Created by Oswald on 1/10/2016.
 */

public class SkillList
{
    // Holds static singleton to list the various skills within
    private static SkillList sSkillList;
    // private List<Skill> mSkills;

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mSkillsDatabase;


    // Constructor
    private SkillList(Context context)
    {
        // Setup and open a Writable DB:
        mContext = context.getApplicationContext();
        mSkillsDatabase = new SkillDBHelper(mContext).getWritableDatabase();

        String mSkillsFileName = "Skills.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try
        {
            inStream = manager.open(mSkillsFileName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));

        Integer lineNum = 0;

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

        String line;

        mSkillsDatabase.beginTransaction();

        try
        {
            while ((line = buffer.readLine()) != null)
            {
                String[] columns = line.split(",");

                ContentValues cv = new ContentValues(14);

                if (columns.length != 14)
                {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                // Header Line:
                if (lineNum == 0)
                {
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


                mSkillsDatabase.insert(SkillTable.TABLE_NAME, null, cv);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        mSkillsDatabase.setTransactionSuccessful();
        mSkillsDatabase.endTransaction();

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


    // get the SkillList object in use
    public static SkillList get(Context context)
    {
        if (sSkillList == null)
        {
            sSkillList = new SkillList(context);
        }

        return sSkillList;
    }


    // Use the CursorWrapper to retrieve properly formed database record values
    private SkillCursorWrapper querySkills(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mSkillsDatabase.query
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
    public List<Skill> getSkills()
    {
        //return new ArrayList<>();
        // return mSkills;

        // Retrieve all skill records from DB:
        List<Skill> skills = new ArrayList<>();

        // Use custom cursor to retrieve rows
        SkillCursorWrapper cursor = querySkills(null, null);

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


    // getter to return a single skill from mSkills
    public Skill getSkill(UUID id)
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
        SkillCursorWrapper cursor = querySkills(SkillTable.Cols.UUID + " = ?", new String[] { id.toString() });

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

    // WRITE to the columns in our DB:
    private static ContentValues getContentValues(Skill skill)
    {
        ContentValues values = new ContentValues();

        values.put(SkillTable.Cols.UUID, skill.getId().toString());
        values.put(SkillTable.Cols.TITLE, skill.getTitle());
        values.put(SkillTable.Cols.DATE, skill.getAddedDate().getTime());
        values.put(SkillTable.Cols.EXPERIENCE, skill.isExperienced() ? 1 : 0);
        values.put(SkillTable.Cols.PEER_NAME, skill.getPeer());

        return values;
    }

    public void addSkill(Skill newSkill)
    {
        // mSkills.add(newSkill);

        // Add new ContentValue to the DB:
        ContentValues values = getContentValues(newSkill);

        // Arg1: Table to insert value into
        // Arg2: fail to insert if the record is null
        // Arg3: hash for skill to add
        mSkillsDatabase.insert(SkillTable.TABLE_NAME, null, values);
    }

    // Delete a skill from the database:
    public void removeSkill(Skill remSkill)
    {
        // Old: for non DB usage:
        // mSkills.remove(remSkill);

        // Get the UUID of the Skill to be removed:
        String uuidString = remSkill.getId().toString();

        // Arg1 (Table): Define the table to be updated
        // Arg2 (whereClause): Define the values to be set in the record to be updated
        // Arg3 (whereArgs): Where clause to define which record is to be updated (based on UUID)
        mSkillsDatabase.delete
        (
            SkillTable.TABLE_NAME,
            SkillTable.Cols.UUID + " = ?",
            new String[] { uuidString }
        );
    }

    public void updateSkill(Skill upSkill)
    {
        String uuidString = upSkill.getId().toString();

        // Create a hash for the Skill record
        ContentValues values = getContentValues(upSkill);

        // Arg1: Define the table to be updated
        // Arg2: Define the values to be set in the record to be updated
        // Arg3: Where clause to define which record is to be updated (based on UUID)
        mSkillsDatabase.update
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

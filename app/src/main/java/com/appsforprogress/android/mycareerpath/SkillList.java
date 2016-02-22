package com.appsforprogress.android.mycareerpath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.appsforprogress.android.mycareerpath.database.SkillBaseHelper;
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
    private SQLiteDatabase mDatabase;


    // Constructor
    private SkillList(Context context)
    {
        // Setup and open a Writable DB:
        mContext = context.getApplicationContext();
        mDatabase = new SkillBaseHelper(mContext).getWritableDatabase();

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
        Cursor cursor = mDatabase.query
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

        //return new ArrayList<>();
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

        } finally {
            cursor.close();
        }

    }

    // Set up hash for our database records:
    private static ContentValues getContentValues(Skill skill)
    {
        ContentValues values = new ContentValues();

        values.put(SkillTable.Cols.UUID, skill.getId().toString());
        values.put(SkillTable.Cols.TITLE, skill.getTitle());
        values.put(SkillTable.Cols.DATE, skill.getAddedDate().getTime());
        values.put(SkillTable.Cols.EXPERIENCE, skill.isExperienced() ? 1 : 0);

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
        mDatabase.insert(SkillTable.TABLE_NAME, null, values);
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
        mDatabase.delete
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
        mDatabase.update
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

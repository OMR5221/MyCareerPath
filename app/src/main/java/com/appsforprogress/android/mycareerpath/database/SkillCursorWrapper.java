package com.appsforprogress.android.mycareerpath.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.appsforprogress.android.mycareerpath.Skill;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Oswald on 2/20/2016.
 */

// Wrap custom methods around a Cursor to alter Database query records
// to cast the data palled out of the Database into Java compatible data types
public class SkillCursorWrapper extends CursorWrapper
{

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public SkillCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Skill getSkill()
    {
        // Retrieve Database record and cast to Java data types
        String uuidString = getString(getColumnIndex(SkillTable.Cols.UUID));
        String title = getString(getColumnIndex(SkillTable.Cols.TITLE));
        long date = getLong(getColumnIndex(SkillTable.Cols.DATE));
        int hasExperience = getInt(getColumnIndex(SkillTable.Cols.EXPERIENCE));

        // Create a new Skill and assign values retrieved to the new Skill
        Skill skill = new Skill(UUID.fromString(uuidString));
        skill.setTitle(title);
        skill.setAddedDate(new Date(date));
        skill.setExperienced(hasExperience != 0);

        return skill;
    }
}

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
public class SkillCursorWrapper extends AttributeCursorWrapper
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

    // READ values from columns in our DB:
    public Skill getSkillRecord()
    {
        super.getAttributeRecord();

        // Retrieve Database record and cast to Java data types
        int n = getInt(getColumnIndex(SkillTable.Cols.N_VALUE));
        Float stdError = getFloat(getColumnIndex(SkillTable.Cols.STANDARD_ERROR));
        Float lowCIBound = getFloat(getColumnIndex(SkillTable.Cols.LOWER_CI_BOUND));
        Float upCIBound = getFloat(getColumnIndex(SkillTable.Cols.UPPER_CI_BOUND));
        String recSuppress = getString(getColumnIndex(SkillTable.Cols.RECOMMEND_SUPPRESS));
        String notRelevant = getString(getColumnIndex(SkillTable.Cols.NOT_RELEVANT));
        // long date = getLong(getColumnIndex(SkillTable.Cols.DATE));
        String peerName = getString(getColumnIndex(SkillTable.Cols.PEER_NAME));
        Integer proficiency = getInt(getColumnIndex(SkillTable.Cols.PROFICIENCY));

        // Create a new Skill and assign values retrieved from the Database for display in SkillFragment
        Skill skill = new Skill();
        skill.setN(n);
        skill.setStandardError(stdError);
        skill.setLowerCIBound(lowCIBound);
        skill.setUpperCIBound(upCIBound);
        skill.setRecommendSuppressStr(recSuppress);
        skill.setNotRelevantStr(notRelevant);
        skill.setPeerName(peerName);
        skill.setProficiency(proficiency);
        // skill.setAddedDate(new Date(date));
        // skill.setExperienced(hasExperience != 0);

        return skill;
    }
}

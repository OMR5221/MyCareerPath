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

    // READ values from columns in our DB:
    public Skill getSkill()
    {
        // super.getAttributeRecord();

        // Retrieve Database record and cast to Java data types
        // Retrieve Database record and cast to Java data types
        String uuidString = getString(getColumnIndex(SkillTable.Cols.UUID));
        String oNetCode = getString(getColumnIndex(SkillTable.Cols.ONET_CODE));
        String careerName = getString(getColumnIndex(SkillTable.Cols.CAREER_NAME));
        String elementId = getString(getColumnIndex(SkillTable.Cols.ELEMENT_ID));
        String elementName = getString(getColumnIndex(SkillTable.Cols.ELEMENT_NAME));
        String scaleId = getString(getColumnIndex(SkillTable.Cols.SCALE_ID));
        String scaleName = getString(getColumnIndex(SkillTable.Cols.SCALE_NAME));
        String dataValue = getString(getColumnIndex(SkillTable.Cols.DATA_VALUE));
        String dateAdded = getString(getColumnIndex(SkillTable.Cols.DATE_ADDED));
        String n = getString(getColumnIndex(SkillTable.Cols.N_VALUE));
        String stdError = getString(getColumnIndex(SkillTable.Cols.STANDARD_ERROR));
        String lowCIBound = getString(getColumnIndex(SkillTable.Cols.LOWER_CI_BOUND));
        String upCIBound = getString(getColumnIndex(SkillTable.Cols.UPPER_CI_BOUND));
        String recSuppress = getString(getColumnIndex(SkillTable.Cols.RECOMMEND_SUPPRESS));
        String notRelevant = getString(getColumnIndex(SkillTable.Cols.NOT_RELEVANT));
        String peerName = getString(getColumnIndex(SkillTable.Cols.PEER_NAME));
        Integer proficiency = getInt(getColumnIndex(SkillTable.Cols.PROFICIENCY));

        // Create a new Skill and assign values retrieved from the Database for display in SkillFragment
        Skill skill = new Skill(UUID.fromString(uuidString));

        skill.setONetCode(oNetCode);
        skill.setCareerName(careerName);
        skill.setElementId(elementId);
        skill.setElementName(elementName);
        skill.setScaleId(scaleId);
        skill.setScaleName(scaleName);
        skill.setDataValue(dataValue);
        skill.setDateAdded(dateAdded);
        skill.setN(n);
        skill.setStandardError(stdError);
        skill.setLowerCIBound(lowCIBound);
        skill.setUpperCIBound(upCIBound);
        skill.setRecommendSuppressStr(recSuppress);
        skill.setNotRelevantStr(notRelevant);
        skill.setPeerName(peerName);
        skill.setProficiency(proficiency);
        // skill.setDateAdded(new Date(dateAdded));
        // skill.setExperienced(hasExperience != 0);

        return skill;
    }
}

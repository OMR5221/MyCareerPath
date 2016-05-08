package com.appsforprogress.android.mycareerpath;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.UUID;

/**
 * Created by Oswald on 1/10/2016.
 */

public abstract class AttributeList_OLD<A>
{
    // Holds static singleton to list the various skills within
    private static AttributeList_OLD sAttributeList;

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mAttributesDatabase;

    // get the SkillList object in use
    //public abstract <T> T get(Context context);

    // Use the CursorWrapper to retrieve properly formed database record values
    public abstract <C> C selectRawRecords(String whereClause, String[] whereArgs);

    // Getter for skills List
    public abstract List<A> selectFormattedRecords();

    // getter to return a single skill from mSkills
    public abstract <A extends Attribute> A selectRecord(UUID id);

    // public abstract void insertRecord(A attr);

    public abstract void removeRecord(A attr);

    public abstract void updateRecord(A attr);

}

package com.appsforprogress.android.mycareerpath;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appsforprogress.android.mycareerpath.database.AttributeCursorWrapper;
import com.appsforprogress.android.mycareerpath.database.SkillCursorWrapper;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Oswald on 1/10/2016.
 */

public abstract class AttributeList<A>
{
    // Holds static singleton to list the various skills within
    private static AttributeList sAttributeList;

    // For Database Usage:
    private Context mContext;
    private SQLiteDatabase mAttributesDatabase;

    // get the SkillList object in use
    //public abstract <T> T get(Context context);

    // Use the CursorWrapper to retrieve properly formed database record values
    public abstract <C extends AttributeCursorWrapper> C selectRawRecords(String whereClause, String[] whereArgs);

    // Getter for skills List
    public abstract List<A> selectFormattedRecords();

    // getter to return a single skill from mSkills
    public abstract <A extends Attribute> A selectRecord(UUID id);

    public abstract ContentValues getContentValues(A attr);

    public abstract void insertRecord(A attr);

    public abstract void removeRecord(A attr);

    public abstract void updateRecord(A attr);

}

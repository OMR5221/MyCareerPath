package com.appsforprogress.android.mycareerpath.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

/**
 * Created by Oswald on 2/19/2016.
 */
public class SkillBaseHelper extends SQLiteOpenHelper
{

    private static final Integer VERSION = 1;
    private static final String DATABASE_NAME = "skillBase.db";


    public SkillBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Called to create DB and Tables if does not already exist:
        db.execSQL
        (
            "create table " + SkillTable.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                SkillTable.Cols.UUID + ", " +
                SkillTable.Cols.TITLE + ", " +
                SkillTable.Cols.DATE + ", " +
                SkillTable.Cols.EXPERIENCE + ", " +
                SkillTable.Cols.PEER_NAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

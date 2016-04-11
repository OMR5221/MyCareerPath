package com.appsforprogress.android.mycareerpath.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.appsforprogress.android.mycareerpath.database.SkillDBSchema.SkillTable;

/**
 * Created by Oswald on 2/19/2016.
 */
public class SkillDBHelper extends SQLiteOpenHelper
{

    private static final Integer VERSION = 1;
    private static final String DATABASE_NAME = "skill.db";


    public SkillDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String columns =
                "O*NET-SOC Code,Element ID,Element Name,Scale ID," +
                "Scale Name,Data Value,N,Standard Error,Lower CI Bound,Upper CI Bound," +
                "Recommend Suppress,Not Relevant";

        // Called to create DB and Tables if does not already exist:
        db.execSQL
        (
            "create table " + SkillTable.TABLE_NAME +
            "(" +
                " _id integer primary key autoincrement, " + columns +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

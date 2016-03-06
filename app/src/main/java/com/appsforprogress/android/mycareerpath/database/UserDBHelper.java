package com.appsforprogress.android.mycareerpath.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appsforprogress.android.mycareerpath.database.UserDBSchema.UserTable;

/**
 * Created by Oswald on 2/19/2016.
 */
public class UserDBHelper extends SQLiteOpenHelper
{

    private static final Integer VERSION = 1;
    private static final String DATABASE_NAME = "users.db";


    public UserDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Called to create DB and Tables if does not already exist:
        db.execSQL
        (
            "create table " + UserTable.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.FIRST_NAME + ", " +
                UserTable.Cols.LAST_NAME + ", " +
                UserTable.Cols.BIRTH_DATE + ", " +
                UserTable.Cols.SIGNUP_DATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

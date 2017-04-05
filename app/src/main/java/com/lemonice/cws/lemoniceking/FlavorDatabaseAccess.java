package com.lemonice.cws.lemoniceking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cmichalowski on 3/14/17.
 */

public class FlavorDatabaseAccess extends SQLiteOpenHelper {

    public FlavorDatabaseAccess(Context context) {
        super(context, FlavorDatabaseOpenHelper.DB_NAME, null, FlavorDatabaseOpenHelper.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + FlavorDatabaseOpenHelper.FlavorEntry.TABLE + " ( " +
                FlavorDatabaseOpenHelper.FlavorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FlavorDatabaseOpenHelper.FlavorEntry.COL_FLAVOR_TITLE + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FlavorDatabaseOpenHelper.FlavorEntry.TABLE);
        onCreate(db);
    }
}

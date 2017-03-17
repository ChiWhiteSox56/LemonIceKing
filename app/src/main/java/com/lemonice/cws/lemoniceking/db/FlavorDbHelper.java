package com.lemonice.cws.lemoniceking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cmichalowski on 3/14/17.
 */

public class FlavorDbHelper extends SQLiteOpenHelper {

    public FlavorDbHelper(Context context) {
        super(context, FlavorContract.DB_NAME, null, FlavorContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + FlavorContract.FlavorEntry.TABLE + " ( " +
                FlavorContract.FlavorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FlavorContract.FlavorEntry.COL_FLAVOR_TITLE + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FlavorContract.FlavorEntry.TABLE);
        onCreate(db);
    }
}

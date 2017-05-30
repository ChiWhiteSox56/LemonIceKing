package com.lemonice.cws.lemoniceking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by cmichalowski on 3/14/17.
 */

// The FlavorContract class defines constants which are used to access the data in the database
// The helper class FlavorDbOpenHelper opens the database

public class FlavorDbOpenHelper extends SQLiteAssetHelper {

    public FlavorDbOpenHelper(Context context) {
        super(context, FlavorContract.DB_NAME, null, FlavorContract.DB_VERSION);
    }

}

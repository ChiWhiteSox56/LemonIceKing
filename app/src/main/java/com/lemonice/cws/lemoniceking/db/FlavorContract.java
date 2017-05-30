package com.lemonice.cws.lemoniceking.db;

import android.provider.BaseColumns;

/**
 * Created by cmichalowski on 3/14/17.
 */

// The FlavorContract class defines constants which are used to access the data in the database
// The helper class FlavorDbOpenHelper opens the database

public class FlavorContract {

    public static final String DB_NAME = "allFlavors.db";
    public static final int DB_VERSION = 1;

    public class FlavorEntry implements BaseColumns {

        public static final String TABLE = "LEMONICEKING";
        public static final String COL_FLAVOR_TITLE = "FLAVOR";

    }
}

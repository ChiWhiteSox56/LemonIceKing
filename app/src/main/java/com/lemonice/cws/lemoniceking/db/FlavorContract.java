package com.lemonice.cws.lemoniceking.db;

import android.provider.BaseColumns;

/**
 * Created by cmichalowski on 3/14/17.
 */

public class FlavorContract {

    public static final String DB_NAME = "com.lemonice.cws.lemoniceking.db";
    public static final int DB_VERSION = 1;

    public class FlavorEntry implements BaseColumns {
        public static final String TABLE = "flavors";

        public static final String COL_FLAVOR_TITLE = "title";

    }
}

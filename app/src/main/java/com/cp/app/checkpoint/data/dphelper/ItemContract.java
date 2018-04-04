package com.cp.app.checkpoint.data.dphelper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ahmed Yehya on 21/12/2017.
 */

public class ItemContract {

    public static final String CONTENT_AUTHORITY = "com.cp.checkpoint.desiredlistorderdp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ITEMS = "items";


    public static final class ItemEntry implements BaseColumns{
        public final static String TABLE_ITEMS = "items";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ITEM_ID = "item_id";
        public final static String COLUMN_ITEM_NAME = "name";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_TOTAL_PRICE = "price";
        public final static String COLUMN_ITEM_NOTES = "notes";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ITEMS);



    }

}

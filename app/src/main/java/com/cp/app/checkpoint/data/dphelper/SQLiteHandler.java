package com.cp.app.checkpoint.data.dphelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import com.cp.app.checkpoint.data.models.ListOfOneOrderModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 21/12/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    Context context;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "list_of_order";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE "+ ItemContract.ItemEntry.TABLE_ITEMS + "(" +
                ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ItemContract.ItemEntry.COLUMN_ITEM_ID + " TEXT NOT NULL, "+
                ItemContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT, "+
                ItemContract.ItemEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "+
                ItemContract.ItemEntry.COLUMN_TOTAL_PRICE + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE_ITEMS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addItemToOrderList(String itemId, String itemName, String desiredQuantity, String totalPrice) {
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_ID,itemId);
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME,itemName);
        values.put(ItemContract.ItemEntry.COLUMN_QUANTITY,desiredQuantity);
        values.put(ItemContract.ItemEntry.COLUMN_TOTAL_PRICE,totalPrice);

        Uri newUri;
        newUri = context.getContentResolver().insert(ItemContract.ItemEntry.CONTENT_URI,values);

        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(context, "failed to insert",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(context,"insert is done",
                    Toast.LENGTH_SHORT).show();
        }


    }

    public ArrayList<ListOfOneOrderModel> getOrderItemList()
    {
        ArrayList<ListOfOneOrderModel> orderList = new ArrayList<>();
        String[] projection = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_ITEM_ID,
                ItemContract.ItemEntry.COLUMN_ITEM_NAME,
                ItemContract.ItemEntry.COLUMN_QUANTITY,
                ItemContract.ItemEntry.COLUMN_TOTAL_PRICE,
        };

        Cursor cursor = context.getContentResolver().query(ItemContract.ItemEntry.CONTENT_URI, projection,null,null,null);

        if (cursor.moveToFirst()){
            do{
                String dbId = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry._ID));
                String itemId = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_ID));
                String itemName = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME));
                Integer quantity = cursor .getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_QUANTITY));
                Integer totalPrice = cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_TOTAL_PRICE));
                orderList.add(new ListOfOneOrderModel(dbId,itemId,itemName,quantity,totalPrice));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();
        return orderList;
    }

    public void deleteItemFromOrderList(Uri uri)
    {
        int checkEffect = context.getContentResolver().delete(uri,null,null);
        if (checkEffect > 0) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(context, "delete is done",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(context,"delete is failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

}

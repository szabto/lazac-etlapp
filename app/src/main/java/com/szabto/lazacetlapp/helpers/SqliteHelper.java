package com.szabto.lazacetlapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 3/24/17.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "szori_db";
    public static final String VIEWED_TABLE_NAME = "viewed_menus";
    public static final String PERSON_COLUMN_ID = "_id";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void viewMenu( String id ) {
        if( !this.isViewedMenu( id ) ) {
            ContentValues values = new ContentValues();
            values.put(SqliteHelper.PERSON_COLUMN_ID, String.valueOf(id));
            this.getWritableDatabase().insert(SqliteHelper.VIEWED_TABLE_NAME, null, values);
        }
    }

    public boolean isViewedMenu( String id ) {
        Cursor c = this.getReadableDatabase().query(SqliteHelper.VIEWED_TABLE_NAME, new String[] {SqliteHelper.PERSON_COLUMN_ID}, SqliteHelper.PERSON_COLUMN_ID +" = ?", new String[] {
                id
        },null,null,null);

        return c.getCount() > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + VIEWED_TABLE_NAME + " (" +
                PERSON_COLUMN_ID + " INT UNSIGNED)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VIEWED_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
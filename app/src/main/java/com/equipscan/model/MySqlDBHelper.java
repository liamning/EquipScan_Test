package com.equipscan.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlDBHelper extends SQLiteOpenHelper {

    private static final String TAG = MySqlDBHelper.class.getSimpleName();
    public static final String DB_NAME = "test.db";
    public static final int DB_VERS = 1;
    public static final String TABLE = "test_table";
    public static final boolean Debug = false;

    public MySqlDBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERS);
       // this.context = context;
    }

    public Cursor query(SQLiteDatabase db, String query) {
        Cursor cursor = db.rawQuery(query, null);
        if (Debug) {
            Log.d(TAG, "Executing Query: "+ query);
        }
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
		/* Create table Logic, once the Application has ran for the first time. */
        String sql = String.format("CREATE TABLE %s (aid INTEGER PRIMARY KEY AUTOINCREMENT, test)", TABLE);
        db.execSQL(sql);
        if (Debug) {
            Log.d(TAG, "onCreate Called.");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE));
        if (Debug) {
            Log.d(TAG, "Upgrade: Dropping Table and Calling onCreate");
        }
        this.onCreate(db);

    }
}

package com.effone.viewpageholder.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class SqliteConnection extends SQLiteOpenHelper {
    public SqliteConnection(Context context) {
        super(context, DBConstant.DB_NAME, null,DBConstant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstant.CREATE_ORDERITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
}

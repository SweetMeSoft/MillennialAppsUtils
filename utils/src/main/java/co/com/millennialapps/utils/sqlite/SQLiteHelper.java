package co.com.millennialapps.utils.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DBMM.db";
    private static final int DB_VERSION = 1;

    SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLiteConstants.CREATE_TABLE_MALLS);
        db.execSQL(SQLiteConstants.CREATE_TABLE_NODES);
        db.execSQL(SQLiteConstants.CREATE_TABLE_FLOORS);
        db.execSQL(SQLiteConstants.CREATE_TABLE_NODES_NODES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL(SQLiteConstants.DROP_TABLE_MALLS);
        db.execSQL(SQLiteConstants.DROP_TABLE_NODES);
        db.execSQL(SQLiteConstants.DROP_TABLE_FLOORS);
        db.execSQL(SQLiteConstants.DROP_TABLE_NODES_NODES);

        db.execSQL(SQLiteConstants.CREATE_TABLE_MALLS);
        db.execSQL(SQLiteConstants.CREATE_TABLE_NODES);
        db.execSQL(SQLiteConstants.CREATE_TABLE_FLOORS);
        db.execSQL(SQLiteConstants.CREATE_TABLE_NODES_NODES);*/
    }
}

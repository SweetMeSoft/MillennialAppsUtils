package co.com.millennialapps.utils.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

class SQLiteHelper extends SQLiteOpenHelper {

    private List<String> sentencesCreateTables = new LinkedList<>();

    public SQLiteHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    public SQLiteHelper(Context context, String dbName, int dbVersion, List<String> sentencesCreateTables) {
        super(context, dbName, null, dbVersion);
        this.sentencesCreateTables = sentencesCreateTables;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sentence : sentencesCreateTables) {
            db.execSQL(sentence);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

package co.com.millennialapps.utils.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.crash.FirebaseCrash;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SQLiteManager<T> {

    private static SQLiteManager manager;
    private final SQLiteHelper helper;
    private final SQLiteDatabase db;

    public static SQLiteManager getInstance(Context context, String dbName, int dbVersion) {
        return manager == null ? manager = new SQLiteManager(context, dbName, dbVersion) : manager;
    }

    public static SQLiteManager getInstance(Context context, String dbName, int dbVersion, List<String> sentencesCreateTables) {
        return manager == null ? manager = new SQLiteManager(context, dbName, dbVersion, sentencesCreateTables) : manager;
    }

    private SQLiteManager(Context context, String dbName, int dbVersion) {
        helper = new SQLiteHelper(context, dbName, dbVersion);
        db = helper.getWritableDatabase();
    }

    private SQLiteManager(Context context, String dbName, int dbVersion, List<String> sentencesCreateTables) {
        helper = new SQLiteHelper(context, dbName, dbVersion, sentencesCreateTables);
        db = helper.getWritableDatabase();
    }

    public boolean isTableExists(T obj) {
        String select = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = '" + obj.getClass().getSimpleName() + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        }
        return false;
    }

    public List<T> select(Class<T> clazz, String where, String groupBy, String orderBy) {
        ArrayList<T> list = new ArrayList<>();
        String[] columns = SQLiteTools.getColumns(clazz);
        try {
            Cursor cursor = db.query(clazz.getSimpleName(), columns, where, null, groupBy, null, orderBy);
            T obj = clazz.newInstance();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    for (String column : columns) {
                        Field field = clazz.getDeclaredField(column);
                        field.setAccessible(true);
                        field.set(obj, cursor.getString(cursor.getColumnIndex(column)));
                    }
                    list.add(obj);

                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (java.lang.InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return list;
    }

    public long insert(T obj) {
        try {
            return db.insertOrThrow(obj.getClass().getSimpleName(), null, SQLiteTools.getContentValues(obj));
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
        return 0;
    }

    public boolean existOn(T obj, String where) {
        return db.query(obj.getClass().getSimpleName(), SQLiteTools.getColumns(obj.getClass()), where,
                null, null, null, null).getCount() > 0;
    }

    public void update(T obj, String where) {
        try {
            db.update(obj.getClass().getSimpleName(), SQLiteTools.getContentValues(obj), where, null);
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
    }

    public int delete(String table, String where) {
        return db.delete(table, where, null);
    }

    public SQLiteHelper getHelper() {
        return helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}

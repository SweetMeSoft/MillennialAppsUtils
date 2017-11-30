package millennialapps.com.co.utils.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.crash.FirebaseCrash;

public class SQLiteManager {

    private static SQLiteManager manager;
    private final SQLiteHelper helper;
    private final SQLiteDatabase db;

    public static SQLiteManager getInstance(Context context) {
        return manager == null ? manager = new SQLiteManager(context) : manager;
    }

    private SQLiteManager(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public boolean isTableExists(String tableName) {
        String select = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = '" + tableName + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        }
        return false;
    }

    public Cursor select(String table, String[] columns, String where, String groupBy, String orderBy) {
        return db.query(table, columns, where, null, groupBy, null, orderBy);
    }

    /*public <T> List<T> select(String table, String[] columns, String where, String groupBy, String orderBy, Class<T> clazz) {
        LinkedList<T> list = new LinkedList<>();
        Cursor cursor = db.query(table, columns, where, null, groupBy, null, orderBy);
        try {
            T obj = clazz.newInstance();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(obj, cursor.getString());
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }*/

    public long insert(String table, ContentValues values) {
        try {
            return db.insertOrThrow(table, null, values);
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
        return 0;
    }

    public boolean existOn(String table, String[] columns, String where) {
        return db.query(table, columns, where, null, null, null, null).getCount() > 0;
    }

    public void update(String table, ContentValues values, String where) {
        try {
            db.update(table, values, where, null);
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
    }

    public SQLiteHelper getHelper() {
        return helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public int delete(String table, String where) {
        return db.delete(table, where, null);
    }
}

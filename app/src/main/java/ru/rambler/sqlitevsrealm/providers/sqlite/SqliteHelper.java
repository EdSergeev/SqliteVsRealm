package ru.rambler.sqlitevsrealm.providers.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String TAG = SqliteHelper.class.getSimpleName();
    private static final String DB_NAME = "sqlitedb.db";
    private static final int DB_VERSION = 3;


    public interface Tables {
        String Students = "Students";
        String Groups = "Groups";
    }

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try {
            db.execSQL("CREATE TABLE " + Tables.Groups + " (group_id INTEGER PRIMARY KEY, title TEXT NOT NULL)");
            db.execSQL("CREATE TABLE " + Tables.Students + " (id INTEGER PRIMARY KEY, name TEXT NOT NULL, average_score INT, group_id INT NOT NULL)");

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.e(TAG, "cannot create database tables", e);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.Students);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.Groups);

        onCreate(db);
    }
}

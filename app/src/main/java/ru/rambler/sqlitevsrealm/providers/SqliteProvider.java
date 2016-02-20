package ru.rambler.sqlitevsrealm.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;
import ru.rambler.sqlitevsrealm.providers.sqlite.SqliteHelper;

public class SqliteProvider implements DbProvider {

    private SqliteHelper helper;
    private SQLiteDatabase db;

    @Override
    public String getName() {
        return "Sqlite";
    }

    @Override
    public void init(Context context) {
        helper = new SqliteHelper(context);
    }

    @Override
    public void open() {
        db = helper.getWritableDatabase();
    }

    @Override
    public void close() {
        db = null;
        if (helper != null) {
            helper.close();
        }
    }

    @Override
    public void clean() {
        begin();
        db.execSQL("DELETE FROM " + SqliteHelper.Tables.Students);
        db.execSQL("DELETE FROM " + SqliteHelper.Tables.Groups);
        commit();
    }

    @Override
    public void begin() {
        db.beginTransaction();
    }

    @Override
    public void commit() {
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public long insert(Student student) {
        ContentValues value = new ContentValues(3);
        value.put("name", student.getName());
        value.put("average_score", student.getAverageScore());
        value.put("group_id", student.getGroupId());
        return db.insert(SqliteHelper.Tables.Students, null, value);
    }

    @Override
    public long insert(Group group) {
        ContentValues value = new ContentValues(1);
        value.put("title", group.getTitle());
        return db.insert(SqliteHelper.Tables.Groups, null, value);
    }

    @Override
    public long selectStudentsByGroupId(long groupId) {
        Cursor c = db.rawQuery("SELECT * FROM " + SqliteHelper.Tables.Students + " WHERE group_id = " + groupId, null);
        while (c.moveToNext()) {
            c.getLong(0);
        }
        int result = c.getCount();
        c.close();
        return result;
    }

    @Override
    public long deleteGroup(long groupId) {
        db.execSQL("DELETE FROM " + SqliteHelper.Tables.Students + " WHERE group_id = " + groupId);
        db.execSQL("DELETE FROM " + SqliteHelper.Tables.Groups + " WHERE group_id = " + groupId);
        return 0;
    }
}

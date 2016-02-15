package ru.rambler.sqlitevsrealm.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;
import ru.rambler.sqlitevsrealm.providers.sqlite.SqliteHelper;

public class SqliteProvider implements DbProvider {

    private SqliteHelper helper;
    private SQLiteDatabase db;

    @Override
    public void open(Context context) {
        helper = new SqliteHelper(context);
        db = helper.getWritableDatabase();
    }

    @Override
    public void close() {
        db = null;
        helper.close();
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
}

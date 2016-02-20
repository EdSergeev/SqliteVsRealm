package ru.rambler.sqlitevsrealm.providers;

import android.content.Context;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;

public interface DbProvider {
    String getName();

    void init(Context context);

    void open();

    void close();

    void clean();

    void begin();

    void commit();

    long insert(Student student);

    long insert(Group group);

    long selectStudentsByGroupId(long groupId);

    long selectStudentsByGroupIdAndSort(long groupId);

    long selectStudentsBetween(int fromScore, int toScore);

    long deleteGroup(long groupId);
}

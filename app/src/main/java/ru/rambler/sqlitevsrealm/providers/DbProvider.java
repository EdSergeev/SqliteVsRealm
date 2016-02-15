package ru.rambler.sqlitevsrealm.providers;

import android.content.Context;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;

public interface DbProvider {
    void open(Context context);

    void close();

    void begin();

    void commit();

    long insert(Student student);

    long insert(Group group);
}

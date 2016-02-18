package ru.rambler.sqlitevsrealm.providers;

import android.content.Context;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;

public interface DbProvider {
    String getName();

    void open(Context context);

    void close();

    void clean();

    void begin();

    void commit();

    long insert(Student student);

    long insert(Group group);
}

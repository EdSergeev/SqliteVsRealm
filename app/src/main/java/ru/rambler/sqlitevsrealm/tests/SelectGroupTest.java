package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class SelectGroupTest implements BaseTest {

    @Override
    public void run(DbProvider provider) {
        long count = provider.selectStudentsByGroupId(Config.SELECT_STUDENTS_BY_GROUP_ID);
        assert count == Config.STUDENTS_PER_GROUP;
    }
}

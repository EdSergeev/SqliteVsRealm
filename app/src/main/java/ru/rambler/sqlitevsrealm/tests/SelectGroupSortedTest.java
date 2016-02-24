package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class SelectGroupSortedTest implements BaseTest {
    @Override
    public void run(DbProvider provider) {
        provider.selectStudentsByGroupIdAndSort(Config.SELECT_STUDENTS_BY_GROUP_ID);
    }
}

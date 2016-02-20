package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class SelectGroupSortedTest extends BaseTest {
    public SelectGroupSortedTest(DbProvider provider) {
        super(provider);
    }

    @Override
    protected void work(DbProvider provider) {
        provider.begin();
        provider.selectStudentsByGroupIdAndSort(Config.SELECT_STUDENTS_BY_GROUP_ID);
        provider.commit();
    }
}

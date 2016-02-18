package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class SelectByGroupIdTest extends BaseTest {

    public SelectByGroupIdTest(DbProvider provider) {
        super(provider);
    }

    @Override
    protected void work(DbProvider provider) {
        long count = provider.selectStudentsByGroupId(50L);
        assert count == 500;
    }

    @Override
    public String getTestName() {
        return "SelectByGroupId";
    }
}

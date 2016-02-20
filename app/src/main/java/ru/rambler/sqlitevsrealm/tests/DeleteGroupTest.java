package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class DeleteGroupTest extends BaseTest {
    public DeleteGroupTest(DbProvider provider) {
        super(provider);
    }

    @Override
    protected void work(DbProvider provider) {
        provider.begin();
        provider.deleteGroup(Config.DELETE_GROUP);
        provider.commit();
    }
}

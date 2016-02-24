package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class DeleteGroupTest implements BaseTest {

    @Override
    public void run(DbProvider provider) {
        provider.deleteGroup(Config.DELETE_GROUP);
    }
}

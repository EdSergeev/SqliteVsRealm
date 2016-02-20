package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class SelectBetweenTest extends BaseTest {
    public SelectBetweenTest(DbProvider provider) {
        super(provider);
    }

    @Override
    protected void work(DbProvider provider) {
        provider.begin();
        provider.selectStudentsBetween(Config.SCORE_FROM, Config.SCORE_TO);
        provider.commit();
    }
}

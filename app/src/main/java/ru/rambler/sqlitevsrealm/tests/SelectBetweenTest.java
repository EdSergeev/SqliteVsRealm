package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class SelectBetweenTest implements BaseTest {
    @Override
    public void run(DbProvider provider) {
        provider.selectStudentsBetween(Config.SCORE_FROM, Config.SCORE_TO);
    }
}

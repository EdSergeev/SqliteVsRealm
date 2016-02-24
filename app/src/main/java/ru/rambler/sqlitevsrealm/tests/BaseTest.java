package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public interface BaseTest {
    void run(DbProvider provider);
}

package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.providers.DbProvider;

public abstract class BaseTest implements Runnable {

    private final DbProvider provider;

    public BaseTest(DbProvider provider) {
        this.provider = provider;
    }

    @Override
    public final void run() {
        work(provider);
    }

    protected abstract void work(DbProvider provider);

    public abstract String getTestName();
}

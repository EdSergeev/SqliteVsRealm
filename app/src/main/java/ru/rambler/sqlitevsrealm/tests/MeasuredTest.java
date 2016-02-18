package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.TestCallback;

public abstract class MeasuredTest implements Runnable {

    private final TestCallback callback;
    private final String providerName;

    public MeasuredTest(TestCallback callback, String providerName) {
        this.callback = callback;
        this.providerName = providerName;
    }

    @Override
    public final void run() {
        long startTime = System.currentTimeMillis();
        work();
        long spentTime = System.currentTimeMillis() - startTime;
        callback.postResult(providerName, spentTime);
    }

    protected abstract void work();
}

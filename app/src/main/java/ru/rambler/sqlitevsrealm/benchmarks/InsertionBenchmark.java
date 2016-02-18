package ru.rambler.sqlitevsrealm.benchmarks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ru.rambler.sqlitevsrealm.providers.DbProvider;
import ru.rambler.sqlitevsrealm.providers.SqliteProvider;
import ru.rambler.sqlitevsrealm.tests.InsertionTest;

public class InsertionBenchmark {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final List<DbProvider> providers = new ArrayList<>();
    private Future task;

    public InsertionBenchmark() {
        providers.add(new SqliteProvider());
    }

    public void begin() {
//        task = executorService.submit(new InsertionTest() {
//
//        });
    }

    public void cancel() {
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
    }
}

package ru.rambler.sqlitevsrealm.benchmarks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.rambler.sqlitevsrealm.providers.DbProvider;
import ru.rambler.sqlitevsrealm.tests.BaseTest;

public abstract class Benchmark {
    private static final String TAG = Benchmark.class.getSimpleName();
    private final List<DbProvider> providerList;
    private TaskRunner task;
    private static Executor executor = Executors.newSingleThreadExecutor();

    public Benchmark(@NonNull List<DbProvider> providerList) {
        this.providerList = providerList;
    }

    public void begin() {
        cancel();
        task = new TaskRunner();
        task.executeOnExecutor(executor);
    }

    protected abstract BaseTest createTest(DbProvider provider);

    public void cancel() {
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    private class TaskRunner extends AsyncTask<Void, Void, Map<String, Long>> {

        @Override
        protected Map<String, Long> doInBackground(Void... params) {
            Map<String, Long> results = new HashMap<>(providerList.size());
            for (DbProvider provider : providerList) {
                if (isCancelled()) {
                    break;
                }
                long spentTime = runTest(provider);
                Log.d(TAG, "Provider '" + provider.getName() + "' spent " + spentTime + "ms");
                results.put(provider.getName(), spentTime);
            }
            return results;
        }

        @Override
        protected void onPostExecute(Map<String, Long> stringLongMap) {

        }

        private long runTest(DbProvider provider) {
            long startTime = System.currentTimeMillis();
            createTest(provider).run();
            return System.currentTimeMillis() - startTime;
        }
    }
}

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
    private static Executor executor = Executors.newSingleThreadExecutor();
    private final List<DbProvider> providerList;
    private final String benchmarkName;
    private final Callback callback;
    private TaskRunner task;

    public Benchmark(@NonNull String benchmarkName,
                     @NonNull List<DbProvider> providerList,
                     @NonNull Callback callback) {
        this.benchmarkName = benchmarkName;
        this.providerList = providerList;
        this.callback = callback;
    }

    protected abstract BaseTest createTest();

    public void begin() {
        cancel();
        task = new TaskRunner();
        task.executeOnExecutor(executor);
    }

    public void cancel() {
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    private class TaskRunner extends AsyncTask<Void, Void, Map<String, Long>> {

        @Override
        protected void onPreExecute() {
            callback.begin();
        }

        @Override
        protected Map<String, Long> doInBackground(Void... params) {
            Map<String, Long> results = new HashMap<>(providerList.size());
            BaseTest test = createTest();
            for (DbProvider provider : providerList) {
                if (isCancelled()) {
                    break;
                }
                provider.open();
                try {
                    long spentTime = runTest(test, provider);
                    results.put(provider.getName(), spentTime);
                    Log.d(TAG, benchmarkName + ": Provider '" + provider.getName() + "' spent " + spentTime + "ms");
                } finally {
                    provider.close();
                }
            }
            return results;
        }

        @Override
        protected void onPostExecute(Map<String, Long> results) {
            callback.postResults(benchmarkName, results);
        }

        private long runTest(BaseTest test, DbProvider provider) {
            long startTime = System.currentTimeMillis();
            provider.begin();
            try {
                test.run(provider);
            } finally {
                provider.commit();
            }
            return System.currentTimeMillis() - startTime;
        }
    }

    public interface Callback {
        void begin();

        void postResults(String benchmarkName, Map<String, Long> results);
    }
}

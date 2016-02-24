package ru.rambler.sqlitevsrealm;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.rambler.sqlitevsrealm.benchmarks.Benchmark;
import ru.rambler.sqlitevsrealm.providers.DbProvider;
import ru.rambler.sqlitevsrealm.providers.RealmProvider;
import ru.rambler.sqlitevsrealm.providers.SqliteProvider;
import ru.rambler.sqlitevsrealm.tests.BaseTest;
import ru.rambler.sqlitevsrealm.tests.Config;
import ru.rambler.sqlitevsrealm.tests.DeleteGroupTest;
import ru.rambler.sqlitevsrealm.tests.InsertionTest;
import ru.rambler.sqlitevsrealm.tests.SelectBetweenTest;
import ru.rambler.sqlitevsrealm.tests.SelectGroupSortedTest;
import ru.rambler.sqlitevsrealm.tests.SelectGroupTest;
import ru.rambler.sqlitevsrealm.widgets.TitledProgressBar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout container;
    private ContentLoadingProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (LinearLayout) findViewById(R.id.content);
        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);

        List<DbProvider> providers = createProviders();

        initProviders(providers);

        initBenchmarks(providers);
    }

    private List<DbProvider> createProviders() {
        List<DbProvider> providers = new ArrayList<>();
        providers.add(new RealmProvider());
        providers.add(new SqliteProvider());
        return providers;
    }

    private void initProviders(List<DbProvider> providers) {
        for (DbProvider provider : providers) {
            provider.init(this);
            provider.open();
            provider.clean();
            provider.close();
        }
    }

    private void initBenchmarks(List<DbProvider> providers) {
        String insertName = String.format("Insert %d items", Config.GROUPS * Config.STUDENTS_PER_GROUP);
        new Benchmark(insertName, providers, benchmarkCallback) {
            @Override
            protected BaseTest createTest() {
                return new InsertionTest();
            }
        }.begin();

        new Benchmark("Select students using 'groupId'", providers, benchmarkCallback) {
            @Override
            protected BaseTest createTest() {
                return new SelectGroupTest();
            }
        }.begin();

        new Benchmark("Select students using 'groupId' and sort", providers, benchmarkCallback) {
            @Override
            protected BaseTest createTest() {
                return new SelectGroupSortedTest();
            }
        }.begin();

        new Benchmark("Select students using 'between (m,n)'", providers, benchmarkCallback) {
            @Override
            protected BaseTest createTest() {
                return new SelectBetweenTest();
            }
        }.begin();

        new Benchmark("Delete students using groupId", providers, benchmarkCallback) {
            @Override
            protected BaseTest createTest() {
                return new DeleteGroupTest();
            }
        }.begin();
    }

    private final Benchmark.Callback benchmarkCallback = new Benchmark.Callback() {
        @Override
        public void begin() {
            if (isFinishing()) {
                return;
            }
            progress.show();
        }

        @Override
        public void postResults(String benchmarkName, Map<String, Long> results) {
            if (isFinishing()) {
                return;
            }
            addBenchmarkNameView(benchmarkName);
            if (results.isEmpty()) {
                return;
            }
            long maxScore = 0;
            long sum = 0;
            for (long score : results.values()) {
                maxScore = Math.max(maxScore, score);
                sum += score;
            }
            double maxValue = maxScore + (sum / results.size()) * 0.5;
            for (Map.Entry<String, Long> result : results.entrySet()) {
                addScoreView(result.getKey(), result.getValue().intValue(), (int) maxValue);
            }
            progress.hide();
        }
    };

    private void addBenchmarkNameView(String benchmarkName) {
        View view = LayoutInflater.from(this).inflate(R.layout.benchark_title, container, false);
        ((TextView) view.findViewById(R.id.title)).setText(benchmarkName);
        container.addView(view);
    }

    private void addScoreView(String title, int score, int max) {
        TitledProgressBar resultBar = new TitledProgressBar(this);
        resultBar.setMax(max);
        resultBar.setProgress(score);
        resultBar.setTitle(title + ": " + score + "ms");
        container.addView(resultBar);
    }
}

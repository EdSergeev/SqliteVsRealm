package ru.rambler.sqlitevsrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.rambler.sqlitevsrealm.benchmarks.Benchmark;
import ru.rambler.sqlitevsrealm.providers.DbProvider;
import ru.rambler.sqlitevsrealm.providers.RealmProvider;
import ru.rambler.sqlitevsrealm.providers.SqliteProvider;
import ru.rambler.sqlitevsrealm.tests.BaseTest;
import ru.rambler.sqlitevsrealm.tests.InsertionTest;
import ru.rambler.sqlitevsrealm.tests.SelectByGroupIdTest;
import ru.rambler.sqlitevsrealm.widgets.TitledProgressBar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (LinearLayout) findViewById(R.id.content);

        List<DbProvider> providers = new ArrayList<DbProvider>() {{
            add(new RealmProvider());
            add(new SqliteProvider());
        }};
        for (DbProvider provider : providers) {
            provider.init(this);
            provider.open();
            provider.clean();
            provider.close();
        }

        new Benchmark(providers) {
            @Override
            protected BaseTest createTest(DbProvider provider) {
                return new InsertionTest(provider);
            }
        }.begin();

        new Benchmark(providers) {
            @Override
            protected BaseTest createTest(DbProvider provider) {
                return new SelectByGroupIdTest(provider);
            }
        }.begin();

        addBenchmarkResult("Insertion");
        addResult("Sqlite: 4353ms", 79, 100);
        addResult("Realm: 3353ms", 69, 100);
    }

    private void addBenchmarkResult(String benchmarkTitle) {
        View view = View.inflate(this, R.layout.benchark_title, container);
        ((TextView) view.findViewById(R.id.title)).setText(benchmarkTitle);
    }

    private void addResult(String title, int score, int max) {
        TitledProgressBar resultBar = new TitledProgressBar(this);
        resultBar.setProgress(score);
        resultBar.setMax(max);
        resultBar.setTitle(title);
        container.addView(resultBar);
    }
}

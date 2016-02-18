package ru.rambler.sqlitevsrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ru.rambler.sqlitevsrealm.benchmarks.Benchmark;
import ru.rambler.sqlitevsrealm.providers.DbProvider;
import ru.rambler.sqlitevsrealm.providers.SqliteProvider;
import ru.rambler.sqlitevsrealm.tests.BaseTest;
import ru.rambler.sqlitevsrealm.tests.InsertionTest;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<DbProvider> providers = new ArrayList<DbProvider>() {{
            add(new SqliteProvider());
        }};
        for (DbProvider provider : providers) {
            provider.open(this);
            provider.clean();
        }

        new Benchmark(providers) {
            @Override
            protected BaseTest createTest(DbProvider provider) {
                return new InsertionTest(provider);
            }
        }.begin();
    }

}

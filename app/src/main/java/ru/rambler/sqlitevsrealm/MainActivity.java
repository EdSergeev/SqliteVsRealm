package ru.rambler.sqlitevsrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ru.rambler.sqlitevsrealm.benchmarks.Benchmark;
import ru.rambler.sqlitevsrealm.providers.DbProvider;
import ru.rambler.sqlitevsrealm.providers.RealmProvider;
import ru.rambler.sqlitevsrealm.providers.SqliteProvider;
import ru.rambler.sqlitevsrealm.tests.BaseTest;
import ru.rambler.sqlitevsrealm.tests.InsertionTest;
import ru.rambler.sqlitevsrealm.tests.SelectByGroupIdTest;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

}

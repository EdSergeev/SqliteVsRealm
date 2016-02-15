package ru.rambler.sqlitevsrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void testInserts(DbProvider provider) {
        provider.begin();
        //provider.insert(new Group())
        provider.commit();
    }
}

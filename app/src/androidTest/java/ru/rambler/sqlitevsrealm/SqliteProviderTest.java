package ru.rambler.sqlitevsrealm;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;
import ru.rambler.sqlitevsrealm.providers.DbProvider;
import ru.rambler.sqlitevsrealm.providers.SqliteProvider;

public class SqliteProviderTest extends AndroidTestCase {

    DbProvider provider;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        provider = new SqliteProvider();
        provider.open(getContext());
    }

    public void testInserts() {
        provider.begin();
        long id;
        id = provider.insert(new Group("Group 1"));
        Assert.assertFalse(id == 0);
        id = provider.insert(new Student("student 1", 55, id));
        Assert.assertFalse(id == 0);
        provider.commit();
    }
}

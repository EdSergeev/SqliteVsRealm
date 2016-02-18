package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;
import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class InsertionTest extends BaseTest {
    private static final int GROUP_COUNT = 100;
    private static final int STUDENTS_PER_GROUP = 500;

    public InsertionTest(DbProvider provider) {
        super(provider);
    }

    @Override
    public String getTestName() {
        return "Insertion";
    }

    @Override
    protected void work(DbProvider provider) {
        provider.begin();
        for (int g = 0; g < GROUP_COUNT; g++) {
            long groupId = provider.insert(new Group(g, "Group #" + g));
            for (int s = 0; s < STUDENTS_PER_GROUP; s++) {
                provider.insert(new Student(g * STUDENTS_PER_GROUP + s + 1, "Student #" + g + "/" + "s", s, groupId));
            }
        }
        provider.commit();
    }
}

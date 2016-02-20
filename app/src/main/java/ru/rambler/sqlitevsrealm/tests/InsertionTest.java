package ru.rambler.sqlitevsrealm.tests;

import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;
import ru.rambler.sqlitevsrealm.providers.DbProvider;

public class InsertionTest extends BaseTest {

    public InsertionTest(DbProvider provider) {
        super(provider);
    }

    @Override
    protected void work(DbProvider provider) {
        provider.begin();
        for (int g = 0; g < Config.GROUPS; g++) {
            long groupId = provider.insert(new Group(g, "Group #" + g));
            for (int s = 0; s < Config.STUDENTS_PER_GROUP; s++) {
                provider.insert(new Student(g * Config.STUDENTS_PER_GROUP + s + 1, "Student #" + g + "/" + "s", s, groupId));
            }
        }
        provider.commit();
    }
}

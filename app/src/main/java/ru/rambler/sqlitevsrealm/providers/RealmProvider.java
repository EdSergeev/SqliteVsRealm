package ru.rambler.sqlitevsrealm.providers;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.rambler.sqlitevsrealm.models.Group;
import ru.rambler.sqlitevsrealm.models.Student;
import ru.rambler.sqlitevsrealm.providers.realm.RealmGroup;
import ru.rambler.sqlitevsrealm.providers.realm.RealmStudent;

public class RealmProvider implements DbProvider {
    private Realm realm;

    @Override
    public String getName() {
        return "Realm";
    }

    @Override
    public void init(Context context) {
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build());
    }

    @Override
    public void open() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void close() {
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    public void clean() {
        realm.beginTransaction();
        realm.clear(RealmStudent.class);
        realm.clear(RealmGroup.class);
        realm.commitTransaction();
    }

    @Override
    public void begin() {
        realm.beginTransaction();
    }

    @Override
    public void commit() {
        realm.commitTransaction();
    }

    @Override
    public long insert(Student student) {
        RealmStudent item = realm.createObject(RealmStudent.class);
        item.setId(student.getId());
        item.setAverageScore(student.getAverageScore());
        item.setName(student.getName());
        item.setGroupId(student.getGroupId());
        return item.getId();
    }

    @Override
    public long insert(Group group) {
        RealmGroup item = new RealmGroup(group.getGroupId(), group.getTitle());
        return realm.copyToRealm(item).getGroupId();
    }

    @Override
    public long selectStudentsByGroupId(long groupId) {
        RealmResults<RealmStudent> list = realm.where(RealmStudent.class)
                .equalTo("groupId", groupId)
                .findAll();
        iterateStudents(list);
        return list.size();
    }

    @Override
    public long selectStudentsByGroupIdAndSort(long groupId) {
        RealmResults<RealmStudent> list = realm.where(RealmStudent.class)
                .equalTo("groupId", groupId)
                .findAllSorted("averageScore", Sort.DESCENDING);
        iterateStudents(list);
        return list.size();
    }

    @Override
    public long selectStudentsBetween(int fromScore, int toScore) {
        RealmResults<RealmStudent> list = realm.where(RealmStudent.class).between("averageScore", fromScore, toScore).findAll();
        iterateStudents(list);
        return list.size();
    }

    private void iterateStudents(RealmResults<RealmStudent> list) {
        for (RealmStudent student : list) {
            student.getId();
            student.getName();
            student.getAverageScore();
            student.getGroupId();
        }
    }

    @Override
    public long deleteGroup(long groupId) {
        realm.where(RealmStudent.class).equalTo("groupId", groupId).findAll().clear();
        realm.where(RealmGroup.class).equalTo("groupId", groupId).findAll().clear();
        return 0;
    }
}

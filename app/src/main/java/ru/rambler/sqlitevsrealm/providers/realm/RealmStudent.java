package ru.rambler.sqlitevsrealm.providers.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmStudent extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private int averageScore;
    private long groupId;

    public RealmStudent() {
    }

    public RealmStudent(long id, String name, int averageScore, long groupId) {
        this.id = id;
        this.averageScore = averageScore;
        this.groupId = groupId;
        this.name = name;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

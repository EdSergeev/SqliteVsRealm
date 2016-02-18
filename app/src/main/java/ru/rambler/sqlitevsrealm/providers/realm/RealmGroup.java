package ru.rambler.sqlitevsrealm.providers.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmGroup extends RealmObject {
    @PrimaryKey
    private long groupId;
    private String title;


    public RealmGroup() {
    }

    public RealmGroup(long groupId, String title) {
        this.groupId = groupId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

}

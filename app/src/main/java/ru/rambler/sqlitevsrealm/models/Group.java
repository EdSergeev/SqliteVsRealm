package ru.rambler.sqlitevsrealm.models;

public class Group {
    private long groupId;
    private String title;

    public Group(long groupId, String title) {
        this.groupId = groupId;
        this.title = title;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getTitle() {
        return title;
    }
}

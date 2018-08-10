package com.mickaelbrenoit.demo.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.mickaelbrenoit.demo.database.Names.FIELD_TITLE_ALBUM;
import static com.mickaelbrenoit.demo.database.Names.FOREIGN_KEY_USERID_ALBUM;
import static com.mickaelbrenoit.demo.database.Names.PRIMARY_KEY_ALBUM;
import static com.mickaelbrenoit.demo.database.Names.PRIMARY_KEY_USER;
import static com.mickaelbrenoit.demo.database.Names.TABLE_NAME_ALBUM;

@Entity(tableName = TABLE_NAME_ALBUM,
        foreignKeys = @ForeignKey(  entity = User.class,
                                    parentColumns = PRIMARY_KEY_USER,
                                    childColumns = FOREIGN_KEY_USERID_ALBUM))
public class Album {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = PRIMARY_KEY_ALBUM)
    private int id;

    @NonNull
    @ColumnInfo(name = FIELD_TITLE_ALBUM)
    private String title;

    @NonNull
    @ColumnInfo(name = FOREIGN_KEY_USERID_ALBUM)
    private int userId;

    public Album() {
    }

    @Ignore
    public Album(@NonNull int id, @NonNull String title, @NonNull int userId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public int getUserId() {
        return userId;
    }

    public void setUserId(@NonNull int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                '}';
    }
}

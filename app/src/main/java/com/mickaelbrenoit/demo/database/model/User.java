package com.mickaelbrenoit.demo.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.mickaelbrenoit.demo.database.Names.FIELD_EMAIL_USER;
import static com.mickaelbrenoit.demo.database.Names.FIELD_NAME_USER;
import static com.mickaelbrenoit.demo.database.Names.FIELD_PASSWORD_USER;
import static com.mickaelbrenoit.demo.database.Names.FIELD_USERNAME_USER;
import static com.mickaelbrenoit.demo.database.Names.PRIMARY_KEY_USER;
import static com.mickaelbrenoit.demo.database.Names.TABLE_NAME_USER;

@Entity(tableName = TABLE_NAME_USER)
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = PRIMARY_KEY_USER)
    private int id;

    @NonNull
    @ColumnInfo(name = FIELD_NAME_USER)
    private String name;

    @NonNull
    @ColumnInfo(name = FIELD_USERNAME_USER)
    private String username;

    @NonNull
    @ColumnInfo(name = FIELD_EMAIL_USER)
    private String email;

    @NonNull
    @ColumnInfo(name = FIELD_PASSWORD_USER)
    private String password;

    public User() {
    }

    public User(@NonNull int id, @NonNull String name, @NonNull String username, @NonNull String email, @NonNull String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}

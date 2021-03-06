package com.mickaelbrenoit.demo.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static com.mickaelbrenoit.demo.database.Names.FIELD_EMAIL_USER;
import static com.mickaelbrenoit.demo.database.Names.FIELD_NAME_USER;
import static com.mickaelbrenoit.demo.database.Names.FIELD_PASSWORD_USER;
import static com.mickaelbrenoit.demo.database.Names.FIELD_USERNAME_USER;
import static com.mickaelbrenoit.demo.database.Names.PRIMARY_KEY_USER;
import static com.mickaelbrenoit.demo.database.Names.TABLE_NAME_USER;

@Entity(tableName = TABLE_NAME_USER)
public class User implements Parcelable{

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

    @Ignore
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(password);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        username = in.readString();
        email = in.readString();
        password = in.readString();
    }

    @Override
    public String toString() {
        return username;
    }
}

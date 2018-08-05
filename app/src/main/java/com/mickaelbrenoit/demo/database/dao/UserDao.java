package com.mickaelbrenoit.demo.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mickaelbrenoit.demo.database.model.User;

import java.util.List;

import static com.mickaelbrenoit.demo.database.Names.FIELD_PASSWORD_USER;
import static com.mickaelbrenoit.demo.database.Names.FIELD_USERNAME_USER;
import static com.mickaelbrenoit.demo.database.Names.TABLE_NAME_USER;

@Dao
public interface UserDao {

    @Insert
    void insertAllUsers(List<User> users);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM " + TABLE_NAME_USER)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + TABLE_NAME_USER +
            " WHERE " + FIELD_USERNAME_USER + " = :username" +
            " AND " + FIELD_PASSWORD_USER + " = :password")
    User login(String username, String password);
}

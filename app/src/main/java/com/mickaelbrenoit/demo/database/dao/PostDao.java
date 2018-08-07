package com.mickaelbrenoit.demo.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mickaelbrenoit.demo.database.model.Post;

import java.util.List;

import static com.mickaelbrenoit.demo.database.Names.FOREIGN_KEY_USERID_POST;
import static com.mickaelbrenoit.demo.database.Names.TABLE_NAME_POST;

@Dao
public interface PostDao {

    @Insert
    void insertAllPosts(List<Post> posts);

    @Update
    void updatePost(Post post);

    @Delete
    void deletePost(Post post);

    @Query("SELECT * FROM " + TABLE_NAME_POST)
    List<Post> getAllPosts();

    @Query("SELECT * FROM " + TABLE_NAME_POST + " WHERE " + FOREIGN_KEY_USERID_POST + " = :userLoggedId")
    List<Post> getAllPostsByUserId(int userLoggedId);
}

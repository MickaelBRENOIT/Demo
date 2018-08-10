package com.mickaelbrenoit.demo.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mickaelbrenoit.demo.database.model.Album;

import java.util.List;

import static com.mickaelbrenoit.demo.database.Names.FOREIGN_KEY_USERID_ALBUM;
import static com.mickaelbrenoit.demo.database.Names.TABLE_NAME_ALBUM;

@Dao
public interface AlbumDao {

    @Insert
    void insertAllAlbums(List<Album> albums);

    @Insert
    void insertAlbum(Album album);

    @Update
    void updateAlbum(Album album);

    @Delete
    void deleteAlbum(Album album);

    @Query("DELETE FROM " + TABLE_NAME_ALBUM)
    void deleteAllAlbums();

    @Query("SELECT * FROM " + TABLE_NAME_ALBUM)
    List<Album> getAllAlbums();

    @Query("SELECT * FROM " + TABLE_NAME_ALBUM + " WHERE " + FOREIGN_KEY_USERID_ALBUM + " = :userId")
    List<Album> getAllAlbumsByUserId(int userId);
}

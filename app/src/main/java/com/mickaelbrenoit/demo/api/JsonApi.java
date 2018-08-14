package com.mickaelbrenoit.demo.api;

import com.mickaelbrenoit.demo.api.model.AlbumApi;
import com.mickaelbrenoit.demo.api.model.PhotoApi;
import com.mickaelbrenoit.demo.api.model.PostApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JsonApi {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Headers("Content-Type: application/json")
    @GET("posts")
    Call<List<PostApi>> getPosts();

    @Headers("Content-Type: application/json")
    @GET("albums")
    Call<List<AlbumApi>> getAlbums();

    @Headers("Content-Type: application/json")
    @GET("photos")
    Call<List<PhotoApi>> getPhotos(@Query("albumId") int albumId);

}

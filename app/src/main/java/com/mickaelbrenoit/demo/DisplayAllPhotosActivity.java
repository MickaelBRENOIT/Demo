package com.mickaelbrenoit.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.mickaelbrenoit.demo.adapter.ListPhotosAdapter;
import com.mickaelbrenoit.demo.api.JsonApi;
import com.mickaelbrenoit.demo.api.model.PhotoApi;
import com.mickaelbrenoit.demo.database.model.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mickaelbrenoit.demo.api.JsonApi.BASE_URL;
import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_OBJECT_ALBUM;

public class DisplayAllPhotosActivity extends NavigationDrawerActivity {

    private static final String TAG = "DisplayAllPhotosActivit";

    @Nullable
    @BindView(R.id.recyclerView_photos)
    RecyclerView recyclerView_photos;

    @Nullable
    @BindView(R.id.button_retour_photo)
    Button button_retour_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_photos);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Album album = bundle.getParcelable(PUT_EXTRA_OBJECT_ALBUM);
            Log.d(TAG, "onCreate: id --> " + album.getId());

            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

            JsonApi jsonApi = retrofit.create(JsonApi.class);
            Call<List<PhotoApi>> call = jsonApi.getPhotos(album.getId());

            call.enqueue(new Callback<List<PhotoApi>>(){

                @Override
                public void onResponse(Call<List<PhotoApi>> call, Response<List<PhotoApi>> response) {

//                    List<PhotoApi> photoApiList = new ArrayList<>();
//                    for (PhotoApi photoApi : response.body()) {
//                        photoApiList.add(new PhotoApi(photoApi.getId(), photoApi.getTitle(), photoApi.getUrl(), photoApi.getThumbnailUrl(), photoApi.getAlbumId()));
//                    }

                    final ListPhotosAdapter listPhotosAdapter = new ListPhotosAdapter(response.body(), getApplicationContext());
                    final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

                    recyclerView_photos.setLayoutManager(mLayoutManager);
                    recyclerView_photos.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_photos.setAdapter(listPhotosAdapter);

                }

                @Override
                public void onFailure(Call<List<PhotoApi>> call, Throwable t) {
                    Log.e(TAG, "onFailure: Something goes wrong: " + t.getMessage());
                }

            });
        }
    }
}

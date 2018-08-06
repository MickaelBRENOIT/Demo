package com.mickaelbrenoit.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.api.JsonApi;
import com.mickaelbrenoit.demo.api.model.PostApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mickaelbrenoit.demo.api.JsonApi.BASE_URL;

public class PostFragment extends Fragment {

    private static final String TAG = "PostFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, null);

        Log.d(TAG, "onCreateView: in");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<PostApi>> call = jsonApi.getPosts();

        call.enqueue(new Callback<List<PostApi>>(){
            @Override
            public void onResponse(Call<List<PostApi>> call, Response<List<PostApi>> response) {
                Log.d(TAG, "onResponse: Server response: " + response.toString());
//                Log.d(TAG, "onResponse: received information: " + response.body().toString());
                Log.d(TAG, "onResponse: size: " +  response.body().size());

                for (PostApi postApi : response.body()) {
                    // TODO - insert all posts in database
                    Log.d(TAG, "onResponse: --> " + postApi.getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<PostApi>> call, Throwable t) {
                Log.e(TAG, "onFailure: Something goes wrong: " + t.getMessage());
            }
        });

        return view;
    }
}

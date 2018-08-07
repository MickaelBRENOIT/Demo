package com.mickaelbrenoit.demo.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.adapter.ListPostsAdapter;
import com.mickaelbrenoit.demo.api.JsonApi;
import com.mickaelbrenoit.demo.api.model.PostApi;
import com.mickaelbrenoit.demo.database.DatabaseSingleton;
import com.mickaelbrenoit.demo.database.model.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mickaelbrenoit.demo.api.JsonApi.BASE_URL;

public class PostFragment extends Fragment {

    private static final String TAG = "PostFragment";

    @BindView(R.id.recyclerView_posts)
    RecyclerView recyclerView_posts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, null);

        ButterKnife.bind(this, view);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<PostApi>> call = jsonApi.getPosts();

        call.enqueue(new Callback<List<PostApi>>(){
            @Override
            public void onResponse(Call<List<PostApi>> call, Response<List<PostApi>> response) {
                Log.d(TAG, "onResponse: Server response: " + response.toString());
//                Log.d(TAG, "onResponse: received information: " + response.body().toString());
                Log.d(TAG, "onResponse: size: " +  response.body().size());

                List<Post> postList = new ArrayList<>();
                for (PostApi postApi : response.body()) {
                    // TODO - insert all posts in database
                    Log.d(TAG, "onResponse: --> " + postApi.getTitle());
                    postList.add(new Post(postApi.getId(), postApi.getTitle(), postApi.getBody(), postApi.getUserId()));
                }

                AddAllPostsAsyncTask addAllPostsAsyncTask = new AddAllPostsAsyncTask(postList);
                addAllPostsAsyncTask.execute();

            }

            @Override
            public void onFailure(Call<List<PostApi>> call, Throwable t) {
                Log.e(TAG, "onFailure: Something goes wrong: " + t.getMessage());
            }
        });

        return view;
    }

    private class AddAllPostsAsyncTask extends AsyncTask<Void, Void, Void> {

        private List<com.mickaelbrenoit.demo.database.model.Post> postList;

        public AddAllPostsAsyncTask(List<com.mickaelbrenoit.demo.database.model.Post> postList) {
            this.postList = postList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            db.postDao().insertAllPosts(postList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ListPostsAdapter listPostsAdapter = new ListPostsAdapter(postList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView_posts.setLayoutManager(mLayoutManager);
            recyclerView_posts.setItemAnimator(new DefaultItemAnimator());
            recyclerView_posts.setAdapter(listPostsAdapter);
        }
    }
}

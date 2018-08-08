package com.mickaelbrenoit.demo.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mickaelbrenoit.demo.AddOrModifyPostActivity;
import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.adapter.ListPostsAdapter;
import com.mickaelbrenoit.demo.api.JsonApi;
import com.mickaelbrenoit.demo.api.model.PostApi;
import com.mickaelbrenoit.demo.database.DatabaseSingleton;
import com.mickaelbrenoit.demo.database.model.Post;
import com.mickaelbrenoit.demo.database.model.User;
import com.mickaelbrenoit.demo.helper.EnumCode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.mickaelbrenoit.demo.api.JsonApi.BASE_URL;
import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_OBJECT_POST;
import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_TITLE_POST;
import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_USER_LOGGED;
import static com.mickaelbrenoit.demo.helper.RequestCode.RESULT_CODE_ADD_POST;

public class PostFragment extends Fragment {

    private static final String TAG = "PostFragment";

    @BindView(R.id.recyclerView_posts)
    RecyclerView recyclerView_posts;

    static User userLogged;
    static List<Post> postListAll = new ArrayList<>();
    static List<Post> postListByUserId = new ArrayList<>();
    static ListPostsAdapter listPostsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, null);

        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userLogged = bundle.getParcelable(PUT_EXTRA_USER_LOGGED);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<PostApi>> call = jsonApi.getPosts();

        call.enqueue(new Callback<List<PostApi>>(){
            @Override
            public void onResponse(Call<List<PostApi>> call, Response<List<PostApi>> response) {
                Log.d(TAG, "onResponse: Server response: " + response.toString());
                Log.d(TAG, "onResponse: size: " +  response.body().size());


                for (PostApi postApi : response.body()) {
                    postListAll.add(new Post(postApi.getId(), postApi.getTitle(), postApi.getBody(), postApi.getUserId()));
                }

                AddAllPostsAsyncTask addAllPostsAsyncTask = new AddAllPostsAsyncTask();
                addAllPostsAsyncTask.execute();

            }

            @Override
            public void onFailure(Call<List<PostApi>> call, Throwable t) {
                Log.e(TAG, "onFailure: Something goes wrong: " + t.getMessage());
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT) {    //if swipe left
                    Post post = postListByUserId.get(position);

                    UpdateArraylistPostsAsyncTask updateArraylistPostsAsyncTask = new UpdateArraylistPostsAsyncTask(EnumCode.DELETE, post, position);
                    updateArraylistPostsAsyncTask.execute();

                }
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foregroundView = ((ListPostsAdapter.ViewHolderPost) viewHolder).view_foreground_post;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((ListPostsAdapter.ViewHolderPost) viewHolder).view_foreground_post;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((ListPostsAdapter.ViewHolderPost) viewHolder).view_foreground_post;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((ListPostsAdapter.ViewHolderPost) viewHolder).view_foreground_post;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView_posts);

        return view;
    }

    private class AddAllPostsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            if (db.postDao().getAllPosts().isEmpty()) {
                db.postDao().insertAllPosts(postListAll);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            InstantiateRecyclerViewWithPostsByUserIdAsyncTask instantiateRecyclerViewWithPostsByUserIdAsyncTask = new InstantiateRecyclerViewWithPostsByUserIdAsyncTask();
            instantiateRecyclerViewWithPostsByUserIdAsyncTask.execute();
        }
    }

    private class InstantiateRecyclerViewWithPostsByUserIdAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            postListByUserId = db.postDao().getAllPostsByUserId(userLogged.getId());
            listPostsAdapter = new ListPostsAdapter(postListByUserId);
            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView_posts.setLayoutManager(mLayoutManager);
                    recyclerView_posts.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_posts.setAdapter(listPostsAdapter);
                }
            });
            return null;
        }
    }

    @Optional
    @OnClick(R.id.button_add_post)
    public void addPost(View v) {
        Intent intent = new Intent(getActivity(), AddOrModifyPostActivity.class);
        intent.putExtra(PUT_EXTRA_TITLE_POST, getString(R.string.button_add_post));
        startActivityForResult(intent, RESULT_CODE_ADD_POST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CODE_ADD_POST:
                if (resultCode == RESULT_OK) {
                    Post post = data.getParcelableExtra(PUT_EXTRA_OBJECT_POST);
                    post.setUserId(userLogged.getId());

                    UpdateArraylistPostsAsyncTask updateArraylistPostsAsyncTask = new UpdateArraylistPostsAsyncTask(EnumCode.ADD, post, null);
                    updateArraylistPostsAsyncTask.execute();
                }
                break;
        }
    }

    private class UpdateArraylistPostsAsyncTask extends AsyncTask<Void, Void, Void> {

        private EnumCode mEnumCode;
        private Post mPost;
        private Integer position;

        public UpdateArraylistPostsAsyncTask(EnumCode enumCode, Post post, Integer position) {
            mEnumCode = enumCode;
            mPost = post;
            this.position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            if (mEnumCode == EnumCode.ADD) {
                db.postDao().insertPost(mPost);
            } else if (mEnumCode == EnumCode.DELETE) {
                db.postDao().deletePost(mPost);
            }

            final List<Post> posts = db.postDao().getAllPostsByUserId(userLogged.getId());

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mEnumCode == mEnumCode.ADD) {
                        postListByUserId = posts;
                        listPostsAdapter.notifyItemAdded(posts);
                    } else if (mEnumCode == mEnumCode.DELETE) {
                        postListByUserId = posts;
                        listPostsAdapter.notifyItemDeleted(posts, position);
                    }
                }
            });
            return null;
        }
    }
}

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.adapter.ListAlbumsAdapter;
import com.mickaelbrenoit.demo.api.JsonApi;
import com.mickaelbrenoit.demo.api.model.AlbumApi;
import com.mickaelbrenoit.demo.database.DatabaseSingleton;
import com.mickaelbrenoit.demo.database.model.Album;
import com.mickaelbrenoit.demo.database.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mickaelbrenoit.demo.api.JsonApi.BASE_URL;
import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_USER_LOGGED;

public class AlbumFragment extends Fragment {

    private static final String TAG = "AlbumFragment";

    @BindView(R.id.spinner_list_users)
    Spinner spinner_list_users;
    static ArrayAdapter<String> spinnerUsersAdapter;

    @BindView(R.id.recyclerView_albums)
    RecyclerView recyclerView_albums;
    static ListAlbumsAdapter listAlbumsAdapter;

    static User userLogged;
    static List<Album> albumListAll = new ArrayList<>();
    static List<Album> albumListByUserId = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, null);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userLogged = bundle.getParcelable(PUT_EXTRA_USER_LOGGED);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<AlbumApi>> call = jsonApi.getAlbums();

        call.enqueue(new Callback<List<AlbumApi>>(){
            @Override
            public void onResponse(Call<List<AlbumApi>> call, Response<List<AlbumApi>> response) {
                Log.d(TAG, "onResponse: Server response: " + response.toString());
                Log.d(TAG, "onResponse: size: " +  response.body().size());


                for (AlbumApi albumApi : response.body()) {
                    albumListAll.add(new Album(albumApi.getId(), albumApi.getTitle(), albumApi.getUserId()));
                }

                AddAllAlbumsAsyncTask addAllAlbumsAsyncTask = new AddAllAlbumsAsyncTask();
                addAllAlbumsAsyncTask.execute();

            }

            @Override
            public void onFailure(Call<List<AlbumApi>> call, Throwable t) {
                Log.e(TAG, "onFailure: Something goes wrong: " + t.getMessage());
            }
        });

        return view;
    }

    private class AddAllAlbumsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseSingleton db = com.mickaelbrenoit.demo.database.DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            if (db.albumDao().getAllAlbums().isEmpty()) {
                db.albumDao().insertAllAlbums(albumListAll);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            InstantiateRecyclerViewWithAlbumsByUserIdAsyncTask instantiateRecyclerViewWithAlbumsByUserIdAsyncTask = new InstantiateRecyclerViewWithAlbumsByUserIdAsyncTask();
            instantiateRecyclerViewWithAlbumsByUserIdAsyncTask.execute();
        }
    }

    private class InstantiateRecyclerViewWithAlbumsByUserIdAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            albumListByUserId = db.albumDao().getAllAlbumsByUserId(userLogged.getId());
            listAlbumsAdapter = new ListAlbumsAdapter(albumListByUserId, getActivity());
            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView_albums.setLayoutManager(mLayoutManager);
                    recyclerView_albums.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_albums.setAdapter(listAlbumsAdapter);
                    InstantiateSpinnerUsers instantiateSpinnerUsers = new InstantiateSpinnerUsers();
                    instantiateSpinnerUsers.execute();
                }
            });
            return null;
        }
    }

    private class InstantiateSpinnerUsers extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            List<String> users = new ArrayList<>();
            for (User user: db.userDao().getAllUsers()) {
                users.add(user.getUsername());
            }
            return users;
        }

        @Override
        protected void onPostExecute(List<String> users) {

//            spinnerUsersAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, users) {
//
////                @Override
////                public boolean isEnabled(int position) {
////                    if (position == 0) {
////                        // Desactivate first item
////                        return false;
////                    } else {
////                        return true;
////                    }
////                }
//
//                @Override
//                public View getDropDownView(int position, View convertView,
//                                            ViewGroup parent) {
//                    View view = super.getDropDownView(position, convertView, parent);
//                    TextView tv = (TextView) view;
////                    if (position == 0) {
////                        // Set the disable item text color
////                        tv.setTextColor(some color ...);
////                    } else {
////                        tv.setTextColor(some color ...);
////                    }
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        tv.setTextColor(getResources().getColor(R.color.colorAccent, null));
//                    } else {
//                        tv.setTextColor(getResources().getColor(R.color.colorAccent));
//                    }
//
//                    return view;
//                }
//            };

            spinnerUsersAdapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.custom_spinner_text, users);
            spinnerUsersAdapter.setDropDownViewResource(R.layout.custom_spinner_text);
            spinner_list_users.setAdapter(spinnerUsersAdapter);
            spinner_list_users.setSelection(spinnerUsersAdapter.getPosition(userLogged.getUsername()));
        }
    }

    @OnItemSelected(R.id.spinner_list_users)
    public void onItemSpinnerUsersSelected(Spinner spinner) {
        Log.d(TAG, "onItemSpinnerUsersSelected: pos --> " + spinner.getSelectedItemPosition());
        Log.d(TAG, "onItemSpinnerUsersSelected: str --> " + spinner.getSelectedItem().toString());

        UpdateListAlbumAsyncTask updateListAlbumAsyncTask = new UpdateListAlbumAsyncTask();
        updateListAlbumAsyncTask.execute(spinner.getSelectedItem().toString());
    }

    private class UpdateListAlbumAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(getActivity().getApplicationContext());
            albumListByUserId = db.albumDao().getAllAlbumsByUsername(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listAlbumsAdapter.notifyItemUpdated(albumListByUserId);
                }
            });
        }
    }
}

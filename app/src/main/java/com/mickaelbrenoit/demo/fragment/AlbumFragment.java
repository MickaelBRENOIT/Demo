package com.mickaelbrenoit.demo.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mickaelbrenoit.demo.R;
import com.mickaelbrenoit.demo.database.DatabaseSingleton;
import com.mickaelbrenoit.demo.database.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_USER_LOGGED;

public class AlbumFragment extends Fragment {

    private static final String TAG = "AlbumFragment";

    @BindView(R.id.spinner_list_users)
    Spinner spinner_list_users;
    static ArrayAdapter<String> spinnerUsersAdapter;
    @BindView(R.id.recyclerView_albums)
    RecyclerView recyclerView_albums;

    static User userLogged;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, null);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userLogged = bundle.getParcelable(PUT_EXTRA_USER_LOGGED);
        }

        InstantiateSpinnerUsers instantiateSpinnerUsers = new InstantiateSpinnerUsers();
        instantiateSpinnerUsers.execute();

        return view;
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
    }
}

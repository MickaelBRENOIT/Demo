package com.mickaelbrenoit.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import com.mickaelbrenoit.demo.database.model.User;
import com.mickaelbrenoit.demo.fragment.AlbumFragment;
import com.mickaelbrenoit.demo.fragment.PostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_USER_LOGGED;

public class FragmentActivity extends NavigationDrawerActivity {

    private static final String TAG = "FragmentActivity";

    @Nullable @BindView(R.id.navigation)
    BottomNavigationView navigation;

    User userLogged;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d(TAG, "onNavigationItemSelected: in");
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_post:
                    Log.d(TAG, "onNavigationItemSelected: postfragment");
                    fragment = new PostFragment();
                    break;
                case R.id.navigation_photo:
                    Log.d(TAG, "onNavigationItemSelected: albumfragment");
                    fragment = new AlbumFragment();
                    break;
                case R.id.navigation_notifications:
                    return true;
            }

            return loadFragment(fragment, userLogged);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        userLogged = intent.getParcelableExtra(PUT_EXTRA_USER_LOGGED);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new PostFragment(), userLogged);
    }

    private boolean loadFragment(Fragment fragment, User user) {
        if (fragment != null) {
            if (user != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(PUT_EXTRA_USER_LOGGED, user);
                fragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}

package com.mickaelbrenoit.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.mickaelbrenoit.demo.database.model.User;
import com.mickaelbrenoit.demo.fragment.PostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentActivity extends NavigationDrawerActivity {

    @Nullable @BindView(R.id.navigation)
    BottomNavigationView navigation;

    User userLogged;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_post:
                    fragment = new PostFragment();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
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
        userLogged = intent.getParcelableExtra("USER_LOGGED");

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new PostFragment(), userLogged);
    }

    private boolean loadFragment(Fragment fragment, User user) {
        if (fragment != null) {
            if (user != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("USER_LOGGED", user);
                fragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

}

package com.mickaelbrenoit.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mickaelbrenoit.demo.database.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.mickaelbrenoit.demo.RequestCode.PUT_EXTRA_OBJECT_POST;
import static com.mickaelbrenoit.demo.RequestCode.PUT_EXTRA_TITLE_POST;

public class AddOrModifyPostActivity extends NavigationDrawerActivity {

    @Nullable
    @BindView(R.id.textView_title_post_activity)
    TextView textView_title_post_activity;

    @Nullable
    @BindView(R.id.textInputLayout_title_post_object)
    TextInputLayout textInputLayout_title_post_object;
    @Nullable
    @BindView(R.id.textInputEditText_title_post_object)
    TextInputEditText textInputEditText_title_post_object;

    @Nullable
    @BindView(R.id.textInputLayout_body_post_object)
    TextInputLayout textInputLayout_body_post_object;
    @Nullable
    @BindView(R.id.textInputEditText_body_post_object)
    TextInputEditText textInputEditText_body_post_object;

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_modify_post);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra(PUT_EXTRA_TITLE_POST);
        textView_title_post_activity.setText(title);
        if (intent.getParcelableExtra(PUT_EXTRA_OBJECT_POST) != null) {
            // TODO retrieve the post
        }

    }

    @Optional
    @OnClick(R.id.button_validate_post)
    public void validatePost(View v) {
        Toast.makeText(getApplicationContext(), "Validate", Toast.LENGTH_LONG).show();
    }

    @Optional
    @OnClick(R.id.button_cancel_post)
    public void cancelPost(View v) {
        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
    }
}

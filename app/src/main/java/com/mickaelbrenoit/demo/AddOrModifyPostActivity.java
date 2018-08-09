package com.mickaelbrenoit.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mickaelbrenoit.demo.database.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_OBJECT_POST;
import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_TITLE_POST;

public class AddOrModifyPostActivity extends NavigationDrawerActivity {

    private static final String TAG = "AddOrModifyPostActivity";

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
        Log.d(TAG, "onCreate: Package: " + intent.getPackage() + ", Class: " + intent.getClass() + ", Type: " + intent.getType() + ", Action: " + intent.getAction());
        String title = intent.getStringExtra(PUT_EXTRA_TITLE_POST);
        textView_title_post_activity.setText(title);
        if (intent.getParcelableExtra(PUT_EXTRA_OBJECT_POST) != null) {
            post = intent.getParcelableExtra(PUT_EXTRA_OBJECT_POST);

            textInputEditText_title_post_object.setText(post.getTitle());
            textInputEditText_body_post_object.setText(post.getBody());
        }

    }

    @Optional
    @OnClick(R.id.button_validate_post)
    public void validatePost(View v) {
        if (submitForm()) {

            if (post == null) {
                post = new Post();
            }

            post.setTitle(textInputEditText_title_post_object.getText().toString());
            post.setBody(textInputEditText_body_post_object.getText().toString());

            Intent i = new Intent();
            i.putExtra(PUT_EXTRA_OBJECT_POST, post);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    private boolean submitForm() {
        if (!validateTitle()) {
            return false;
        }
        if (!validateBody()) {
            return false;
        }
        return true;
    }

    private boolean validateBody() {
        if (textInputEditText_body_post_object.getText().toString().length() == 0) {
            textInputLayout_body_post_object.setErrorEnabled(true);
            textInputLayout_body_post_object.setError(getString(R.string.body_check_empty));
            return false;
        } else {
            textInputLayout_body_post_object.setError(null);
            textInputLayout_body_post_object.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateTitle() {
        if (textInputEditText_title_post_object.getText().toString().length() == 0) {
            textInputLayout_title_post_object.setErrorEnabled(true);
            textInputLayout_title_post_object.setError(getString(R.string.title_check_empty));
            return false;
        } else {
            textInputLayout_title_post_object.setError(null);
            textInputLayout_title_post_object.setErrorEnabled(false);
        }
        return true;
    }

    @Optional
    @OnClick(R.id.button_cancel_post)
    public void cancelPost(View v) {
        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
    }

    private void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}

package com.mickaelbrenoit.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.mickaelbrenoit.demo.database.DatabaseSingleton;
import com.mickaelbrenoit.demo.database.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mickaelbrenoit.demo.helper.RequestCode.PUT_EXTRA_USER_LOGGED;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.textInputLayout_username)
    TextInputLayout textInputLayout_username;
    @BindView(R.id.textInputLayout_password)
    TextInputLayout textInputLayout_password;

    @BindView(R.id.textInputEditText_username)
    TextInputEditText textInputEditText_username;
    @BindView(R.id.textInputEditText_password)
    TextInputEditText textInputEditText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    public void login(View view) {
        if (validateForm()) {
            String username = textInputEditText_username.getText().toString();
            String password = textInputEditText_password.getText().toString();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginActivity.this);
            loginAsyncTask.execute(user);

        }
    }

    private boolean validateForm() {
        if (!validateUsername()) {
            requestFocus(textInputLayout_username);
            return false;
        }

        if (!validatePassword()) {
            requestFocus(textInputLayout_password);
            return false;
        }

        return true;
    }

    private boolean validateUsername() {
        if (textInputEditText_username.getText().toString().length() == 0) {
            textInputLayout_username.setErrorEnabled(true);
            textInputLayout_username.setError(getString(R.string.username_check_empty));
            return false;
        } else {
            textInputLayout_username.setError(null);
            textInputLayout_username.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (textInputEditText_password.getText().toString().length() == 0) {
            textInputLayout_password.setErrorEnabled(true);
            textInputLayout_password.setError(getString(R.string.password_check_empty));
            return false;
        } else {
            textInputLayout_password.setError(null);
            textInputLayout_password.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class LoginAsyncTask extends AsyncTask<User, Void, Void> {

        private Context context;
        private User user;

        public LoginAsyncTask(Context context) {
            this.context = context;
            this.user = null;
        }

        @Override
        protected Void doInBackground(User... users) {
            DatabaseSingleton db = DatabaseSingleton.getAppDatabase(context);
            user = db.userDao().login(users[0].getUsername(), users[0].getPassword());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (user != null) {
                ((Activity) context).finish();
                Intent intent = new Intent(context, FragmentActivity.class);
                intent.putExtra(PUT_EXTRA_USER_LOGGED, user);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "This user doesn't exist...", Toast.LENGTH_LONG).show();
            }
        }
    }
}

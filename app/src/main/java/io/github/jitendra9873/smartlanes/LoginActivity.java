package io.github.jitendra9873.smartlanes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    // Login Preferences
    public static final String SP_LOGIN_PREF = "LoginPreferences";
    public static final String SP_LOGIN_LOGGED_IN_STATE = "LoggedInState";
    public static final String SP_LOGIN_USER_NAME = "Username";
    public static final String SP_LOGIN_AADHAR = "aadhar";
    public static final String SP_LOGIN_LICENCE = "licence";

    // Lock to avoid deadlock
    private static boolean lockLogin = false;

    // UI references.
    private EditText usernameET;
    private EditText passwordView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        usernameET = findViewById(R.id.login_username);

        passwordView = findViewById(R.id.login_password);
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 907 || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button emailSignInButton = findViewById(R.id.login_sign_in);
        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        setupProgressDialog();
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    private void attemptLogin() {
        if (lockLogin) {
            return;
        }

        // Reset errors.
        usernameET.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = usernameET.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isValidPassword(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            usernameET.setError(getString(R.string.error_field_required));
            focusView = usernameET;
            cancel = true;
        } else if (!isValidUsername(email)) {
            usernameET.setError(getString(R.string.error_invalid_email));
            focusView = usernameET;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            progressDialog.setMessage(getString(R.string.login_authenticating));
            progressDialog.show();
            signIn(email, password);
        }
    }

    static boolean isValidUsername(String username) {
        return username.matches(".+@.+\\..+");
    }

    static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    void signIn(final String username, final String password) {
        new signInTask(this, username, password, usernameET, passwordView, progressDialog).execute();
    }

    private static class signInTask extends AsyncTask<Void, Void, Integer> {

        WeakReference<Context> wrContext;
        WeakReference<Activity> wrActivity;
        WeakReference<EditText> wrUsernameET, wrPassET;
        String username, password;
        ProgressDialog progressDialog;

        //String login pass (Dummy Data)
        String[][] loginData = {
                {"dcunha.cyprien@gmail.com", "pass@123", "564356728976", "444c3443-4146-3439-3433"},
                {"jitendrakumhar@gmail.com", "qwerty@123", "763456728976", "4b413139-4551-3133-3136"},
                {"edwardgonsalves24@gmail.com", "pass@123", "093456728976", "4d483031-4142-3132-3334"},
                {"rchta26@gmail.com", "pass@123", "432756728976", "524a3237-5441-3133-3337"}
        };

        signInTask(Activity activity, final String username, final String password,
                   EditText usernameET, EditText passET, ProgressDialog progressDialog){
            this.wrActivity = new WeakReference<>(activity);
            this.wrContext = new WeakReference<>(activity.getApplicationContext());
            this.wrUsernameET = new WeakReference<>(usernameET);
            this.wrPassET = new WeakReference<>(passET);
            this.username = username;
            this.password = password;
            this.progressDialog = progressDialog;
        }

        @Override
        protected void onPreExecute(){
            lockLogin = true;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int idx = -1;
            for(int i=0; i<loginData.length; i++){
                if(Objects.equals(loginData[i][0], username)){
                    if(Objects.equals(loginData[i][1], password)){
                        idx = i;
                        break;
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return idx;

        }

        @Override
        protected void onPostExecute(Integer response){
            if(response != -1){
                SharedPreferences prefs = wrContext.get().getSharedPreferences(SP_LOGIN_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putBoolean(SP_LOGIN_LOGGED_IN_STATE, true);
                editor.putString(SP_LOGIN_USER_NAME, username);
                editor.putString(SP_LOGIN_AADHAR, loginData[response][2]);
                editor.putString(SP_LOGIN_LICENCE, loginData[response][3]);

                editor.apply();

                wrActivity.get().finish();
                wrContext.get().startActivity(new Intent(wrContext.get(), MainActivity.class));
            }
            else{
                wrUsernameET.get().setError(wrContext.get().getString(R.string.error_incorrect_password));
                wrPassET.get().setError(wrContext.get().getString(R.string.error_incorrect_password));
                wrPassET.get().requestFocus();
            }
            lockLogin = false;
            progressDialog.dismiss();
        }
    }

}
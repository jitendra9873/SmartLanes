package io.github.jitendra9873.smartlanes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_AADHAR;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_PREF;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_USER_NAME;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Toolbar toolbar = findViewById(R.id.ep_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText usernameET = findViewById(R.id.edit_profile_username);
        EditText aadHarET = findViewById(R.id.edit_profile_aadhar);
        EditText licenceET = findViewById(R.id.edit_profile_licence);

        SharedPreferences prefs = getSharedPreferences(SP_LOGIN_PREF, Context.MODE_PRIVATE);
        String email = prefs.getString(SP_LOGIN_USER_NAME, "dcunha.cyprien@gmail.com");
        String aadharNo = prefs.getString(SP_LOGIN_AADHAR, "564356728976");

        usernameET.setText(email);
        aadHarET.setText(aadharNo);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Verifying");

        final Activity activity = this;

        FloatingActionButton fab = findViewById(R.id.et_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected void onPreExecute(){
                        progressDialog.show();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid){
                        progressDialog.dismiss();
                        activity.finish();
                    }
                }.execute();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

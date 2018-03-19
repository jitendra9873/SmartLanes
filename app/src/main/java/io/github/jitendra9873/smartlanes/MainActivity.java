package io.github.jitendra9873.smartlanes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_ID;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_LOGGED_IN_STATE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        new LoginTask(this, progressDialog).execute();
    }

    private static class LoginTask extends AsyncTask<Void, Void, Void> {

        WeakReference<Activity> wrActivity;
        WeakReference<Context> wrContext;
        ProgressDialog progressDialog;

        LoginTask(Activity activity, ProgressDialog pd){
            wrActivity = new WeakReference<>(activity);
            wrContext = new WeakReference<>(activity.getApplicationContext());
            this.progressDialog = pd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(wrContext.get().getString(R.string.login_logging_in));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences prefs = wrContext.get().getSharedPreferences(SP_LOGIN_ID, Context.MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean(SP_LOGIN_LOGGED_IN_STATE, false);

            if(!isLoggedIn){
                wrActivity.get().finish();
                wrActivity.get().startActivity(new Intent(wrActivity.get(), LoginActivity.class));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPreExecute();
            progressDialog.dismiss();
        }

    }
}

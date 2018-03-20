package io.github.jitendra9873.smartlanes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import io.github.jitendra9873.smartlanes.fragments.AccountFragment;
import io.github.jitendra9873.smartlanes.fragments.HomeFragment;
import io.github.jitendra9873.smartlanes.fragments.TransactionsFragment;

import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_ID;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_LOGGED_IN_STATE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        new LoginTask(this, progressDialog).execute();

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.nav_bar_open, R.string.nav_bar_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new HomeFragment());
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(Gravity.START)){
            drawerLayout.closeDrawer(Gravity.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return false;
    }

    private void displaySelectedScreen(int itemId) {
        android.support.v4.app.Fragment fragment = null;

        switch (itemId){
            case R.id.nav_menu_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_menu_rec_transactions:
                fragment = new TransactionsFragment();
                break;
            case R.id.nav_menu_profile:
                fragment = new AccountFragment();
                break;
        }
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        if(drawerLayout.isDrawerOpen(Gravity.START)){
            drawerLayout.closeDrawer(Gravity.START);
        }
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

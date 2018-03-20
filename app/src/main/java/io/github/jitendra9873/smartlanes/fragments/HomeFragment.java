package io.github.jitendra9873.smartlanes.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import io.github.jitendra9873.smartlanes.R;
import io.github.jitendra9873.smartlanes.adapters.TransactionAdapter;
import io.github.jitendra9873.smartlanes.data.Transaction;

import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_BALANCE;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_PREF;

public class HomeFragment extends Fragment {

    TransactionAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle("Home");

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        AppBarLayout appBarLayout = view.findViewById(R.id.home_appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()){
                    ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                }
                else{
                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                }
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.home_transaction);
        adapter = new TransactionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Button accDetBtn = view.findViewById(R.id.home_account_details);
        accDetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = new AccountFragment();
                    fragment.setEnterTransition(new AutoTransition());
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                    navigationView.getMenu().getItem(2).setChecked(true);
                }
            }
        });

        Button moreTrxButton = view.findViewById(R.id.home_recent_trx_more_button);
        moreTrxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager != null){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = new TransactionsFragment();
                    fragment.setEnterTransition(new AutoTransition());
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                    navigationView.getMenu().getItem(1).setChecked(true);
                }
            }
        });

        Button addMoneyButton = view.findViewById(R.id.home_add_money);
        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(R.layout.dialog_addmoney);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncTask<Void, Void, Void>(){

                            ProgressDialog progressDialog = new ProgressDialog(getContext());

                            @Override
                            protected void onPreExecute(){
                                progressDialog.setMessage("Verifying and Adding Money");
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
                            }
                        }.execute();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        SharedPreferences prefs = getContext().getSharedPreferences(SP_LOGIN_PREF, Context.MODE_PRIVATE);
        String balance = "₹ " + prefs.getFloat(SP_LOGIN_BALANCE, 0.0f);
        TextView balanceTV = view.findViewById(R.id.home_account_balance);
        balanceTV.setText(balance);

        prepareDummyTransactions();

        return view;
    }

    private void prepareDummyTransactions() {
        String[] name={"Jaipur toll","Naughar toll","Jaipur toll","Diya toll","Pune toll","Bandra toll","Nh toll","Vaia toll"}; //8
        String[] licence={"MH-04-AK-1298","RJ-27-TA-1337","RJ-27-TA-1337","MH-04-AK-1298","MH-04-AK-1298","RJ-27-TA-1337","RJ-27-TA-1337",};  //8
        double[] charge={17.8,25.7,25.7,17.8,17.8,25.7,17.8,17.8,17.8,25.7};
        long[] ts={1521580245851L, 1520580245851L, 1519580245851L,
                1515580245851L,1514580245851L,1510580245851L,1509580245851L,1508580245851L};


        for(int i=0; i<5; i++){
            Transaction t = new Transaction(i+1, name[i], licence[i], charge[i], new Date(ts[i]));
            adapter.addTransaction(t);
        }
    }

}

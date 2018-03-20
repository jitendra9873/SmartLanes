package io.github.jitendra9873.smartlanes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import io.github.jitendra9873.smartlanes.R;
import io.github.jitendra9873.smartlanes.adapters.TransactionAdapter;
import io.github.jitendra9873.smartlanes.data.Transaction;

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

        prepareDummyTransactions();

        return view;
    }

    private void prepareDummyTransactions() {
        for(int i=0; i<5; i++){
            Transaction t = new Transaction(1, "Jaipur Toll", "MH-04-AK-1298", 17.8, new Date());
            adapter.addTransaction(t);
        }
    }

}

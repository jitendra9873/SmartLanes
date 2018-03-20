package io.github.jitendra9873.smartlanes.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import io.github.jitendra9873.smartlanes.R;
import io.github.jitendra9873.smartlanes.adapters.TransactionAdapter;
import io.github.jitendra9873.smartlanes.data.Transaction;

public class TransactionsFragment extends Fragment {

    TransactionAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle("Recent Transactions");

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        RecyclerView recyclerView = view.findViewById(R.id.transaction_rv);
        adapter = new TransactionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpacingDecoration(3));
        recyclerView.setAdapter(adapter);

        prepareDummyTransactions();

        return view;
    }

    private void prepareDummyTransactions() {
        String[] name={"Jaipur toll","Naughar toll","Jaipur toll","Diya toll","Pune toll",
                "Bandra toll","Nh toll","Vaia toll", "Diya toll","Pune toll"};
        String[] licence={"MH-04-AK-1298","RJ-27-TA-1337","RJ-27-TA-1337","MH-04-AK-1298",
                "MH-04-AK-1298","RJ-27-TA-1337","RJ-27-TA-1337", "MH-04-AK-1298","RJ-27-TA-1337",
                "MH-04-AK-1298","RJ-27-TA-1337"};
        double[] charge={17.8,25.7,25.7,17.8,17.8,25.7,17.8,17.8,17.8,25.7,17.8,25.7};
        long[] ts={1521580245851L, 1520580245851L, 1519580245851L,
                1515580245851L,1514580245851L,1510580245851L,1509580245851L,1508580245851L,
                1507580245851L,1506580245851L};


        for(int i=0; i<10; i++){
            Transaction t = new Transaction(i+1, name[i], licence[i], charge[i], new Date(ts[i]));
            adapter.addTransaction(t);
        }
    }

    public static class SpacingDecoration extends RecyclerView.ItemDecoration {
        private int spacing;

        SpacingDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = spacing;
            outRect.top = spacing;

        }
    }
}

package io.github.jitendra9873.smartlanes.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

        RecyclerView recyclerView = view.findViewById(R.id.transaction_rv);
        adapter = new TransactionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpacingDecoration(3));
        recyclerView.setAdapter(adapter);

        prepareDummyTransactions();

        return view;
    }

    private void prepareDummyTransactions() {
        for(int i=0; i<10; i++){
            Transaction t = new Transaction(1, "Jaipur Toll", "MH-04-AK-1298", 17.8, new Date());
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

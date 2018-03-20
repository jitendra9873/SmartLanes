package io.github.jitendra9873.smartlanes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.jitendra9873.smartlanes.R;

public class TransmitterFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transmitter, container, false);

        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle("Transmitter");

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        final View trnsRV = view.findViewById(R.id.transmission_rl);
        final View noTrnsRV = view.findViewById(R.id.no_transmission_rl);

        View startTrnsV = view.findViewById(R.id.start_transmission_lbtn);
        View stopTrnsV = view.findViewById(R.id.stop_transmission_lbtn);

        startTrnsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trnsRV.setVisibility(View.VISIBLE);
                noTrnsRV.setVisibility(View.GONE);
            }
        });

        stopTrnsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trnsRV.setVisibility(View.GONE);
                noTrnsRV.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

}

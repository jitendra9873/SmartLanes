package io.github.jitendra9873.smartlanes.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;

import io.github.jitendra9873.smartlanes.R;

import static android.app.Activity.RESULT_OK;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_AADHAR;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_LICENCE;
import static io.github.jitendra9873.smartlanes.LoginActivity.SP_LOGIN_PREF;

public class TransmitterFragment extends Fragment {

    private static int BLUETOOTH_REQUEST_CODE = 8739;

    private BluetoothAdapter bluetoothAdapter;
    private BeaconTransmitter beaconTransmitter;

    View trnsRV, noTrnsRV;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transmitter, container, false);

        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle("Transmitter");

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        trnsRV= view.findViewById(R.id.transmission_rl);
        noTrnsRV = view.findViewById(R.id.no_transmission_rl);

        View startTrnsV = view.findViewById(R.id.start_transmission_lbtn);
        View stopTrnsV = view.findViewById(R.id.stop_transmission_lbtn);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19");
        beaconTransmitter = new BeaconTransmitter(getContext(), beaconParser);

        startTrnsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothOn();
            }
        });

        stopTrnsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trnsRV.setVisibility(View.GONE);
                noTrnsRV.setVisibility(View.VISIBLE);
                beaconTransmitter.stopAdvertising();
            }
        });
        return view;
    }

    public void bluetoothOn(){
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, BLUETOOTH_REQUEST_CODE);
        }
        else{
            trnsRV.setVisibility(View.VISIBLE);
            noTrnsRV.setVisibility(View.GONE);
            beaconTransmitter.startAdvertising(getBeacon());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BLUETOOTH_REQUEST_CODE && resultCode == RESULT_OK && getContext() != null){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            trnsRV.setVisibility(View.VISIBLE);
            noTrnsRV.setVisibility(View.GONE);
            beaconTransmitter.startAdvertising(getBeacon());
        }
    }



    private Beacon getBeacon(){

        SharedPreferences preferences = getContext().getSharedPreferences(SP_LOGIN_PREF, Context.MODE_PRIVATE);
        String aadharNo = preferences.getString(SP_LOGIN_AADHAR, "564356728976");
        String licenceHex = preferences.getString(SP_LOGIN_LICENCE, "444c3443-4146-3439-3433");

        return new Beacon.Builder()
                .setId1(licenceHex + aadharNo)
                .setId2("0x" + aadharNo)
                .setManufacturer(0x0118)
                .setTxPower(-59)
                .setDataFields(Arrays.asList(new Long[] {0l}))
                .build();
    }

}

package io.github.jitendra9873.smartlanes.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import io.github.jitendra9873.smartlanes.R;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        setHasOptionsMenu(true);

        FragmentActivity act = getActivity();
        if(act != null) getActivity().setTitle("Account");

        TextView nameTV = view.findViewById(R.id.account_user_name);
        TextView emailTV = view.findViewById(R.id.account_user_email);
        TextView aadharnoTV = view.findViewById(R.id.account_aadhar_no);
        TextView balanceTV = view.findViewById(R.id.account_balance);
        TextView licenceIdTV = view.findViewById(R.id.account_licence_id);
        TextView licenceExpTV = view.findViewById(R.id.account_licence_expiry);

        final RadioButton button1 = view.findViewById(R.id.vt_rb1);
        final RadioButton button2 = view.findViewById(R.id.vt_rb2);

        button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) button2.setChecked(false);
            }
        });

        button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) button1.setChecked(false);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account, menu);
        menu.getItem(0).setIcon(R.drawable.ic_edit_white_24dp);
        menu.getItem(0).setTitle("Edit profile");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_account_edit:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

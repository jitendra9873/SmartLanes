package io.github.jitendra9873.smartlanes.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import io.github.jitendra9873.smartlanes.EditProfileActivity;
import io.github.jitendra9873.smartlanes.R;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_account, container, false);

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
        final RadioButton button3 = view.findViewById(R.id.vt_rb3);

        button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    button2.setChecked(false);
                    button3.setChecked(false);
                }
            }
        });

        button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    button1.setChecked(false);
                    button3.setChecked(false);
                }
            }
        });

        button3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    button1.setChecked(false);
                    button2.setChecked(false);
                }
            }
        });

        Button addButton = view.findViewById(R.id.add_vehicle_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Vehicle");
                builder.setView(R.layout.dialog_addvehicle);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncTask<Void, Void, Void>(){

                            ProgressDialog progressDialog = new ProgressDialog(getContext());

                            @Override
                            protected void onPreExecute(){
                                progressDialog.setMessage("Verifying");
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
                                View newVehRow = view.findViewById(R.id.table_hidden_row);
                                newVehRow.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

package io.github.jitendra9873.smartlanes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.jitendra9873.smartlanes.R;
import io.github.jitendra9873.smartlanes.data.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactionList;

    public TransactionAdapter(){
        transactionList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction trx = transactionList.get(position);
        holder.tollnameTV.setText(trx.trToll);
        holder.vechicleIdTV.setText(trx.vehicleId);

        String feeStr = String.format(Locale.US, "₹ %.2f", trx.trFee);
        holder.txFeeTV.setText(feeStr);

        String dateStr = new SimpleDateFormat("MMM yy", Locale.US).format(trx.trTime);
        holder.txTimeTV.setText(dateStr);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void addTransaction(Transaction t){
        transactionList.add(t);
        notifyItemInserted(transactionList.size() - 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tollnameTV;
        TextView vechicleIdTV;
        TextView txFeeTV;
        TextView txTimeTV;

        ViewHolder(View itemView) {
            super(itemView);
            tollnameTV = itemView.findViewById(R.id.trx_tollname);
            vechicleIdTV = itemView.findViewById(R.id.trx_vehicle_id);
            txFeeTV = itemView.findViewById(R.id.trx_fee);
            txTimeTV = itemView.findViewById(R.id.trx_time);
        }
    }
}

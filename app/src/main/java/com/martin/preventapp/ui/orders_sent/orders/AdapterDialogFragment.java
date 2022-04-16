package com.martin.preventapp.ui.orders_sent.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.ArrayList;

public class AdapterDialogFragment extends RecyclerView.Adapter<AdapterDialogFragment.AdapterDialogFragmentViewHolder> {
    private ArrayList<CardViewDetailOrder> arrayProducts;

    public static class AdapterDialogFragmentViewHolder extends RecyclerView.ViewHolder {
        public TextView productText;
        public TextView amountText;

        public AdapterDialogFragmentViewHolder(View itemView) {
            super(itemView);
            productText = itemView.findViewById(R.id.Client);
            amountText = itemView.findViewById(R.id.Amount);
        }
    }

    public AdapterDialogFragment(ArrayList<CardViewDetailOrder> arrayProducts2) {
        arrayProducts = arrayProducts2;
    }

    @Override
    public AdapterDialogFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_detail_order_sent, parent, false);
        AdapterDialogFragmentViewHolder evh = new AdapterDialogFragmentViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(AdapterDialogFragmentViewHolder holder, int position) {
        CardViewDetailOrder currentItem = arrayProducts.get(position);

        holder.productText.setText(currentItem.getProduct());
        holder.amountText.setText("Cantidad: " + currentItem.getAmount() + " " + currentItem.getUnit());
    }

    @Override
    public int getItemCount() {
        return arrayProducts.size();
    }
}

package com.martin.preventapp.ui.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.List;

public class CardViewOrderAdapter extends RecyclerView.Adapter<CardViewOrderAdapter.OrderViewHolder> {
    private List<CardViewOrder> items;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        // items field on CardView

        public TextView product;
        public TextView amount;

        public OrderViewHolder(View v) {
            super(v);

            product = (TextView) v.findViewById(R.id.Product);
            amount = (TextView) v.findViewById(R.id.amount);
        }
    }

    public CardViewOrderAdapter(List<CardViewOrder> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_order, viewGroup, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder viewHolder, int i) {
        viewHolder.product.setText(items.get(i).getProduct());
        viewHolder.amount.setText("Cantidad:" + String.valueOf(items.get(i).getAmount()));
    }
}

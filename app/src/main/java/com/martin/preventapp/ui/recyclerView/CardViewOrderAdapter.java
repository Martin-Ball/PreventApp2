package com.martin.preventapp.ui.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CardViewOrderAdapter extends RecyclerView.Adapter<CardViewOrderAdapter.OrderViewHolder> {
    private List<CardViewOrder> items;


    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        // items field on CardView

        public Button delete;
        public TextView product;
        public TextView amount;




        public OrderViewHolder(View v) {
            super(v);

            delete = (Button) v.findViewById(R.id.deleteProduct);
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
    public void onBindViewHolder(OrderViewHolder viewHolder, int position) {

        viewHolder.product.setText(items.get(position).getProduct());
        viewHolder.amount.setText("Cantidad:" + items.get(position).getAmount());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //viewHolder.amount.setText("POSICION CV: " + position + "ELIMINADO: " + items.get(position).getProduct());
                    //Toast.makeText(view.getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
                    items.remove(viewHolder.getAdapterPosition());
            }
        });

    }
}

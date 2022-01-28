package com.martin.preventapp.ui.new_order.recyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.ArrayList;


public class CardViewOrderAdapter extends RecyclerView.Adapter<CardViewOrderAdapter.CardViewOrderViewHolder> {
    private ArrayList<CardViewOrder> arrayProducts;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CardViewOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView productText;
        public TextView amountText;
        public ImageView deleteImage;

        public CardViewOrderViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            productText = itemView.findViewById(R.id.Product);
            amountText = itemView.findViewById(R.id.Amount);
            deleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public CardViewOrderAdapter(ArrayList<CardViewOrder> exampleList) {
        arrayProducts = exampleList;
    }

    @Override
    public CardViewOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_order, parent, false);
        CardViewOrderViewHolder evh = new CardViewOrderViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(CardViewOrderViewHolder holder, int position) {
        CardViewOrder currentItem = arrayProducts.get(position);

        holder.productText.setText(currentItem.getProduct());
        holder.amountText.setText(currentItem.getAmount());
    }

    @Override
    public int getItemCount() {
        return arrayProducts.size();
    }
}

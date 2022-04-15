package com.martin.preventapp.ui.new_order.recyclerView;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.ArrayList;


public class CardViewOrderAdapter extends RecyclerView.Adapter<CardViewOrderAdapter.CardViewOrderViewHolder> {
    private ArrayList<CardViewOrder> arrayProducts;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {

        void onDeleteClick(int position);

        void addButtonClick(int position);

        void removeButtonClick(int position);

        void editTextAmountChange(int position, String amount);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CardViewOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView productText;
        public TextView amountText;
        public EditText amountTextSelected;
        public ImageView deleteImage;
        public ImageView addImage;
        public ImageView removeImage;

        public CardViewOrderViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            productText = itemView.findViewById(R.id.Client);
            amountText = itemView.findViewById(R.id.Amount);
            amountTextSelected = itemView.findViewById(R.id.amountSelected);
            deleteImage = itemView.findViewById(R.id.image_delete_button);
            addImage = itemView.findViewById(R.id.image_add_button);
            removeImage = itemView.findViewById(R.id.image_remove_button);

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

            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.addButtonClick(position);
                        }
                    }
                }
            });

            amountTextSelected.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.editTextAmountChange(position, s.toString());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            removeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.removeButtonClick(position);
                        }
                    }
                }
            });
        }
    }

        public CardViewOrderAdapter(ArrayList<CardViewOrder> arrayProducts2) {
            arrayProducts = arrayProducts2;
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
            holder.amountText.setText("Cantidad: " + currentItem.getAmount());
            if (Integer.parseInt(currentItem.getAmount()) > 0) {
                holder.amountTextSelected.setText(currentItem.getAmount());
            }
        }

        @Override
        public int getItemCount() {
            return arrayProducts.size();
        }
    }

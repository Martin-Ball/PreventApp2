package com.martin.preventapp.View.new_order.recyclerView;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.ArrayList;


public class CardViewOrderAdapter extends RecyclerView.Adapter<CardViewOrderAdapter.CardViewOrderViewHolder>{
    private ArrayList<CardViewOrder> arrayProducts;
    private ArrayList<String> Units;
    private String CompanySelected;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void addButtonClick(int position);

        void editTextAmountChange(int position, String amount);

        void removeButtonClick(int position);

        void selectUnit(int position, String unit, int positionItem, int sizeSpinner, Spinner spinnerUnit);

        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CardViewOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView productText;
        public Spinner unit;
        public EditText amountTextSelected;
        public ImageView deleteImage;
        public ImageView addImage;
        public ImageView removeImage;

        public CardViewOrderViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            productText = itemView.findViewById(R.id.Client);
            unit = itemView.findViewById(R.id.Unit);
            amountTextSelected = itemView.findViewById(R.id.amountSelected);
            deleteImage = itemView.findViewById(R.id.image_delete_button);
            addImage = itemView.findViewById(R.id.image_add_button);
            removeImage = itemView.findViewById(R.id.image_remove_button);

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

            unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                                String unitSelected = adapterView.getItemAtPosition(i).toString();
                                listener.selectUnit(position, unitSelected, i, adapterView.getAdapter().getCount(), unit);
                            }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

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

        public CardViewOrderAdapter(ArrayList<CardViewOrder> arrayProducts2, String CompanySelected2, ArrayList<String> Units2) {
            this.arrayProducts = arrayProducts2;
            this.CompanySelected = CompanySelected2;
            this.Units = Units2;
        }

        @Override
        public CardViewOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_order, parent, false);
            CardViewOrderViewHolder evh = new CardViewOrderViewHolder(v, mListener);

            Spinner UnitSelector = v.findViewById(R.id.Unit);

            ArrayAdapter<String> adapterUnitsSpinner = new ArrayAdapter<>(v.getRootView().getContext(),
                    android.R.layout.simple_list_item_1, Units);

            UnitSelector.setAdapter(adapterUnitsSpinner);

            return evh;
        }

        @Override
        public void onBindViewHolder(CardViewOrderViewHolder holder, int position) {
            CardViewOrder currentItem = arrayProducts.get(position);

            holder.unit.setSelection(Integer.parseInt(currentItem.getPositionItem()));
            holder.productText.setText(currentItem.getProduct());

            if (Integer.parseInt(currentItem.getAmount()) > 0) {
                holder.amountTextSelected.setText(currentItem.getAmount());
            }
        }

        @Override
        public int getItemCount() {
            return arrayProducts.size();
        }
    }




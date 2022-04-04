package com.martin.preventapp.ui.list.dialogFragmentList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.ArrayList;

public class AdapterListDialogFragmentProduct extends RecyclerView.Adapter<AdapterListDialogFragmentProduct.AdapterDialogListFragmentViewHolder> {
private ArrayList<CardViewDetailProduct> arrayProducts;

    public static class AdapterDialogListFragmentViewHolder extends RecyclerView.ViewHolder {
        public TextView product;

        public AdapterDialogListFragmentViewHolder(View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.Product);
        }
    }

    public AdapterListDialogFragmentProduct(ArrayList<CardViewDetailProduct> arrayProducts2) {
        arrayProducts = arrayProducts2;
    }

    @Override
    public AdapterDialogListFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_product_list, parent, false);
        AdapterDialogListFragmentViewHolder evh = new AdapterDialogListFragmentViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(AdapterDialogListFragmentViewHolder holder, int position) {
        CardViewDetailProduct currentItem = arrayProducts.get(position);

        holder.product.setText(currentItem.getProduct());
    }

    @Override
    public int getItemCount() {
        return arrayProducts.size();
    }
}
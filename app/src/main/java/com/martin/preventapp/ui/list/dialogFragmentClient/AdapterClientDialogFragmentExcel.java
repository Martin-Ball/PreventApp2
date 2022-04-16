package com.martin.preventapp.ui.list.dialogFragmentClient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.ArrayList;

public class AdapterClientDialogFragmentExcel extends RecyclerView.Adapter<AdapterClientDialogFragmentExcel.AdapterDialogFragmentViewHolder> {
    private ArrayList<CardViewDetailExcel> arrayProducts;

    public static class AdapterDialogFragmentViewHolder extends RecyclerView.ViewHolder {
        public TextView clientText;
        public TextView CUITText;
        public TextView FantasyNameText;
        public TextView StreetAddressText;

        public AdapterDialogFragmentViewHolder(View itemView) {
            super(itemView);
            clientText = itemView.findViewById(R.id.Client);
            CUITText = itemView.findViewById(R.id.Amount);
            FantasyNameText = itemView.findViewById(R.id.FantasyName);
            StreetAddressText = itemView.findViewById(R.id.StreetAddress);
        }
    }

    public AdapterClientDialogFragmentExcel(ArrayList<CardViewDetailExcel> arrayProducts2) {
        arrayProducts = arrayProducts2;
    }

    @Override
    public AdapterDialogFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_client_list, parent, false);
        AdapterDialogFragmentViewHolder evh = new AdapterDialogFragmentViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(AdapterDialogFragmentViewHolder holder, int position) {
        CardViewDetailExcel currentItem = arrayProducts.get(position);

        holder.clientText.setText(currentItem.getClient());
        holder.CUITText.setText("CUIT: " + currentItem.getCUIT());
        holder.FantasyNameText.setText("Nombre de Fantasia: " + currentItem.getFantasyName());
        holder.StreetAddressText.setText("Dirección: " + currentItem.getStreetAddress());
    }

    @Override
    public int getItemCount() {
        return arrayProducts.size();
    }
}

package com.martin.preventapp.ui.orders_sent.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentDialogBinding;

import java.util.ArrayList;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    private @NonNull FragmentDialogBinding binding;

    private ArrayList<CardViewDetailOrder> arrayCardViewDetailProducts;
    private ArrayList<ArrayList<String>> arrayProducts;
    private AdapterDialogFragment adapterDialogFragment;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView closeFragment = root.findViewById(R.id.closeFragment);

        arrayCardViewDetailProducts = new ArrayList<>();

        arrayCardViewDetailProducts.add(new CardViewDetailOrder("PAPITAS", "0"));
        arrayCardViewDetailProducts.add(new CardViewDetailOrder("PAPITAS", "0"));
        arrayCardViewDetailProducts.add(new CardViewDetailOrder("PAPITAS", "0"));
        arrayCardViewDetailProducts.add(new CardViewDetailOrder("PAPITAS", "0"));
        arrayCardViewDetailProducts.add(new CardViewDetailOrder("PAPITAS", "0"));
        arrayCardViewDetailProducts.add(new CardViewDetailOrder("PAPITAS", "0"));
        arrayCardViewDetailProducts.add(new CardViewDetailOrder("PAPITAS", "0"));


        //add product and amount into array
        //arrayProducts.add(arrayCardViewDetailProducts);

        //add product and amount into hashmap
        //ProductAndAmount.put("Product", productAndAmount);
        //ProductAndAmount.put("Amount", "0");

        //add product and amount hashmap on Order Hashmap

        //ProductsOrders.put("Orders", ProductAndAmount);

        //build Recycler View with CardViews
        buildRecyclerView(root);

        //add product and amount into hashmap
        //ProductAndAmount.put("Product", productAndAmount);
        //ProductAndAmount.put("Amount", "0");

        //add product and amount hashmap on Order Hashmap

        //ProductsOrders.put("Orders", ProductAndAmount);

        //build Recycler View with CardViews
        buildRecyclerView(root);

        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return root;
    }

    public void buildRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.detailOrdersRV);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        adapterDialogFragment = new AdapterDialogFragment(arrayCardViewDetailProducts);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapterDialogFragment);
    }
}
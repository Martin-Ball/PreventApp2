package com.martin.preventapp.ui.list.dialogFragmentList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentDialogExcelBinding;
import com.martin.preventapp.databinding.FragmentDialogListBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.firebase.Products;
import com.martin.preventapp.ui.list.dialogFragmentClient.AdapterClientDialogFragmentExcel;
import com.martin.preventapp.ui.list.dialogFragmentClient.CardViewDetailExcel;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogFragmentList extends androidx.fragment.app.DialogFragment {

    private FragmentDialogListBinding binding;
    private String CompanySelected;
    private ArrayList<CardViewDetailProduct> CardViewProductList;
    private ArrayList<String> ProductList;
    private RecyclerView mRecyclerView;
    private AdapterListDialogFragmentProduct adapterListDialogFragmentProduct;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView closeFragment = root.findViewById(R.id.closeFragmentExcel);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            CompanySelected = bundle.getString("CompanySelected");
            CardViewProductList = (ArrayList<CardViewDetailProduct>) bundle.getSerializable("CardViewProductList");
            ProductList = (ArrayList<String>) bundle.getSerializable("ProductList");
        }

        //build Recycler View with CardViews
        buildRecyclerView(root);

        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //Upload clients
        Button UploadList = root.findViewById(R.id.uploadList);
        UploadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Products products = new Products();
                products.setProductList(CompanySelected, ProductList, getView());
            }
        });

        return root;
    }

    public void buildRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.detailListRV);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        adapterListDialogFragmentProduct = new AdapterListDialogFragmentProduct(CardViewProductList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapterListDialogFragmentProduct);
    }
}

package com.martin.preventapp.ui.list.dialogFragment;

import android.os.Bundle;
import android.util.Log;
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
import com.martin.preventapp.firebase.Clients;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogFragmentExcel extends androidx.fragment.app.DialogFragment {
    private @NonNull
    FragmentDialogExcelBinding binding;

    private ArrayList<CardViewDetailExcel> arrayCardViewExcelClient;
    private AdapterClientDialogFragmentExcel adapterClientDialogFragmentExcel;
    private HashMap<String, Object> nameClientList;
    private RecyclerView mRecyclerView;
    private String CompanySelected = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogExcelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView closeFragment = root.findViewById(R.id.closeFragmentExcel);
        TextView commentTV = root.findViewById(R.id.commentTV);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            CompanySelected = bundle.getString("CompanySelected");
            arrayCardViewExcelClient = (ArrayList<CardViewDetailExcel>) bundle.getSerializable("ClientList");
            nameClientList = (HashMap<String, Object>) bundle.getSerializable("nameClientList");
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
        Button UploadClients = root.findViewById(R.id.uploadClients);
        UploadClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clients clients = new Clients();
                clients.setClientList(CompanySelected, nameClientList, root);
            }
        });

        return root;
    }

    public void buildRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.detailOrdersRV);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        adapterClientDialogFragmentExcel = new AdapterClientDialogFragmentExcel(arrayCardViewExcelClient);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapterClientDialogFragmentExcel);
    }
}
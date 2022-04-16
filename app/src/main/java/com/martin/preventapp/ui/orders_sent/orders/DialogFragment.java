package com.martin.preventapp.ui.orders_sent.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentDialogBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    private @NonNull FragmentDialogBinding binding;

    private ArrayList<CardViewDetailOrder> arrayCardViewDetailProducts;
    private ArrayList<ArrayList<String>> arrayProducts;
    private AdapterDialogFragment adapterDialogFragment;
    private RecyclerView mRecyclerView;

    private String ClientSelected = "";
    private String HourSelected = "";
    private String CompanySelected = "";

    private HashMap<String, Object> Date = new HashMap<>();
    private HashMap<String, Object> Client = new HashMap<>();
    private HashMap<String, Object> Hour = new HashMap<>();
    private ArrayList<String> ProductAndAmount = new ArrayList<>();

    String Comment = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView closeFragment = root.findViewById(R.id.closeFragmentExcel);
        TextView commentTV = root.findViewById(R.id.commentTV);

        arrayCardViewDetailProducts = new ArrayList<>();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            ClientSelected = bundle.getString("ClientSelected");
            HourSelected = bundle.getString("HourSelected");
            Date = (HashMap<String, Object>) bundle.getSerializable("DateHashMap");
        }

        /*Client = (HashMap<String, Object>) Date.get("CLIENTE NUTRIFRESCA");
        //Date selected on calendar fragment
        Hour = (HashMap<String, Object>) Client.get("01:47");
        ProductAndAmount = (ArrayList<String>) Hour.get("2");
        Comment = Hour.get("comment").toString();*/

        Client = (HashMap<String, Object>) Date.get(ClientSelected);
        //Date selected on calendar fragment
        Hour = (HashMap<String, Object>) Client.get(HourSelected);


        Comment = Hour.get("comment").toString();

        for (int i=1; i<= Hour.size()-1; i++){
                ProductAndAmount = (ArrayList<String>) Hour.get(String.valueOf(i));
                arrayCardViewDetailProducts.add(new CardViewDetailOrder(ProductAndAmount.get(0), ProductAndAmount.get(1), ProductAndAmount.get(2)));
        }

        commentTV.setText("Comentario: " + Comment);

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
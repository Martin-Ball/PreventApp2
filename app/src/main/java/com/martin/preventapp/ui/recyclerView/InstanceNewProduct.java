package com.martin.preventapp.ui.recyclerView;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.List;

public class InstanceNewProduct {

    public void cardViewNewProduct(List items, View root)
    {
        // Get Recycler
        RecyclerView recycler = root.findViewById(R.id.ordersRecycler);
        recycler.setHasFixedSize(true);

        // Use admin for LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(root.getContext());
        recycler.setLayoutManager(lManager);

        // Create new adaptador
        RecyclerView.Adapter adapterCardViewOrders = new CardViewOrderAdapter(items);
        recycler.setAdapter(adapterCardViewOrders);

    }
}

package com.martin.preventapp.ui.orders_sent.fragment_orders;

import static android.widget.Toast.LENGTH_LONG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentOrdersBinding;
import com.martin.preventapp.databinding.FragmentOrdersSentBinding;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private @NonNull FragmentOrdersBinding binding;
    private MyRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
               
                // data to populate the RecyclerView with
                ArrayList<String> animalNames = new ArrayList<>();
                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");
                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");
                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");
                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");

                // set up the RecyclerView
                RecyclerView recyclerView = root.findViewById(R.id.rv2);
                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                adapter = new MyRecyclerViewAdapter(root.getContext(), animalNames);
                recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return root;

    }
}
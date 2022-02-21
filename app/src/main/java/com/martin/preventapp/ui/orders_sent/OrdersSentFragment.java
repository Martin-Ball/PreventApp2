package com.martin.preventapp.ui.orders_sent;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentOrdersSentBinding;
import com.martin.preventapp.ui.orders_sent.fragment_orders.OrdersFragment;

public class OrdersSentFragment extends Fragment {

    private OrdersSentViewModel ordersSentViewModel;
    private FragmentOrdersSentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersSentViewModel =
                new ViewModelProvider(this).get(OrdersSentViewModel.class);

        binding = FragmentOrdersSentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button bt = root.findViewById(R.id.button);
        OrdersFragment of = new OrdersFragment();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!of.isAdded()) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, of);
                    fragmentTransaction.commit();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
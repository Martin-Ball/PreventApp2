package com.martin.preventapp.ui.order_to_send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.martin.preventapp.databinding.FragmentOrderToSendBinding;
import com.martin.preventapp.databinding.FragmentOrderToSendBinding;

public class OrderToSend extends Fragment {

    private OrderToSendViewModel orderToSendViewModel;
    private FragmentOrderToSendBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderToSendViewModel =
                new ViewModelProvider(this).get(OrderToSendViewModel.class);

        binding = FragmentOrderToSendBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        orderToSendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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
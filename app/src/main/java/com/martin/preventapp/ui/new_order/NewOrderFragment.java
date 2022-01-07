package com.martin.preventapp.ui.new_order;

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

import com.martin.preventapp.databinding.FragmentNewOrderBinding;

public class NewOrderFragment extends Fragment {

    private NewOrderViewModel newOrderViewModel;
    private FragmentNewOrderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newOrderViewModel =
                new ViewModelProvider(this).get(NewOrderViewModel.class);

        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNewOrder;
        newOrderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
package com.martin.preventapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.martin.preventapp.databinding.FragmentDialogBinding;
import com.martin.preventapp.databinding.FragmentOrdersBinding;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
    private @NonNull FragmentDialogBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView closeFragment = root.findViewById(R.id.closeFragment);

        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return root;
    }
}
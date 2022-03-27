package com.martin.preventapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentListBinding;

public class ListFragment extends Fragment {

    private ListViewModel ordersSentViewModel;
    private FragmentListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersSentViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Buttons add
        Button listFile = root.findViewById(R.id.list_file);
        Button clientsFile = root.findViewById(R.id.client_file);

        //TV test
        TextView tvTest = root.findViewById(R.id.textView6);

        



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

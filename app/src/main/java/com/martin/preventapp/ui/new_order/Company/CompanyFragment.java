package com.martin.preventapp.ui.new_order.Company;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.martin.preventapp.MainActivity;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentCompanyBinding;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Company;
import com.martin.preventapp.ui.new_order.NewOrderFragment;

public class CompanyFragment extends Fragment {

    private FragmentCompanyBinding binding;
    private String companySelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCompanyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner company

        Company CompanyList = new Company();

        ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, CompanyList.companyList(root));
        binding.companyAutoCompleteTextView.setAdapter(arrayAdapter);

        binding.companyAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                companySelected = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(root.getContext(), companySelected, Toast.LENGTH_LONG).show();
            }
        });

        //go to NewOrderFragment
        Button GoNewOrder = root.findViewById(R.id.go_order);

        GoNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();// getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main,  NewOrderFragment.class, null)
                        //.setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
            }
        });


        // Inflate the layout for this fragment
        return root;

    }
}
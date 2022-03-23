package com.martin.preventapp.ui.new_order.Company;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.martin.preventapp.MainActivity;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentCompanyBinding;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.firebase.Company;
import com.martin.preventapp.firebase.Products;
import com.martin.preventapp.ui.new_order.NewOrderFragment;

public class CompanyFragment extends Fragment {

    private FragmentCompanyBinding binding;
    private String CompanySelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCompanyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner company

        Company CompanyList = new Company();

        //Spinner Company

        Spinner spinnerCompany = root.findViewById(R.id.spinnerCompany);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CompanyList.companyList(root));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter);

        spinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                CompanySelected = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(root.getContext(), CompanySelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //go to NewOrderFragment
        Button GoNewOrder = root.findViewById(R.id.go_order);

        Bundle bundle = new Bundle();

        FragmentManager fragmentManager = getParentFragmentManager();// getSupportFragmentManager();
        NewOrderFragment newOrderFragment = new NewOrderFragment();

        GoNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CompanySelected == "Seleccione un proveedor"){
                    Toast.makeText(root.getContext(), "Seleccione un proveedor", Toast.LENGTH_SHORT).show();
                }else {

                    bundle.putString("CompanySelected", CompanySelected); // Put anything what you want
                    newOrderFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, newOrderFragment)
                    .addToBackStack(null) // name can be null
                    .commit();
                }
            }
        });

        return root;
    }
}
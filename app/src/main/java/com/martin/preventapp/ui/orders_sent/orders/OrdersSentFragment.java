package com.martin.preventapp.ui.orders_sent.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentOrdersSentBinding;
import com.martin.preventapp.firebase.Company;
import com.martin.preventapp.ui.orders_sent.fragment_orders.FragmentDateSelector;

public class OrdersSentFragment extends Fragment {

    private OrdersSentViewModel ordersSentViewModel;
    private FragmentOrdersSentBinding binding;

    public String CompanySelected = "";
    public String SelectedClient;
    public String DateSelected = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersSentViewModel =
                new ViewModelProvider(this).get(OrdersSentViewModel.class);

        binding = FragmentOrdersSentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner Client Fragment
        FragmentDateSelector spinnerFragment = new FragmentDateSelector();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        //Spinner Company

        Company CompanyList = new Company();

        Spinner spinnerCompany = root.findViewById(R.id.spinnerCompany);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CompanyList.companyList(root));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter);

        spinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 CompanySelected = adapterView.getItemAtPosition(position).toString();
                 //Toast.makeText(root.getContext(), CompanySelected, Toast.LENGTH_SHORT).show();

                 if(position == 0){
                     Toast.makeText(root.getContext(), "Seleccione una empresa", Toast.LENGTH_SHORT).show();
                 }else {
                     if (!spinnerFragment.isAdded()) {
                         addFragmentClient(spinnerFragment, fragmentManager);
                     }

                     if (spinnerFragment.isAdded()){
                         removeFragmentClient(fragmentManager);
                     }
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addFragmentClient(FragmentDateSelector spinnerFragment, FragmentManager fragmentManager){

        Bundle bundle = new Bundle();
        bundle.putString("CompanySelected", CompanySelected);
        spinnerFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_spinner_client_container, spinnerFragment);
        fragmentTransaction.commit();
    }

    private void removeFragmentClient(FragmentManager fragmentManager){

        FragmentDateSelector spinnerFragment = new FragmentDateSelector();

        spinnerFragment.onDestroyView();

        addFragmentClient(spinnerFragment, fragmentManager);
    }
}
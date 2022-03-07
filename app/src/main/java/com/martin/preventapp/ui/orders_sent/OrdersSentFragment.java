package com.martin.preventapp.ui.orders_sent;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentOrdersSentBinding;
import com.martin.preventapp.databinding.FragmentSpinnerSearchableClientBinding;
import com.martin.preventapp.firebase.Company;
import com.martin.preventapp.ui.orders_sent.fragment_orders.OrdersFragment;

import java.util.Calendar;

public class OrdersSentFragment extends Fragment {

    private OrdersSentViewModel ordersSentViewModel;
    private FragmentOrdersSentBinding binding;

    private String CompanySelected = "";
    private String SelectedClient;
    private String DateSelected = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersSentViewModel =
                new ViewModelProvider(this).get(OrdersSentViewModel.class);

        binding = FragmentOrdersSentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner Client Fragment
        FragmentSpinnerSearchableClient spinnerFragment = new FragmentSpinnerSearchableClient();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        //Spinner Company

        Company CompanyList = new Company();
        ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, CompanyList.companyList(root));
        binding.companyAutoCompleteTextView.setAdapter(arrayAdapter);

        binding.companyAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CompanySelected = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(root.getContext(), CompanySelected, Toast.LENGTH_SHORT).show();

                if(CompanySelected == ""){
                    Toast.makeText(root.getContext(), "Seleccione un proveedor", Toast.LENGTH_SHORT).show();
                }else {

                    if (!spinnerFragment.isAdded()) {
                        addFragmentClient(spinnerFragment, fragmentManager);
                    }

                    if (spinnerFragment.isAdded()){
                        removeFragmentClient(fragmentManager);
                    }
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

    private void addFragmentClient(FragmentSpinnerSearchableClient spinnerFragment, FragmentManager fragmentManager){

        Bundle bundle = new Bundle();
        bundle.putString("CompanySelected", CompanySelected);
        spinnerFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_spinner_client_container, spinnerFragment);
        fragmentTransaction.commit();
    }

    private void removeFragmentClient(FragmentManager fragmentManager){

        FragmentSpinnerSearchableClient spinnerFragment = new FragmentSpinnerSearchableClient();

        spinnerFragment.onDestroyView();

        addFragmentClient(spinnerFragment, fragmentManager);
    }
}


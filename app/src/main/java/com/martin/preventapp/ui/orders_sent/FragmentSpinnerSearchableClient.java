package com.martin.preventapp.ui.orders_sent;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentDateSelectorBinding;
import com.martin.preventapp.ui.orders_sent.fragment_orders.OrdersFragment;

import java.util.Calendar;
import java.util.Objects;

public class FragmentSpinnerSearchableClient extends Fragment {

    private FragmentDateSelectorBinding binding;
    private String CompanySelected = "";
    private String SelectedClient;
    public String DateSelected = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentDateSelectorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bundle from Orders Sent Fragment

        Bundle bundle = this.getArguments();

        if(bundle != null){
            CompanySelected = bundle.getString("CompanySelected");
        }

        //Fragment layout Orders
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        OrdersFragment ordersFragment = new OrdersFragment();

        //EditText for DatePicker

        EditText etPlannedDate = root.findViewById(R.id.etPlannedDate);

        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(root.getContext(), R.style.DatePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        DateSelected = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                        etPlannedDate.setText(DateSelected);
                        if(!ordersFragment.isAdded()) {
                            addFragmentOrders(fragmentManager, ordersFragment, etPlannedDate.getText().toString(), SelectedClient);
                        }else {
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(ordersFragment).commit();
                            OrdersFragment ordersFragment1 = new OrdersFragment();
                            addFragmentOrders(fragmentManager, ordersFragment1, etPlannedDate.getText().toString(), SelectedClient);
                        }
                    }
                }, year, month, day);
                picker.show();
            }
        });

        return root;
    }

    public void onDestroyView(){
        super.onDestroyView();
    }

    private void addFragmentOrders(FragmentManager fragmentManager, Fragment ordersFragment, String DateSelected, String SelectedClient){

        Toast.makeText(getContext(), SelectedClient, Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("DateSelected", DateSelected);
        bundle.putString("CompanySelected", CompanySelected);
        ordersFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_orders_container, ordersFragment);
        fragmentTransaction.commit();
    }

}
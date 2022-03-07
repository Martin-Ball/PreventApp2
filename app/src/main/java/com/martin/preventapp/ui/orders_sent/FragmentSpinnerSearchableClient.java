package com.martin.preventapp.ui.orders_sent;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentSpinnerSearchableClientBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.ui.new_order.AddNewClient;
import com.martin.preventapp.ui.orders_sent.fragment_orders.OrdersFragment;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Calendar;

public class FragmentSpinnerSearchableClient extends Fragment {

    private FragmentSpinnerSearchableClientBinding binding;
    private String CompanySelected = "";
    private String SelectedClient;
    private String DateSelected = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSpinnerSearchableClientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bundle from Orders Sent Fragment

        Bundle bundle = this.getArguments();

        if(bundle != null){
            CompanySelected = bundle.getString("CompanySelected");
        }

        Clients clients = new Clients();

        //Fragment layout Orders
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        OrdersFragment ordersFragment = new OrdersFragment();

        //Spinner Searchable client

        SearchableSpinner spinnerClient = root.findViewById(R.id.spinner_searchable_new_client);

        //new client

        ArrayAdapter<String> adapterNewClientSelector = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_list_item_1, clients.clientlist(root, CompanySelected));
        spinnerClient.setAdapter(adapterNewClientSelector);
        spinnerClient.setTitle("Seleccione un cliente");
        spinnerClient.setPositiveButton("CANCELAR");

        spinnerClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    AddNewClient newClient = new AddNewClient();
                    newClient.newClient(root, CompanySelected);
                } else {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    SelectedClient = sNumber;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //EditText for DatePicker

        EditText etPlannedDate = (EditText) root.findViewById(R.id.etPlannedDate);
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
                            addFragmentOrders(ordersFragment, fragmentManager, DateSelected);
                        }

                        if (ordersFragment.isAdded()){
                            removeFragmentOrders(fragmentManager, DateSelected);
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

    private void addFragmentOrders(OrdersFragment ordersFragment, FragmentManager fragmentManager, String DateSelected){
        Bundle bundle = new Bundle();
        bundle.putString("DateSelected", DateSelected);
        ordersFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_orders_container, ordersFragment);
        fragmentTransaction.commit();
    }

    private void removeFragmentOrders(FragmentManager fragmentManager, String DateSelected){

        OrdersFragment ordersFragment = new OrdersFragment();

        ordersFragment.onDestroyView();

        addFragmentOrders(ordersFragment, fragmentManager, DateSelected);
    }
}
package com.martin.preventapp.ui.new_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.ui.AddNewClient;
import com.martin.preventapp.ui.recyclerView.CardViewOrder;
import com.martin.preventapp.ui.recyclerView.CardViewOrderAdapter;
import com.martin.preventapp.ui.recyclerView.InstanceNewProduct;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class NewOrderFragment extends Fragment {

    private NewOrderViewModel newOrderViewModel;
    private FragmentNewOrderBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newOrderViewModel =
                new ViewModelProvider(this).get(NewOrderViewModel.class);

        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //spinner searchable
        SearchableSpinner spinnerClient = root.findViewById(R.id.spinner_searchable_new_client);
        TextView clientNewOrder = root.findViewById(R.id.client_new_order);

        //new client
        Clients clients = new Clients();

        ArrayAdapter<String> adapterNewClientSelector = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1,clients.clientlist());
        spinnerClient.setAdapter(adapterNewClientSelector);
        spinnerClient.setTitle("Seleccione un cliente");
        spinnerClient.setPositiveButton("CANCELAR");

        //firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Clients");

        spinnerClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                {
                    AddNewClient newClient = new AddNewClient();
                    newClient.newClient(root);
                }
                else
                {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    clientNewOrder.setText("Nuevo pedido para el cliente: \n" + sNumber);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //new products CardView
        InstanceNewProduct newProduct = new InstanceNewProduct();
        List items = new ArrayList();

        //spinner searchable
        SearchableSpinner spinnerNewProduct = root.findViewById(R.id.spinner_new_product_searchable);

        //products

        ArrayAdapter<String> adapterNewProductSelector = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1,clients.clientlist());
        spinnerNewProduct.setAdapter(adapterNewProductSelector);
        spinnerNewProduct.setTitle("Seleccione un cliente");
        spinnerNewProduct.setPositiveButton("CANCELAR");

        //firebase
        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("Clients");

        spinnerNewProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                {
                    AddNewClient newClient = new AddNewClient();
                    newClient.newClient(root);
                }
                else
                {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    items.add(new CardViewOrder(sNumber));
                    newProduct.cardViewNewProduct(items, root);

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
}
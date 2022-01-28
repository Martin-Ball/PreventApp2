package com.martin.preventapp.ui.new_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.firebase.OrderDone;
import com.martin.preventapp.firebase.Products;
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
    private String selectedClient = " ";

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
                    clientNewOrder.setText("Nuevo pedido para el cliente: " + sNumber);
                    selectedClient = sNumber;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //new products CardView
        InstanceNewProduct newProduct = new InstanceNewProduct();
        List items = new ArrayList();

        //spinner searchable
        SearchableSpinner spinnerNewProduct = root.findViewById(R.id.spinner_new_product_searchable);

        //products
        Products products = new Products();

        ArrayAdapter<String> adapterNewProductSelector = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1,products.productlist());
        spinnerNewProduct.setAdapter(adapterNewProductSelector);
        spinnerNewProduct.setTitle("Seleccione un producto");
        spinnerNewProduct.setPositiveButton("CANCELAR");


        spinnerNewProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {}
                else
                {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    items.add(new CardViewOrder(sNumber, 2));
                    newProduct.cardViewNewProduct(items, root);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //button finish

        Button finishOrder = root.findViewById(R.id.order_done);

        finishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDone orderDone = new OrderDone();

                orderDone.orderDone(items, selectedClient, binding.ordersRecycler);
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
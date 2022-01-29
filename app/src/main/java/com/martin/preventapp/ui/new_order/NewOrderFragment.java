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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.firebase.OrderDone;
import com.martin.preventapp.firebase.Products;
import com.martin.preventapp.ui.new_order.recyclerView.CardViewOrder;
import com.martin.preventapp.ui.new_order.recyclerView.CardViewOrderAdapter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class NewOrderFragment extends Fragment {

    private NewOrderViewModel newOrderViewModel;
    private FragmentNewOrderBinding binding;
    private String selectedClient = " ";

    private ArrayList<CardViewOrder> arrayProducts;

    private RecyclerView mRecyclerView;
    private CardViewOrderAdapter CardViewProductsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        //initial ArrayList
        arrayProducts = new ArrayList<>();

        ArrayAdapter<String> adapterNewClientSelector = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, clients.clientlist());
        spinnerClient.setAdapter(adapterNewClientSelector);
        spinnerClient.setTitle("Seleccione un cliente");
        spinnerClient.setPositiveButton("CANCELAR");

        spinnerClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    AddNewClient newClient = new AddNewClient();
                    newClient.newClient(root);
                } else {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    clientNewOrder.setText("Nuevo pedido para el cliente: " + sNumber);
                    selectedClient = sNumber;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //spinner searchable
        SearchableSpinner spinnerNewProduct = root.findViewById(R.id.spinner_new_product_searchable);

        //products
        Products products = new Products();

        ArrayAdapter<String> adapterNewProductSelector = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, products.productlist());
        spinnerNewProduct.setAdapter(adapterNewProductSelector);
        spinnerNewProduct.setTitle("Seleccione un producto");
        spinnerNewProduct.setPositiveButton("CANCELAR");


        spinnerNewProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                } else {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    arrayProducts.add(new CardViewOrder(sNumber, "0"));
                    buildRecyclerView(root);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //button finish

        Button finishOrder = root.findViewById(R.id.order_done);

        finishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDone orderDone = new OrderDone();

                orderDone.orderDone(arrayProducts, selectedClient, binding.ordersRecycler);
            }
        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void removeItem(int position) {
        arrayProducts.remove(position);
        CardViewProductsAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text) {
        arrayProducts.get(position).changeTextProduct(text);
        CardViewProductsAdapter.notifyItemChanged(position);
    }

    public void addAmountItem(int position) {
        int amountAdd = Integer.parseInt(arrayProducts.get(position).getAmount());
        arrayProducts.get(position).changeTextAmount(Integer.toString(amountAdd + 1));
        CardViewProductsAdapter.notifyItemChanged(position);
    }

    public void removeAmountItem(int position) {
        int amountRemove = Integer.parseInt(arrayProducts.get(position).getAmount());
        if (amountRemove > 0) {
            arrayProducts.get(position).changeTextAmount(Integer.toString(amountRemove - 1));
            CardViewProductsAdapter.notifyItemChanged(position);
        }
    }


    public void buildRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.ordersRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        CardViewProductsAdapter = new CardViewOrderAdapter(arrayProducts);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(CardViewProductsAdapter);

        CardViewProductsAdapter.setOnItemClickListener(new CardViewOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void addButtonClick(int position)
            {
                addAmountItem(position);
            }

            @Override
            public void removeButtonClick(int position)
            {
                removeAmountItem(position);
            }
        });
    }
}
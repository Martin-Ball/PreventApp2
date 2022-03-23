package com.martin.preventapp.ui.new_order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.firebase.Order;
import com.martin.preventapp.firebase.Products;
import com.martin.preventapp.ui.new_order.recyclerView.CardViewOrder;
import com.martin.preventapp.ui.new_order.recyclerView.CardViewOrderAdapter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import java.util.ArrayList;
import java.util.HashMap;

public class NewOrderFragment extends Fragment {

    private NewOrderViewModel newOrderViewModel;
    private FragmentNewOrderBinding binding;
    private String selectedClient;

    private ArrayList<CardViewOrder> arrayCardViewProducts;
    private ArrayList<ArrayList<String>> arrayProducts;

    private RecyclerView mRecyclerView;
    private CardViewOrderAdapter CardViewProductsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String comment;

    //Orders
    private HashMap<String, Object> ProductAndAmount = new HashMap<>();
    private HashMap<String, HashMap <String, Object>> ProductsOrders = new HashMap<>();

    //Clients
    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> Client = new HashMap<>();
    private HashMap<String, String> ClientInfo = new HashMap<>();

    //Products
    private ArrayList<String> ProductList = new ArrayList<>();

    //
    private String CompanySelected = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newOrderViewModel =
                new ViewModelProvider(this).get(NewOrderViewModel.class);

        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            CompanySelected = bundle.get("CompanySelected").toString();
            Toast.makeText(root.getContext(), bundle.get("CompanySelected").toString(), Toast.LENGTH_LONG).show();
        }


        //spinner searchable
        SearchableSpinner spinnerClient = root.findViewById(R.id.spinner_searchable_new_client);
        TextView clientNewOrder = root.findViewById(R.id.client_new_order);

        //new client
        Clients clients = new Clients();

        //initial ArrayList
        arrayCardViewProducts = new ArrayList<>();

        arrayProducts = new ArrayList<>();

        ArrayAdapter<String> adapterNewClientSelector = new ArrayAdapter<>(root.getContext(),
                                                                            android.R.layout.simple_list_item_1,
                                                                            clients.clientlist(root,
                                                                            CompanySelected));
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

        ProductList = products.productlist(root, CompanySelected);

        ArrayAdapter<String> adapterNewProductSelector = new ArrayAdapter<>(root.getContext(),
                                                                            android.R.layout.simple_list_item_1,
                                                                            ProductList);
        spinnerNewProduct.setAdapter(adapterNewProductSelector);
        spinnerNewProduct.setTitle("Seleccione un producto");
        spinnerNewProduct.setPositiveButton("CANCELAR");


        spinnerNewProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){

                } else if (i == 1) {
                    AddNewProduct addNewProduct = new AddNewProduct();
                    addNewProduct.newProduct(root, CompanySelected, ProductList);
                } else {
                    String productSelected = adapterView.getItemAtPosition(i).toString();
                    arrayCardViewProducts.add(new CardViewOrder(productSelected, "0"));

                    ArrayList<String> productAndAmount = new ArrayList<>();

                    //add product and amount into ArrayList
                    productAndAmount.add(0,productSelected);
                    productAndAmount.add(1,"0");

                    //add product and amount into array
                    arrayProducts.add(productAndAmount);

                    //build Recycler View with CardViews
                    buildRecyclerView(root);
                }
                spinnerNewProduct.setSelection(0);
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

                if(selectedClient == null) {
                    Toast.makeText(root.getContext(), "Seleccione un cliente", Toast.LENGTH_LONG).show();
                }else if (arrayProducts.isEmpty()){
                    Toast.makeText(root.getContext(), "Seleccione productos", Toast.LENGTH_LONG).show();
                }else {
                    alertDialogComments(root);
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


    public void removeItem(int position) {
        arrayCardViewProducts.remove(position);
        CardViewProductsAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text) {
        arrayCardViewProducts.get(position).changeTextProduct(text);
        CardViewProductsAdapter.notifyItemChanged(position);
    }

    public void addAmountItem(int position) {
        int amountAdd = Integer.parseInt(arrayCardViewProducts.get(position).getAmount());
        arrayCardViewProducts.get(position).changeTextAmount(Integer.toString(amountAdd + 1));
        CardViewProductsAdapter.notifyItemChanged(position);

        ArrayList<String> productAndAmount = new ArrayList<>();

        productAndAmount.add(0, arrayCardViewProducts.get(position).getProduct());
        productAndAmount.add(1, Integer.toString(amountAdd + 1));

        arrayProducts.set(position, productAndAmount);
    }

    public void removeAmountItem(int position) {
        int amountRemove = Integer.parseInt(arrayCardViewProducts.get(position).getAmount());
        if (amountRemove > 0) {
            arrayCardViewProducts.get(position).changeTextAmount(Integer.toString(amountRemove - 1));
            CardViewProductsAdapter.notifyItemChanged(position);

            ArrayList<String> productAndAmount = new ArrayList<>();

            productAndAmount.add(0, arrayCardViewProducts.get(position).getProduct());
            productAndAmount.add(1, Integer.toString(amountRemove - 1));

            arrayProducts.set(position, productAndAmount);
        }
    }


    public void buildRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.ordersRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        CardViewProductsAdapter = new CardViewOrderAdapter(arrayCardViewProducts);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(CardViewProductsAdapter);

        CardViewProductsAdapter.setOnItemClickListener(new CardViewOrderAdapter.OnItemClickListener() {

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

    private String alertDialogComments(View root)
    {
        LayoutInflater li = LayoutInflater.from(root.getContext());
        View promptsView = li.inflate(R.layout.dialog_comment_finish_button, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(root.getContext());

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);


        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        comment = userInput.getText().toString();
                        Toast.makeText(promptsView.getContext(), "Comentario agregado al pedido", Toast.LENGTH_SHORT).show();
                        Order order = new Order();
                        order.orderDone(arrayCardViewProducts, arrayProducts, CompanySelected, ProductsOrders, selectedClient, binding.ordersRecycler, comment, root);
                    }
                })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                comment = " ";
                            }
                        })
                .setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        return comment;
    }


    private void uploadClients(){

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        //user:
        //{"Clients"={"ALBRECHT CARINA 2"={"COD"=xx, "Street Address"=xx, "Fantasy Name"=xx, "CUIT"=xx}}}

        //Clients:
        //{"ALBRECHT CARINA 2"={"COD"=xx, "Street Address"=xx, "Fantasy Name"=xx, "CUIT"=xx}}

        //ALBRECHT CARINA 2:
        //{"COD"=690, "Street Address"=xx, "Fantasy Name"=xx, "CUIT"=xx}

        //When i use get("COD") the result is 690


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();
                Client = (HashMap<String, Object>) User.get("Clients");
                ClientInfo = (HashMap<String, String>) Client.get("ALBRECHT CARINA 2");

                //binding.clientNewOrder.setText(ClientInfo.get("CUIT"));
            }
            });

    }

    private void uploadList(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        //user:
        //{"Clients"={"ALBRECHT CARINA 2"={"COD"=xx, "Street Address"=xx, "Fantasy Name"=xx, "CUIT"=xx}}}

        //Clients:
        //{"ALBRECHT CARINA 2"={"COD"=xx, "Street Address"=xx, "Fantasy Name"=xx, "CUIT"=xx}}

        //ALBRECHT CARINA 2:
        //{"COD"=690, "Street Address"=xx, "Fantasy Name"=xx, "CUIT"=xx}

        //When i use get("COD") the result is 690


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();
                Client = (HashMap<String, Object>) User.get("List");
                ClientInfo = (HashMap<String, String>) Client.get("ALBRECHT CARINA 2");

                //binding.clientNewOrder.setText(ClientInfo.get("CUIT"));
            }
        });
    }

}
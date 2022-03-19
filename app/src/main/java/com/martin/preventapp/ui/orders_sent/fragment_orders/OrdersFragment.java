package com.martin.preventapp.ui.orders_sent.fragment_orders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentOrdersBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdersFragment extends Fragment {

    private @NonNull FragmentOrdersBinding binding;
    private RecyclerViewAdapterOrders adapter;

    //private HashMap<String, Object> User = new HashMap<>();
    /*private HashMap<String, Object> Company = new HashMap<>();
    private HashMap<String, Object> Client = new HashMap<>();
    private HashMap<String, Object> Date = new HashMap<>();
    private HashMap<String, Object> OrdersAndComment = new HashMap<>();
    private ArrayList<String> ProductAndAmount = new ArrayList<>();
    private String Comment;*/

    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> Orders = new HashMap<>();
    private HashMap<String, Object> Company = new HashMap<>();
    private HashMap<String, Object> Date = new HashMap<>();
    private  HashMap<String, Object> Client = new HashMap<>();
    private HashMap<String, Object> Hour = new HashMap<>();
    private ArrayList<String> ProductAndAmount = new ArrayList<>();

    String Comment = "";

    private String CompanySelected = "";
    private String ClientSelected = "";
    private String DateSelected = "";

    private RecyclerView recyclerViewOrders;

    private ArrayList<InfoOrders> TextOrders;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            DateSelected = bundle.getString("DateSelected");
            CompanySelected = bundle.getString("CompanySelected");
        }

        TextView TVDateOrders = root.findViewById(R.id.TVDateOrders);
        //Toast.makeText(root.getContext(), ordersSentFragment.DateSelected, Toast.LENGTH_SHORT).show();
        //TVDateOrders.setText("Pedidos de la fecha: " + DateSelected + "\nEmpresa: " + CompanySelected);

        // Orders --> Company --> Date --> Client --> Hour --> Product
        //                                                 |--> Amount

        //Orders:  [HASHMAP]
        //{"Orders"={"Ideas Gastronomicas"={"19-02-2022"={"12"={"20:26"={{"Product6", "4"}, comment=""}}}}}

        //Company:  [HASHMAP]
        //{"Ideas Gastronomicas"={"19-02-2022"={"12"={"20:26"={{"Product6", "4"}, comment=""}}}}

        //19-02-2022:  [HASHMAP]
        //{"19-02-2022"={"12"={"20:26"={{"Product6", "4"}, comment=""}}}

        //12  [HASHMAP]
        //{"20:26"={{"Product6", "4"}, comment=""}}

        //20:26:  [ArrayList]
        //{{"Product6", "4"}, comment=""}

        //When i use get(0).get(0) the result is Product6

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();
                Orders = (HashMap<String, Object>) User.get("Orders");
                Company = (HashMap<String, Object>) Orders.get(CompanySelected);
                Date = (HashMap<String, Object>) Company.get(DateSelected);
                if(Date == null){
                    Toast.makeText(root.getContext(), "No hay pedidos enviados en la fecha seleccionada", Toast.LENGTH_SHORT).show();
                }else {
                    Client = (HashMap<String, Object>) Date.get("CLIENTE NUTRIFRESCA");
                    //Date selected on calendar fragment
                    Hour = (HashMap<String, Object>) Client.get("00:59");
                    ProductAndAmount = (ArrayList<String>) Hour.get("2");
                    Comment = Hour.get("comment").toString();
                }

                //TVDateOrders.setText("Producto: " + ProductAndAmount.get(0) + "\nCantidad: " + ProductAndAmount.get(1) + "\nComentario: " + Comment);
                //TVDateOrders.setText(ProductAndAmount.get(0) + "Comentario: " + Comment);
            }
        });

        // initializing our variables.
        recyclerViewOrders = root.findViewById(R.id.rvOrders);

        EditText editText = root.findViewById(R.id.ETDate);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // calling method to
        // build recycler view.
        buildRecyclerView();

        adapter = new RecyclerViewAdapterOrders(TextOrders, root.getContext());

        adapter.setClickListener(new RecyclerViewAdapterOrders.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(root.getContext(), "Tocaste el item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(root.getContext());
        recyclerViewOrders.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        recyclerViewOrders.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        recyclerViewOrders.setAdapter(adapter);


        // Inflate the layout for this fragment
        return root;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<InfoOrders> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (InfoOrders item : TextOrders) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getClient().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

        // below line we are creating a new array list
        TextOrders = new ArrayList<>();

        // below line is to add data to our array list.
        TextOrders.add(new InfoOrders(CompanySelected, ClientSelected, DateSelected));
        TextOrders.add(new InfoOrders(CompanySelected, ClientSelected, DateSelected));
        TextOrders.add(new InfoOrders(CompanySelected, ClientSelected, DateSelected));
        TextOrders.add(new InfoOrders(CompanySelected, ClientSelected, DateSelected));
        TextOrders.add(new InfoOrders(CompanySelected, ClientSelected, DateSelected));
        TextOrders.add(new InfoOrders("JAVA", "JAVA Self Paced Course", "dasd"));
        TextOrders.add(new InfoOrders("C++", "C++ Self Paced Course", "dasd"));
        TextOrders.add(new InfoOrders("Python", "Python Self Paced Course", "dasd"));
        TextOrders.add(new InfoOrders("Fork CPP", "Fork CPP Self Paced Course", "dasd"));
    }

}
package com.martin.preventapp.ui.orders_sent.fragment_orders;

import android.content.Context;
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
import com.martin.preventapp.firebase.Order;
import com.martin.preventapp.ui.orders_sent.FragmentSpinnerSearchableClient;
import com.martin.preventapp.ui.orders_sent.OrdersSentFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrdersFragment extends Fragment {

    private @NonNull FragmentOrdersBinding binding;
    private RecyclerViewAdapterOrders adapter;

    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> Company = new HashMap<>();
    private HashMap<String, Object> Client = new HashMap<>();
    private HashMap<String, Object> Date = new HashMap<>();
    private HashMap<String, Object> OrdersAndComment = new HashMap<>();
    private ArrayList<String> ProductAndAmount = new ArrayList<>();
    private String Comment;

    private String CompanySelected = "Ideas Gastronómicas";
    private String ClientSelected = "14";
    private String DateSelected = "22/10/22";

    private RecyclerView recyclerViewOrders;

    private ArrayList<InfoOrders> TextOrders;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            DateSelected = bundle.getString("DateSelected");
        }

        TextView TVDateOrders = root.findViewById(R.id.TVDateOrders);
        //Toast.makeText(root.getContext(), ordersSentFragment.DateSelected, Toast.LENGTH_SHORT).show();
        TVDateOrders.setText("Pedidos de la fecha: " + DateSelected);

        //User:  [HASHMAP]
        //{"Orders"={"Ideas Gastronomicas"={"12"={"19-02-2022 20:26"={"1"={"Product6", "4"}}}}}}

        //Company:  [HASHMAP]
        //{"Ideas Gastronomicas"={"12"={"19-02-2022 20:26"={"1"={"Product6", "4"}}}}}

        //12:  [HASHMAP]
        //{"19-02-2022 20:26"={"1"={"Product6", "4"}, comment=""}}}

        //19-02-2022 20:26:  [HASHMAP]
        //{"1"={"Product6", "4"}}

        //1:  [ArrayList]
        //{"Product6", "4"}

        //When i use get(0) the result is Product6

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();
                Company = (HashMap<String, Object>) User.get("Orders");
                //Selected Company
                Client = (HashMap<String, Object>) Company.get("Ideas Gastronómicas");
                //Client Selected
                Date = (HashMap<String, Object>) Client.get("12");
                //Date selected on calendar fragment
                OrdersAndComment = (HashMap<String, Object>) Date.get("19-02-202220:26");
                Comment = OrdersAndComment.get("comment").toString();
                ProductAndAmount = (ArrayList<String>) OrdersAndComment.get("2");

                //tv.setText("Producto: " + ProductAndAmount.get(0) + "\nCantidad: " + ProductAndAmount.get(1));

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

        // initializing our adapter class.
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
            if (item.getCompany().toLowerCase().contains(text.toLowerCase())) {
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
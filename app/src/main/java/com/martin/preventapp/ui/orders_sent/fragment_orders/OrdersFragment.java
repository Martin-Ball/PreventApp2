package com.martin.preventapp.ui.orders_sent.fragment_orders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> Company = new HashMap<>();
    private HashMap<String, Object> Client = new HashMap<>();
    private HashMap<String, Object> Date = new HashMap<>();
    private HashMap<String, Object> OrdersAndComment = new HashMap<>();
    private ArrayList<String> ProductAndAmount = new ArrayList<>();
    private String Comment;

    private String DateSelected = "";

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            DateSelected = bundle.getString("DateSelected");
        }

        TextView TVDateOrders = root.findViewById(R.id.TVDateOrders);
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

                // data to populate the RecyclerView with
                ArrayList<String> animalNames = new ArrayList<>();

                animalNames.clear();

                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");
                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");
                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");
                animalNames.add("Horse");
                animalNames.add("Cow");
                animalNames.add("Camel");
                animalNames.add("Sheep");
                animalNames.add("Goat");

                // set up the RecyclerView

                recyclerView = root.findViewById(R.id.rv2);
                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                adapter = new RecyclerViewAdapterOrders(root.getContext(), animalNames);
                adapter.setClickListener(new RecyclerViewAdapterOrders.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(), "Tocaste el item: " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return root;
    }

    public void onDestroyView(){
        super.onDestroyView();
    }
}
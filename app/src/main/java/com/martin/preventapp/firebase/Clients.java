package com.martin.preventapp.firebase;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.martin.preventapp.ui.new_order.NewOrderFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clients {

    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String,Object> Client = new HashMap<>();
    private ArrayList<String> List = new ArrayList<String>();

    public ArrayList<String> clientlist (View view)
    {
        List.add("+ Agregar nuevo cliente");

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
                List.addAll(Client.keySet());
            }
        });

        return List;
    }

    public void addNewClient (String name, String CUIT, String StreetAddress, String FantasyName, View view)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        // Create a new user with a CODE, Street Address, Fantasy Name and CUIT
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> Client = new HashMap<>();
        HashMap<String, Object> InfoClient = new HashMap<>();

        InfoClient.put("CUIT", CUIT);
        InfoClient.put("Fantasy Name", FantasyName);
        InfoClient.put("Street Address", StreetAddress);

        Client.put(name, InfoClient);

        User.put("Clients", Client);

        try {
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(User, SetOptions.merge());

            Toast.makeText(view.getContext(), "Cliente agregado", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(view.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
    }
    }

    public void addListOfClients()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        // Create a new user with a CODE, Street Address, Fantasy Name and CUIT
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> Client = new HashMap<>();
        HashMap<String, Object> InfoClient = new HashMap<>();

        InfoClient.put("CUIT", "CUIT");
        InfoClient.put("Fantasy Name", "FantasyName");
        InfoClient.put("Street Address", "StreetAddress");

        Client.put("name", InfoClient);

        User.put("Clients", Client);
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(User, SetOptions.merge());
    }
}

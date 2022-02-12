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

public class Clients {

    public ArrayList<String> clientlist ()
    {
        ArrayList<String> numberList = new ArrayList<>();

        numberList.add("+ Agregar nuevo cliente");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Clients");

        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    numberList.add(postSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...
            }
        });


        return numberList;
    }

    public void addNewClient (String name, String CUIT, String StreetAddress, String FantasyName, View view)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        // Create a new user with a CODE, Street Address, Fantasy Name and CUIT
        HashMap<String, Object> user = new HashMap<>();
        HashMap<String, Object> Client = new HashMap<>();
        HashMap<String, Object> InfoClient = new HashMap<>();

        InfoClient.put("CUIT", CUIT);
        InfoClient.put("Fantasy Name", FantasyName);
        InfoClient.put("Street Address", StreetAddress);

        Client.put(name, InfoClient);

        user.put("Clients", Client);

        try {
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(user, SetOptions.merge());

            Toast.makeText(view.getContext(), "Cliente agregado", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(view.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
    }
    }
}

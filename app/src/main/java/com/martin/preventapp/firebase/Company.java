package com.martin.preventapp.firebase;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Company {

    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> List = new HashMap<>();
    private ArrayList<String> CompanyList = new ArrayList<>();

    public ArrayList<String> companyList (View view) {

        CompanyList.add(0, "Seleccione un proveedor");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();

                if(User.get("List") != "") {
                    List = (HashMap<String, Object>) User.get("List");
                    CompanyList.addAll(List.keySet());
                    Log.i("Current User: ", CompanyList.toString());
                }
            }
        });

        return CompanyList;
    }

    public void addCompany (String CompanyName, View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        // Create a new product
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> Product = new HashMap<>();
        ArrayList<String> options = new ArrayList<>();

        options.add("Clients");
        options.add("List");
        options.add("Orders");

        Product.put(CompanyName, " ");

        for(int i=0; i<=2; i++) {
            User.put(options.get(i), Product);

            try {
                // Add a new document with ID for user
                db.collection("users").document(currentFirebaseUser.getUid()).set(User, SetOptions.merge());
                Toast.makeText(view.getContext(), "Proveedor agregado", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
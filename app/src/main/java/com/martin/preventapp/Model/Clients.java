package com.martin.preventapp.Model;
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
import java.util.HashMap;

public class Clients {

    private HashMap <String, Object> User = new HashMap<>();
    private HashMap <String, Object> Company = new HashMap<>();
    private HashMap <String,Object> Client = new HashMap<>();
    private ArrayList<String> List = new ArrayList<String>();

    public ArrayList<String> clientlist (View view, String CompanySelected)
    {
        List.add("Seleccionar el cliente");
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
                Company = (HashMap<String, Object>) User.get("Clients");
                Client = (HashMap<String, Object>) Company.get(CompanySelected);
                List.addAll(Client.keySet());
            }
        });

        return List;
    }

    public void addNewClient (String CompanySelected, String Name, String CUIT, String StreetAddress, String FantasyName, View view)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        // Create a new user with a CODE, Street Address, Fantasy Name and CUIT
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> Company = new HashMap<>();
        HashMap<String, Object> Client = new HashMap<>();
        HashMap<String, Object> InfoClient = new HashMap<>();

        InfoClient.put("CUIT", CUIT);
        InfoClient.put("Fantasy Name", FantasyName);
        InfoClient.put("Street Address", StreetAddress);

        Client.put(Name, InfoClient);

        Company.put(CompanySelected, Client);

        User.put("Clients", Company);

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
        HashMap<String, Object> Company = new HashMap<>();
        HashMap<String, Object> Client = new HashMap<>();
        HashMap<String, Object> InfoClient = new HashMap<>();

        InfoClient.put("CUIT", "22255522");
        InfoClient.put("Fantasy Name", "FantasyName");
        InfoClient.put("Street Address", "StreetAddress");

        for(int i=0;i<=20; i++) {
            Client.put(Integer.toString(i), InfoClient);
        }

        Company.put("Nutrifresca", Client);

        User.put("Clients", Company);
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(User, SetOptions.merge());
    }

    public void setClientList (String CompanySelected, HashMap<String, Object> ClientList, View root){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Create a new product
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> Company = new HashMap<>();

        Company.put(CompanySelected, ClientList);

        User.put("Clients", Company);

        try {
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(User, SetOptions.merge());
            Toast.makeText(root.getContext(), "Clientes agregados", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(root.getContext(), "Error: " + e, Toast.LENGTH_LONG).show();
            Log.i("Firebase: ", e.getMessage());
        }
    }
}

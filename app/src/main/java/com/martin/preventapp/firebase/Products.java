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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Products {

    //ProductList
    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> List = new HashMap<>();
    private ArrayList<String> Products = new ArrayList<>();

    //NewProduct
    private HashMap<String, Object> User2 = new HashMap<>();
    private HashMap<String, Object> List2 = new HashMap<>();
    private ArrayList<String> ProductsNew = new ArrayList<>();

    public ArrayList<String> productlist (View root)
    {
        Products.add("+ Seleccione un producto");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        //user:
        //{"List"={"Nutrifresca"={"COD", "Street Address", "Fantasy Name", "CUIT"}}}

        //Nutrifresca:
        //{"Nutrifresca"={"COD", "Street Address", "Fantasy Name", "CUIT"}}

        //0:
        //{"COD", "Street Address", "Fantasy Name", "CUIT"}

        //The values its contained on ArrayList, get on 0,1,2...


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();
                List = (HashMap<String, Object>) User.get("List");
                Products.addAll((Collection<? extends String>) List.get("Nutrifresca"));
            }
        });

        return Products;
    }

    public void addNewProduct (String name, View view)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User2 = (HashMap<String, Object>) documentSnapshot.getData();
                List2 = (HashMap<String, Object>) User2.get("List");
                ProductsNew.addAll((Collection<? extends String>) List2.get("Ideas Gastronómicas"));
                //Name of New Product
                ProductsNew.add("12312333333555");

                List2.put(name, ProductsNew);

                User2.put("Clients", List);

                // Add a new document with ID for user
                db.collection("users").document(currentFirebaseUser.getUid()).set(User2, SetOptions.merge());
            }
        });
    }
}

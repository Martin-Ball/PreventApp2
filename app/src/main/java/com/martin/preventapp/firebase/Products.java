package com.martin.preventapp.firebase;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.martin.preventapp.ui.new_order.NewOrderFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Products {

    //ProductList
    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> List = new HashMap<>();
    private ArrayList<String> Products = new ArrayList<>();

    public ArrayList<String> getProductlist(View root, String CompanySelected)
    {
        Products.add("Agregar producto al pedido");
        Products.add("+ Agregar un nuevo producto a la lista");

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
                Products.addAll((Collection<? extends String>) List.get(CompanySelected));
            }
        });

        return Products;
    }

    public void setProductList (String CompanySelected, ArrayList<String> ProductList, View root){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Create a new product
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> Company = new HashMap<>();

        Company.put(CompanySelected, ProductList);

        User.put("List", Company);

        try {
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(User, SetOptions.merge());
            Toast.makeText(root.getContext(), "Producto agregado", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(root.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void addNewProduct (String CompanySelected, String ProductName, View view, ArrayList<String> ProductList)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        // Create a new product
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> Company = new HashMap<>();
        ArrayList<String> Product = new ArrayList<>(ProductList);

        Product.remove(0);
        Product.remove(0);

        Product.add(ProductName);

        Company.put(CompanySelected, Product);

        User.put("List", Company);

        try {
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(User, SetOptions.merge());
            Toast.makeText(view.getContext(), "Producto agregado", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(view.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}

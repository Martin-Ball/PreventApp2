package com.martin.preventapp.firebase;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.martin.preventapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Order {

    private StringBuilder message;

    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> Orders = new HashMap<>();
    private ArrayList<String> Products = new ArrayList<>();

    public void orderDone (ArrayList items,
                           ArrayList<ArrayList<String>> arrayProducts,
                           String CompanySelected,
                           HashMap<String, HashMap<String, Object>> ProductsOrders,
                           String selectedClient,
                           RecyclerView ordersRecycler,
                           String comment, View root) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        String limitText = "===========================\n";



        message = new StringBuilder();

        //Header Builder

        message.append(limitText)
                .append("*Cliente:* " + selectedClient + "\n")
                .append("*Fecha:* " + currentDate + "\n")
                .append("*Lista:* " + CompanySelected + "\n")
                .append("*Productos:* " + arrayProducts.size() + "\n");

        message.append(limitText);


        for (int i = 0; i <= arrayProducts.size() - 1; i++) {
                message.append("\n[-] ").append(arrayProducts.get(i).get(0) + "  [CANTIDAD: " + arrayProducts.get(i).get(1) + "] ")
                        .append("\n");

        }

        message.append("\n" + limitText);

            if(comment != " "){
                message.append("Comentarios: \n" + comment);
                message.append("\n" + limitText);
            }


            whatsapp(root);
            firebase(arrayProducts, currentDate, currentTime, CompanySelected, selectedClient, comment, root);
            //clear recyclerView

            clearRecyclerView(ordersRecycler, items);
    }

    private void whatsapp(View root){
        try{
            // Starting Whatsapp
            // Creating new intent

            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("text/plain");
            intent.setPackage("com.whatsapp");

            // Give your message here
            intent.putExtra(Intent.EXTRA_TEXT, message.toString());

            // Checking whether Whatsapp
            // is installed or not
            root.getContext().startActivity(intent);

        }catch (ActivityNotFoundException ex){
            Toast.makeText(root.getContext(), "Whastapp No Instalado", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebase(ArrayList<ArrayList<String>> arrayProducts, String currentDate, String currentTime, String CompanySelected, String selectedClient, String comment, View root){
        //Upload order in firebase

        HashMap<String, Object> ProductAndAmount = new HashMap<>();

        for(int i=1; i<= arrayProducts.size(); i++){
            ProductAndAmount.put(Integer.toString(i), arrayProducts.get(i-1));
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        // Orders --> Company --> Date --> Client --> Hour --> Product
        //                                                 |--> Amount

        HashMap<String, Object> Orders = new HashMap<>();
        HashMap<String, Object> Company = new HashMap<>();
        HashMap<String, Object> Date = new HashMap<>();
        HashMap<String, Object> Client = new HashMap<>();
        HashMap<String, Object> Hour = new HashMap<>();

        ProductAndAmount.put("comment", comment);

        Hour.put(currentTime, ProductAndAmount);
        Client.put(selectedClient, Hour);
        Date.put(currentDate, Client);
        Company.put(CompanySelected, Date);
        Orders.put("Orders", Company);

        /*Orders.put(currentDate + " " + currentTime, ProductAndAmount);
        Client.put(selectedClient, Orders);
        Company.put(CompanySelected, Client);
        User.put("Orders", Company);*/

        try {
            // Add a new document with ID for user
            db.collection("users").document(currentFirebaseUser.getUid()).set(Orders, SetOptions.merge());
            clearArray(arrayProducts, Hour, Client, Date, Company, Orders);
        }catch(Exception e){
            Toast.makeText(root.getContext(), "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

    private void clearRecyclerView(RecyclerView orderRecycler, ArrayList items) {
        orderRecycler.setAdapter(null);
        items.clear();
    }

    private void clearArray(ArrayList<ArrayList<String>> arrayProducts,
                                HashMap<String, Object> Hour,
                                HashMap<String, Object> Client,
                                HashMap<String, Object> Date,
                                HashMap<String, Object> Company,
                                HashMap<String, Object> Orders) {
        arrayProducts.clear();
        Hour.clear();
        Client.clear();
        Date.clear();
        Company.clear();
        Orders.clear();
    }
}

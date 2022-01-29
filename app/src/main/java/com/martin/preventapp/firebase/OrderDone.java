package com.martin.preventapp.firebase;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.martin.preventapp.ui.new_order.NewOrderFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDone {

    public void orderDone (ArrayList items, ArrayList<ArrayList<String>> arrayProducts, String selectedClient, RecyclerView ordersRecycler, View root) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        //Send order from Whatsapp

        String limitText = "===========================\n";

        StringBuilder message = new StringBuilder();

        //Header Builder

        message.append(limitText)
                .append("*Cliente:* " + selectedClient + "\n")
                .append("*Fecha:* " + currentDate + "\n")
                .append("*Lista:* NUTRIFRESCA\n")
                .append("*Productos:* " + arrayProducts.size() + "\n");

        message.append(limitText);

        for (int i = 0; i <= arrayProducts.size() - 1; i++) {
                message.append("\n[-] ").append(arrayProducts.get(i).get(0) + "  [CANTIDAD: " + arrayProducts.get(i).get(1) + "] ")
                        .append("\n");
        }

        message.append("\n" + limitText);

            //Toast.makeText(root.getContext(), message, Toast.LENGTH_LONG).show();


        // Creating new intent
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        // Give your message here
        intent.putExtra(Intent.EXTRA_TEXT, message.toString());

        // Checking whether Whatsapp
        // is installed or not

        // Starting Whatsapp

        try{
            root.getContext().startActivity(intent);
        }catch (ActivityNotFoundException ex){

            Toast.makeText(root.getContext(), "Whastapp No Instalado", Toast.LENGTH_SHORT).show();

        }


        //Upload order in firebase

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("OrderToSend");

            for (int i = 1; i <= items.size(); i++) {

                mDatabase.child(currentTime + " " + currentDate + " " + selectedClient)
                        .child(Integer.toString(i))
                        .setValue(items.get(i-1));
            }

            clearRecyclerView(ordersRecycler, items);
            clearArray(arrayProducts);


    }

    private void clearRecyclerView(RecyclerView orderRecycler, List items) {
        orderRecycler.setAdapter(null);
        items.clear();
    }

    private void clearArray(ArrayList<ArrayList<String>> arrayProducts)
    {
        arrayProducts.clear();
    }
}

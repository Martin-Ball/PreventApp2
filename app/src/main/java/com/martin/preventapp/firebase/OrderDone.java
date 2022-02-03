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

    private StringBuilder message;

    public void orderDone (ArrayList items, ArrayList<ArrayList<String>> arrayProducts, String selectedClient, RecyclerView ordersRecycler, String comment, View root) {

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        String limitText = "===========================\n";

        message = new StringBuilder();

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

            if(comment != " "){
                message.append("Comentarios: \n" + comment);
                message.append("\n" + limitText);
            }

            whatsapp(root);
            firebase(items, currentDate, currentTime, selectedClient);

            //clear recyclerView

            clearRecyclerView(ordersRecycler, items);
            clearArray(arrayProducts);
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

    private void firebase(ArrayList items, String currentDate, String currentTime, String selectedClient){
        //Upload order in firebase

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("OrderToSend");

        for (int i = 1; i <= items.size(); i++) {

            mDatabase.child(currentTime + " " + currentDate + " " + selectedClient)
                    .child(Integer.toString(i))
                    .setValue(items.get(i-1));
        }
    }

    private void clearRecyclerView(RecyclerView orderRecycler, ArrayList items) {
        orderRecycler.setAdapter(null);
        items.clear();
    }

    private void clearArray(ArrayList<ArrayList<String>> arrayProducts) {
        arrayProducts.clear();
    }
}

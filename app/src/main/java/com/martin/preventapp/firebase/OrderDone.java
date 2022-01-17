package com.martin.preventapp.firebase;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDone {

    public void orderDone (List items, String selectedClient, RecyclerView ordersRecycler) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("OrderToSend");

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

            for (int i = 1; i <= items.size(); i++) {

                mDatabase.child(currentTime + " " + currentDate + " " + selectedClient)
                        .child(Integer.toString(i))
                        .setValue(items.get(i-1));
            }

            clear(ordersRecycler, items);
    }

    private void clear(RecyclerView orderRecycler, List items) {
        orderRecycler.setAdapter(null);
        items.clear();
    }

    public void delete(List items, int numberCV, RecyclerView orderRecycler){
        items.remove(numberCV);
        orderRecycler.setAdapter(null);
    }
}

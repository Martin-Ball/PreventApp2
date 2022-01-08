package com.martin.preventapp.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Clients {

    public ArrayList<String> clientlist ()
    {
        ArrayList<String> numberList = new ArrayList<>();

        /* database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Clientes");*/

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Clientes");

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


        /* // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.child("50").getValue().toString();
                numberList.add(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });*/

        numberList.add("+ Agregar un cliente");
        numberList.add("One");
        numberList.add("Three");
        numberList.add("Four");
        numberList.add("Five");
        numberList.add("Six");
        numberList.add("Seven");
        numberList.add("Eight");
        numberList.add("Nine");
        numberList.add("Ten");
        numberList.add("Three");
        numberList.add("Four");
        numberList.add("Five");
        numberList.add("Six");
        numberList.add("Seven");
        numberList.add("Eight");
        numberList.add("Nine");
        numberList.add("Ten");


        return numberList;
    }
}

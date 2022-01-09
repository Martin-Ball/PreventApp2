package com.martin.preventapp.firebase;
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

    public void addNewClient (String name)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Clients");

        String push=mDatabase.push().getKey();
        mDatabase.child(push).setValue(name);

    }
}

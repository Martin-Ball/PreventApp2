package com.martin.preventapp.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Products {
    public ArrayList<String> productlist ()
    {
        ArrayList<String> numberList = new ArrayList<>();

        numberList.add("+ Seleccione un producto");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Products");

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

    public void addNewProduct (String name)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Products");

        String push=mDatabase.push().getKey();
        mDatabase.child(push).setValue(name);

    }
}

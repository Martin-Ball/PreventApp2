package com.martin.preventapp.firebase;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Company {

    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> List = new HashMap<>();
    private ArrayList<String> CompanyList = new ArrayList<>();

    public ArrayList<String> companyList (View view) {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();
                List = (HashMap<String, Object>) User.get("List");
                CompanyList.addAll(List.keySet());
            }
        });

        return CompanyList;
    }
}

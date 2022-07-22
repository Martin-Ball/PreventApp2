package com.martin.preventapp.firebase.login;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.martin.preventapp.View.MainActivity;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.ActivityLoginBinding;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    //register data

    private String email = "";
    private String psw = "";

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Thread.sleep(2000);
        setTheme(R.style.Theme_Preventapp);
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText emailEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        Button registerButton = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            Toast.makeText(binding.password.getContext(), "Sesion iniciada como: " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            showMainActivity();
        }else{
            Toast.makeText(binding.password.getContext(), "Sin sesiones activas", Toast.LENGTH_SHORT).show();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                psw = passwordEditText.getText().toString();

                if (email != null && psw != null){
                    signIn();
                }else{
                    Toast.makeText(binding.getRoot().getContext(), "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailEditText.getText().toString();
                psw = passwordEditText.getText().toString();

                if (email != null && psw != null){
                    registerUser(binding.getRoot());
                }else{
                    Toast.makeText(binding.getRoot().getContext(), "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn(){
        mAuth.signInWithEmailAndPassword(email,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    showMainActivity();
                }else {
                    Toast.makeText(binding.password.getContext(), "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(View root){

        mAuth.createUserWithEmailAndPassword(email, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   showMainActivity();
                   createUserOnDatabase(root);
               }else {
                   Toast.makeText(binding.password.getContext(), "No se puede registrar el usuario", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void showMainActivity(){
        Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createUserOnDatabase(View root){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        // Create a new user with a first and last name
        HashMap<String, Object> User = new HashMap<>();
        HashMap<String, Object> InfoUser = new HashMap<>();

        InfoUser.put("List", "");
        InfoUser.put("Orders", "");
        InfoUser.put("Clients", "");
        InfoUser.put("Units", "");

        // Add a new document with a generated ID
        db
                .collection("users")
                .document(currentFirebaseUser.getUid())
                .set(InfoUser);

        Toast.makeText(root.getContext(), "Registro de usuario exitoso", Toast.LENGTH_SHORT).show();
    }
}
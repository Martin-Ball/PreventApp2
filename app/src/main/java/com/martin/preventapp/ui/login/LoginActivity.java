package com.martin.preventapp.ui.login;


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
import com.martin.preventapp.MainActivity;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    //register data

    private String email = "";
    private String psw = "";

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText emailEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        Button registerButton = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

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
                    registerUser();
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

    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(email, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   showMainActivity();
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
}
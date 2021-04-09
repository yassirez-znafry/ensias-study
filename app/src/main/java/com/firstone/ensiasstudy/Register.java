package com.firstone.ensiasstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText _name,_email,_password, _confPassword;
    Button registerBtn, gotoLoginBtn;
    FirebaseAuth fAuth;
    // FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        _name   = findViewById(R.id.registerName);
        _email      = findViewById(R.id.registerEmail);
        _password   = findViewById(R.id.registerPassword);
        _confPassword      = findViewById(R.id.confPassword);
        registerBtn= findViewById(R.id.registerBtn);
        gotoLoginBtn   = findViewById(R.id.gotoLogin);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _email.getText().toString().trim();
                String password = _password.getText().toString().trim();
                String name = _name.getText().toString();
                String confPassword    = _confPassword.getText().toString();



                if(TextUtils.isEmpty(name)){
                    _name.setError("Name is Required.");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    _email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    _password.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(confPassword)){
                    _confPassword.setError("Confirm Password");
                    return;
                }

                if(!password.equals(confPassword)){
                    _confPassword.setError("Passwords don't match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });







    }
}
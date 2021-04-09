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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText _email, _password;
    Button loginBtn, createAccountBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_EnsiasStudy);
        setContentView(R.layout.activity_login);

        _email = findViewById(R.id.loginEmail);
        _password = findViewById(R.id.loginPassword);
        fAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.loginbtn);
        createAccountBtn = findViewById(R.id.createAccountBtn);



        // when user clicks on login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = _email.getText().toString().trim();
                String password = _password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    _email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    _password.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    _password.setError("Password Must be >= 6 Characters");
                    return;
                }


                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });




        // when user clicks on create account button
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });




    }
}
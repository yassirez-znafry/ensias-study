package com.firstone.ensiasstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firstone.ensiasstudy.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText _name,_email,_password, _confPassword;
    Spinner _annee, _filiere;
    public String annee, filiere;
    Button registerBtn;
    TextView gotoLogin;
    FirebaseAuth fAuth;
    // FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_EnsiasStudy);
        setContentView(R.layout.activity_register);


        _name   = findViewById(R.id.registerName);
        _email      = findViewById(R.id.registerEmail);
        _password   = findViewById(R.id.registerPassword);
        _confPassword      = findViewById(R.id.confPassword);
        _annee = findViewById(R.id.registerAnnee);
        _filiere = findViewById(R.id.registerFiliere);
        registerBtn= findViewById(R.id.registerBtn);
        gotoLogin   = findViewById(R.id.alreadyHaveAnAccountlink);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        ArrayAdapter<CharSequence> adapterAnnee = ArrayAdapter.createFromResource(this,
                R.array.annee, android.R.layout.simple_spinner_item);
        adapterAnnee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _annee.setAdapter(adapterAnnee);
        _annee.setOnItemSelectedListener(new AnneeSpinnerClass());


        ArrayAdapter<CharSequence> adapterFiliere = ArrayAdapter.createFromResource(this,
                R.array.filiere, android.R.layout.simple_spinner_item);
        adapterAnnee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _filiere.setAdapter(adapterFiliere);
        _filiere.setOnItemSelectedListener(new FiliereSpinnerClass());








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
                        Log.v("succedA", "YES SIIIIIIIIIIR");
                        User user = new User(name, email, password, annee, filiere );

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
                        databaseReference.setValue(user).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("failedX", "hhhhhhhhhhhh");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.v("succedX", "YES SIIIIIIIIIIR");
                            }
                        });

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("failedA", "hhhhhhhhhhhh");
                    }
                });

            }
        });




        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }


    // handling the Annee Spinner
    class AnneeSpinnerClass implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            annee = parent.getItemAtPosition(position).toString();
            _annee.setSelection(position);
            _annee.setPrompt(annee);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    // handling the Filiere Spinner
    class FiliereSpinnerClass implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            filiere = parent.getItemAtPosition(position).toString();
            _filiere.setSelection(position);
            _filiere.setPrompt(filiere);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}
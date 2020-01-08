package com.example.wspinomierz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wspinomierz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password;
    private Button regBtn, cancelBtn;
    private ProgressBar prBar;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(); // root
        //Initialize view contents
        initialize();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                //Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void userRegister() {
        //Init progress bar - przetestować działanie
        prBar.setVisibility(View.VISIBLE);
        //Local login and email vars
        final String em = email.getText().toString();
        final String pass = password.getText().toString();
        //Empty fields
        if(TextUtils.isEmpty(em)) {
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_LONG).show();
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(em, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            writeNewUser(em, mFirebaseAuth.getUid());
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            prBar.setVisibility(View.GONE);
                            finish(); //lub nowy intent
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_LONG).show();
                            prBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initialize() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        cancelBtn = findViewById(R.id.cancel);
        prBar = findViewById(R.id.progressBar);
    }
    private void writeNewUser(String email, String UID){
        User user = new User(email, UID);
        reference.child("users").child(UID).setValue(0);
    }
}

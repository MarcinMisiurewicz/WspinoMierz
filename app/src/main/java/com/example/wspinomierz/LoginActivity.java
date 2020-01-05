package com.example.wspinomierz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn;
    private TextView register;
    private ProgressBar prBar;

    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Get firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        //TODO check userLoggedIn
        if(mFirebaseAuth.getCurrentUser()!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        //Initialize view contents
        initialize();



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userLogin() {
        //Init progress bar - przetestować działanie
        prBar.setVisibility(View.VISIBLE);
        //Local login and email vars
        String em = email.getText().toString();
        String pass = password.getText().toString();
        //Empty fields
        if(TextUtils.isEmpty(em)) {
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_LONG).show();
            return;
        }
        //Try to log in
        mFirebaseAuth.signInWithEmailAndPassword(em, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        prBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Incorrect credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void initialize() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        register = findViewById(R.id.register);
        prBar = findViewById(R.id.progressBar);
    }
}

package com.onfocus.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        progressDialog = new ProgressDialog(this);

        //ataching Listener to button
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // checking if email and password are empty
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        // if validation are ok we will first show a progressbar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user is succesfully registered and logged in
                            // we will start the progile activity here
                            // right now lets display a toast only
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            Toast.makeText(MainActivity.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Could not Register. Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }
        if (view == textViewSignin) {
            //will opern login activity here
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}

package com.example.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserRegistration extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        firebaseAuth = firebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.input_email);
        editTextPassword = findViewById(R.id.input_Password);
        progressDialog = new ProgressDialog(this);
    }

    public void registerUser(View view){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return;
        }
        //if validation are ok

        //we will show progress dialog
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            //user is successfully registered and logged in
                                            //we will start the profile activity here
                                            //right now lets display a toast only
                                            progressDialog.dismiss();
                                            Toast.makeText(UserRegistration.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(UserRegistration.this,"Could not register, please try again",Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });
        startActivity(new Intent(this, MainActivity.class));

    }
}

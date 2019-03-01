package com.sih.teamsquadra.motherandchild;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmailID;
    private EditText editTextPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        /*
        if(firebaseAuth.getCurrentUser() != null){
            //profile Activity
            finish();//close current activity
            startActivity(new Intent(this, UserMain.class));
        }
        */

        buttonRegister = findViewById(R.id.button_register);
        editTextPassword = findViewById(R.id.edittext_password_reg);
        editTextEmailID = findViewById(R.id.edittext_email_reg);
        textViewLogin = findViewById(R.id.textview_login);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    private void registerUser(){
        String id = editTextEmailID.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(id)){
            //id is empty
            Toast.makeText(this,"Please enter User Id",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        //validations are ok, register user
        progressDialog.setMessage("Registering User,please wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(id,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(UserRegistration.this, "User is Registered",Toast.LENGTH_SHORT).show();
                    finish();//close current activity
                    startActivity(new Intent(UserRegistration.this, UserUpdateDetails.class));
                }else {
                    Toast.makeText(UserRegistration.this, "Could not register, Please try again",Toast.LENGTH_SHORT).show();
                }
                }
            });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            //register()
            registerUser();
        } else if(v == textViewLogin){
            //LoginActivity
            finish(); // to close current activity
            startActivity(new Intent(this, LoginUser.class));
        }
    }
}

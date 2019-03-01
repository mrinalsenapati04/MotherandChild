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

public class LoginUser extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmailID;
    private EditText editTextPassword;
    private Button buttonLogIn;
    private TextView textViewRegister;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        FirebaseApp.initializeApp(LoginUser.this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile Activity
            finish();//close current activity
            startActivity(new Intent(LoginUser.this, UserMain.class));
        }

        editTextEmailID = findViewById(R.id.edittext_email);
        editTextPassword = findViewById(R.id.edittext_password);
        buttonLogIn = findViewById(R.id.button_login);
        textViewRegister = findViewById(R.id.textview_register);

        buttonLogIn.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    private void userLogin(){
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
        progressDialog.setMessage("please wait login in process...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(id,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    toastMessage(LoginUser.this, "Login Successful");
                    //start Profile Activity
                    finish();//close current activity
                    startActivity(new Intent(LoginUser.this, UserMain.class));
                }else {
                    toastMessage(LoginUser.this, "Unable to login, Please try again");
                }
                }
            });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogIn){
            userLogin();
        }

        if(v == textViewRegister){
            finish(); // to close current activity
            startActivity(new Intent(this, UserRegistration.class));
        }
    }

    private void toastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
package com.sih.teamsquadra.motherandchild;

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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeRegistration extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmployeeEmailID;
    private EditText editTextEmployeePassword;
    private Button buttonEmployeeRegister;
    private TextView textViewEmployeeLogin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_registration);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        /*
        if(firebaseAuth.getCurrentUser() != null){
            //profile Activity
            finish();//close current activity
            startActivity(new Intent(this, UserMain.class));
        }*/

        buttonEmployeeRegister = findViewById(R.id.button_employee_register);
        editTextEmployeePassword = findViewById(R.id.edittext_employee_password_reg);
        editTextEmployeeEmailID = findViewById(R.id.edittext_employee_email_reg);
        textViewEmployeeLogin = findViewById(R.id.textview_employee_login);

        buttonEmployeeRegister.setOnClickListener(this);
        textViewEmployeeLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    private void registerEmployee(){
        String id = editTextEmployeeEmailID.getText().toString().trim();
        String password = editTextEmployeePassword.getText().toString().trim();

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
                            Toast.makeText(EmployeeRegistration.this, "User is Registered",Toast.LENGTH_SHORT).show();
                            finish();//close current activity
                            startActivity(new Intent(EmployeeRegistration.this, EmployeeUpdateDetails.class));
                        }else {
                            Toast.makeText(EmployeeRegistration.this, "Could not register, Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonEmployeeRegister){
            //register()
            registerEmployee();
        } else if(v == textViewEmployeeLogin){
            //LoginActivity
            finish(); // to close current activity
            startActivity(new Intent(this, LoginEmployee.class));
        }
    }
}
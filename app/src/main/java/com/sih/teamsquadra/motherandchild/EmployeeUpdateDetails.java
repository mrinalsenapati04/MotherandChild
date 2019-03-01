package com.sih.teamsquadra.motherandchild;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeUpdateDetails extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText editTextEmployeeFullName;
    private EditText editTextEmployeeID;
    private EditText editTextEmployeeAge;
    private EditText editTextEmployeeGender;
    private EditText editTextEmployeeAadhar;
    private EditText editTextEmployeePhone;
    private Button buttonEmployeeUpdateDetailsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_update_details);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity
            finish();//close current activity
            startActivity(new Intent(this, LoginEmployee.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Employees");
        editTextEmployeeFullName = findViewById(R.id.edittext_employee_name);
        editTextEmployeeID = findViewById(R.id.edittext_employeeID);
        editTextEmployeeAge = findViewById(R.id.edittext_employee_age);
        editTextEmployeeGender = findViewById(R.id.edittext_employee_gender);
        editTextEmployeeAadhar = findViewById(R.id.edittext_employee_aadhar);
        editTextEmployeePhone = findViewById(R.id.edittext_employee_phone);

        buttonEmployeeUpdateDetailsDB = findViewById(R.id.button_employee_update_details_database);
        buttonEmployeeUpdateDetailsDB.setOnClickListener(this);
    }

    private void saveUserInformation(){
        String name = editTextEmployeeFullName.getText().toString().trim();
        String employeeID = editTextEmployeeID.getText().toString().trim();
        String age = editTextEmployeeAge.getText().toString().trim();
        String gender = editTextEmployeeGender.getText().toString().trim();
        String aadhar = editTextEmployeeAadhar.getText().toString().trim();
        String phone = editTextEmployeePhone.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("type").setValue("employee");
        databaseReference.child(user.getUid()).child("name").setValue(name);
        databaseReference.child(user.getUid()).child("address").setValue(employeeID);
        databaseReference.child(user.getUid()).child("age").setValue(age);
        databaseReference.child(user.getUid()).child("gender").setValue(gender);
        databaseReference.child(user.getUid()).child("aadhar").setValue(aadhar);
        databaseReference.child(user.getUid()).child("phone").setValue(phone);
        Toast.makeText(this," Information Saved ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonEmployeeUpdateDetailsDB){
            saveUserInformation();
            toastMessage(EmployeeUpdateDetails.this, "Updated Successfully");
            finish(); // to close current activity
            startActivity(new Intent(this, ViewEmployeeDetails.class));
        }
    }

    private void toastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}

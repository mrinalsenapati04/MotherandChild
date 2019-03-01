package com.sih.teamsquadra.motherandchild;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserUpdateDetails extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText editTextFullName;
    private EditText editTextAddress;
    private EditText editTextAge;
    private EditText editTextGender;
    private EditText editTextAadhar;
    private EditText editTextPhone;
    private EditText editTextPreviousHistory;
    private Button buttonUpdateDetailsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_details);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity
            finish();//close current activity
            startActivity(new Intent(this, LoginUser.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextFullName = findViewById(R.id.edittext_user_name);
        editTextAddress = findViewById(R.id.edittext_user_address);
        editTextAge = findViewById(R.id.edittext_user_age);
        editTextGender = findViewById(R.id.edittext_user_gender);
        editTextAadhar = findViewById(R.id.edittext_user_aadhar);
        editTextPhone = findViewById(R.id.edittext_user_phone);
        editTextPreviousHistory = findViewById(R.id.edittext_user_previousHistory);
        buttonUpdateDetailsDB = findViewById(R.id.button_update_details_database);
        buttonUpdateDetailsDB.setOnClickListener(this);
    }

    private void saveUserInformation(){
        String name = editTextFullName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String aadhar = editTextAadhar.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String previousHistory = editTextPreviousHistory.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).setValue(user);

        databaseReference.child(user.getUid()).child("type").setValue("user");
        databaseReference.child(user.getUid()).child("name").setValue(name);
        databaseReference.child(user.getUid()).child("address").setValue(address);
        databaseReference.child(user.getUid()).child("age").setValue(age);
        databaseReference.child(user.getUid()).child("gender").setValue(gender);
        databaseReference.child(user.getUid()).child("aadhar").setValue(aadhar);
        databaseReference.child(user.getUid()).child("phone").setValue(phone);
        databaseReference.child(user.getUid()).child("previousHistory").setValue(previousHistory);
        Toast.makeText(this," Information Saved ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdateDetailsDB){
            saveUserInformation();
            toastMessage(UserUpdateDetails.this, "Updated Successfully");
            finish(); // to close current activity
            startActivity(new Intent(this, ViewUserDetails.class));
        }

    }

    private void toastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}

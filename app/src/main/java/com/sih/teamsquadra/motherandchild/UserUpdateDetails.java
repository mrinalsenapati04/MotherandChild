package com.sih.teamsquadra.motherandchild;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    private DatabaseReference db;

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


        // editTextAadhar.addT
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
        //databaseReference.child(user.getUid()).setValue(user)

        // checkAadhar(aadhar);
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



   /* private void checkAadhar(final String aadharId){
        Log.v("UserUpdateDetails","here is aadhar" + aadharId);

        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("AadharNumber");

        Log.v("UserUpdateDetails","here is aadhar" + aadharId);

        scoresRef.orderByValue().addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("UserUpdateDetails","Here");
                Log.v("UserUpdateDetails","The " + dataSnapshot.getKey() + " Aadhar Number  is " + dataSnapshot.getValue());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }*/


    private void checkAadhar(final String aadharId) {

        Log.v("UserUpdateDetails", "Here");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference clothingRef = rootRef.child("AadharNumbers");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("UserUpdateDetails", "Here");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


    }


}

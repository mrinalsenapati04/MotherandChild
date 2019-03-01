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

public class DoctorUpdateDetails extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText editTextDoctorFullName;
    private EditText editTextDoctorSpeciality;
    private EditText editTextDoctorAge;
    private EditText editTextDoctorGender;
    private EditText editTextDoctorLicenseNo;
    private EditText editTextDoctorPhone;
    private EditText editTextDoctorExperience;
    private EditText editTextDoctorSkypeID;
    private Button buttonDoctorUpdateDetailsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update_details);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity
            finish();//close current activity
            startActivity(new Intent(this, LoginDoctor.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
        FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextDoctorFullName = findViewById(R.id.edittext_doctor_name);
        editTextDoctorSpeciality = findViewById(R.id.edittext_doctor_type);
        editTextDoctorAge = findViewById(R.id.edittext_doctor_age);
        editTextDoctorGender = findViewById(R.id.edittext_doctor_gender);
        editTextDoctorLicenseNo = findViewById(R.id.edittext_doctor_license);
        editTextDoctorPhone = findViewById(R.id.edittext_doctor_phone);
        editTextDoctorExperience = findViewById(R.id.edittext_doctor_experience);
        editTextDoctorSkypeID = findViewById(R.id.edittext_doctor_skypeID);
        buttonDoctorUpdateDetailsDB = findViewById(R.id.button_doctor_update_details_database);
        buttonDoctorUpdateDetailsDB.setOnClickListener(this);
    }

    private void saveDoctorInformation(){
        String name = editTextDoctorFullName.getText().toString().trim();
        String speciality = editTextDoctorSpeciality.getText().toString().trim();
        String age = editTextDoctorAge.getText().toString().trim();
        String gender = editTextDoctorGender.getText().toString().trim();
        String license = editTextDoctorLicenseNo.getText().toString().trim();
        String phone = editTextDoctorPhone.getText().toString().trim();
        String experience = editTextDoctorExperience.getText().toString().trim();
        String skypeID = editTextDoctorSkypeID.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).setValue(user);

        databaseReference.child(user.getUid()).child("type").setValue("doctor");
        databaseReference.child(user.getUid()).child("name").setValue(name);
        databaseReference.child(user.getUid()).child("speciality").setValue(speciality);
        databaseReference.child(user.getUid()).child("age").setValue(age);
        databaseReference.child(user.getUid()).child("gender").setValue(gender);
        databaseReference.child(user.getUid()).child("license").setValue(license);
        databaseReference.child(user.getUid()).child("phone").setValue(phone);
        databaseReference.child(user.getUid()).child("experience").setValue(experience);
        databaseReference.child(user.getUid()).child("skypeID").setValue(skypeID);
        toastMessage(this," Information Saved ");
    }

    @Override
    public void onClick(View v) {
        if(v == buttonDoctorUpdateDetailsDB){
            saveDoctorInformation();
            toastMessage(this, "Updated Successfully");
            finish(); // to close current activity
            startActivity(new Intent(this, ViewDoctorDetails.class));
        }

    }

    private void toastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}


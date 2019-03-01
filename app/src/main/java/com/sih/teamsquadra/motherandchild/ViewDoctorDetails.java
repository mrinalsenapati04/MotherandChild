package com.sih.teamsquadra.motherandchild;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDoctorDetails extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    private DatabaseReference databaseReference;
    private String doctorID;

    private Button buttonUpdateDoctorDetails;
    private Button buttonDoctorMain;
    private TextView textViewDoctorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_details);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        doctorID = user.getUid();

        textViewDoctorDetails = findViewById(R.id.textview_doctor_details);
        buttonDoctorMain = findViewById(R.id.button_doctor_back);
        buttonDoctorMain.setOnClickListener(this);

        buttonUpdateDoctorDetails = findViewById(R.id.doctor_update_details);
        buttonUpdateDoctorDetails.setOnClickListener(this);

        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                doctorID = user.getUid();
                if (user != null) {
                    // User is signed in
                    Log.v("ViewDoctorDetails", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage(ViewDoctorDetails.this, "Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("ViewDoctorDetails", "onAuthStateChanged:signed_out");
                    toastMessage(ViewDoctorDetails.this, "Successfully signed out.");
                }
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            DoctorInformation uInfo = new DoctorInformation();
            uInfo = ds.child(doctorID).getValue(DoctorInformation.class);

            if (uInfo != null) {
                //display all the information
                Log.v("ViewDoctorDetails", "showData: name: " + uInfo.getName());

                String details = "Full Name: " + uInfo.getName() + "\nSpeciality: " + uInfo.getSpeciality() + "\nAge: " + uInfo.getAge() +
                        "\nGender: " + uInfo.getGender() + "\nLicense_no: " + uInfo.getLicense_no() + "\nPhone: " + uInfo.getPhone() +
                        "\nExperience: " + uInfo.getExperience() + "\nSkpye ID: " + uInfo.getSkypeID();

                textViewDoctorDetails.setText(details);
                Log.v("ViewDoctorDetails", details);
                return;
            }
        }

    }

    private void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonDoctorMain) {
            finish();
            startActivity(new Intent(this, DoctorMain.class));
        }else if (v == buttonUpdateDoctorDetails) {
            startActivity(new Intent(this, DoctorUpdateDetails.class));
        }
    }
}

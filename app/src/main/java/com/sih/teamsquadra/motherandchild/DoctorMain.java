package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import org.jetbrains.annotations.NotNull;

public class DoctorMain extends AppCompatActivity implements View.OnClickListener {
    private Button buttonDoctorLogout;
    private ImageButton imageButtonDoctorTelemedicine;
    private ImageButton imageButtonDoctorConfirmAppointments;
    private ImageButton imageButtonDoctorPatientPrescriptions;
    private ImageButton imageButtonDoctorViewDetails;
    private TextView textViewDoctorWelcome;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    private DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        textViewDoctorWelcome = findViewById(R.id.doctor_welcome);

        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userID = user.getUid();
                    // User is signed in
                    Log.v("DoctorMain", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("DoctorMain", "onAuthStateChanged:signed_out");
                    firebaseAuth.signOut();
                    toastMessage("Successfully signed out.");
                    startActivity(new Intent(DoctorMain.this, LoginDoctor.class));
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



        buttonDoctorLogout = findViewById(R.id.doctor_logout);
        buttonDoctorLogout.setOnClickListener(this);

        imageButtonDoctorViewDetails = findViewById(R.id.doctor_view_details);
        imageButtonDoctorViewDetails.setOnClickListener(this);

        imageButtonDoctorConfirmAppointments = findViewById(R.id.confirm_appointments);
        imageButtonDoctorConfirmAppointments.setOnClickListener(this);

        imageButtonDoctorTelemedicine = findViewById(R.id.doctor_telemedicine);
        imageButtonDoctorTelemedicine.setOnClickListener(this);

        imageButtonDoctorPatientPrescriptions = findViewById(R.id.patients_prescription);
        imageButtonDoctorPatientPrescriptions.setOnClickListener(this);

    }

    private void showData(@NotNull DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            DoctorInformation uInfo = new DoctorInformation();
            Log.v("DoctorMain", "userID: " + userID);
            if (userID != null) {
                uInfo = ds.child(userID).getValue(DoctorInformation.class);
            }

            if (uInfo != null) {
                //display all the information
                Log.v("DoctorMain", "showData: name: " + uInfo.getName());
                Log.v("DoctorMain", "showData: type: " + uInfo.getType());

                if(!uInfo.getType().equals("doctor")){
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                    toastMessage("Not a Doctor..");
                    Log.v("DoctorMain", "Not a Doctor");

                }

                String message = "Welcome "+ uInfo.getType()+", "+ uInfo.getName();
                textViewDoctorWelcome.setText(message);
                Log.v("DoctorMain", message);
                return;
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonDoctorLogout){
            firebaseAuth.signOut();
            toastMessage("Signing Out...");
            finish();
            startActivity(new Intent(this, Feedback.class));

        } else if (v == imageButtonDoctorConfirmAppointments){
            startActivity(new Intent(this, DoctorConfirmAppointments.class));
        }else if (v == imageButtonDoctorViewDetails){
            startActivity(new Intent(this, ViewDoctorDetails.class));
        }else if (v == imageButtonDoctorTelemedicine){
            startActivity(new Intent(this, DoctorTelemedicine.class));
        }else if (v == imageButtonDoctorPatientPrescriptions){
            startActivity(new Intent(this, DoctorPatientPrescriptions.class));
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
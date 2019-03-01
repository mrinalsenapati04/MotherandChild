package com.sih.teamsquadra.motherandchild;

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

public class EmployeeMain extends AppCompatActivity implements View.OnClickListener {

    private Button buttonEmployeeLogout;
    private Button buttonEmployeeTelemedicine;
    private Button buttonBookAppointment;
    private Button buttonEconomicSupport;
    private Button buttonTransportFacility;
    private Button buttonEmployeeViewDetails;
    private TextView textViewEmployeeWelcome;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    private DatabaseReference databaseReference;
    private String employeeID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = firebaseAuth.getCurrentUser();
        employeeID = user.getUid();

        textViewEmployeeWelcome = findViewById(R.id.employee_welcome);

        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    employeeID = user.getUid();
                    Log.v("EmployeeMain", "In2 employeeID: " + employeeID);
                    // User is signed in
                    Log.v("EmployeeMain", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("EmployeeMain", "onAuthStateChanged:signed_out");
                    firebaseAuth.signOut();
                    toastMessage("Successfully signed out.");
                    startActivity(new Intent(EmployeeMain.this, LoginEmployee.class));
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

        buttonEmployeeLogout = findViewById(R.id.employee_logout);
        buttonEmployeeLogout.setOnClickListener(this);

        buttonEmployeeTelemedicine = findViewById(R.id.button_telemedicine);
        buttonEmployeeTelemedicine.setOnClickListener(this);

        buttonBookAppointment = findViewById(R.id.button_dr_appointment);
        buttonBookAppointment.setOnClickListener(this);

        buttonTransportFacility = findViewById(R.id.button_transportation);
        buttonTransportFacility.setOnClickListener(this);

        buttonEconomicSupport = findViewById(R.id.button_economic_support);
        buttonEconomicSupport.setOnClickListener(this);

        buttonEmployeeViewDetails = findViewById(R.id.employee_view_details);
        buttonEmployeeViewDetails.setOnClickListener(this);
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            EmployeeInformation uInfo = new EmployeeInformation();
            Log.v("EmployeeMain", "employeeID: " + employeeID);
            if(employeeID != null) {
                uInfo = ds.child(employeeID).getValue(EmployeeInformation.class);
            }

            if (uInfo != null) {
                //display all the information
                Log.v("EmployeeMain", "showData: name: " + uInfo.getName());
                Log.v("EmployeeMain", "showData: name: " + uInfo.getType());

                if(!uInfo.getType().equals("employee")){
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                    toastMessage("Not an Employee..");
                    Log.v("EmployeeMain", "Not an Employee..");
                }

                String message = "Welcome " +uInfo.getType()+", " +uInfo.getName();
                textViewEmployeeWelcome.setText(message);
                Log.v("EmployeeMain", message);
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonEmployeeLogout) {
            firebaseAuth.signOut();
            toastMessage("Signing Out...");
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if (v == buttonEmployeeViewDetails) {
            startActivity(new Intent(this, ViewEmployeeDetails.class));
        }else if (v == buttonEmployeeTelemedicine) {
            startActivity(new Intent(this, EmployeeTelemedicine.class));
        }else if (v == buttonBookAppointment) {
            startActivity(new Intent(this, EmployeeBookAppointment.class));
        }else if (v == buttonTransportFacility) {
            startActivity(new Intent(this, TransportFacility.class));
        }else if (v == buttonEconomicSupport) {
            startActivity(new Intent(this, EconomicSupport.class));
        }


    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

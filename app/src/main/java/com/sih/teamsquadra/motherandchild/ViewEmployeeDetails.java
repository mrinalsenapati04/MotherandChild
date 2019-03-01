package com.sih.teamsquadra.motherandchild;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import org.jetbrains.annotations.NotNull;

public class ViewEmployeeDetails extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    private DatabaseReference databaseReference;
    private String userID;

    private Button buttonEmployeeMain;
    private Button buttonUpdateEmployeeDetails;
    private TextView textViewEmployeeDetails;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_details);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        textViewEmployeeDetails = findViewById(R.id.textview_employee_details);
        buttonEmployeeMain = findViewById(R.id.button_employee_back);
        buttonEmployeeMain.setOnClickListener(this);


        buttonUpdateEmployeeDetails = findViewById(R.id.employee_update_details);
        buttonUpdateEmployeeDetails.setOnClickListener(this);

        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userID = user.getUid();
                    // User is signed in
                    Log.v("ViewEmployeeDetails", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage(ViewEmployeeDetails.this, "Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("ViewEmployeeDetails", "onAuthStateChanged:signed_out");
                    toastMessage(ViewEmployeeDetails.this, "Successfully signed out.");
                }
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            EmployeeInformation uInfo = new EmployeeInformation();
            uInfo = ds.child(userID).getValue(EmployeeInformation.class);

            if (uInfo != null) {
                //display all the information
                Log.v("ViewEmployeeDetails", "showData: name: " + uInfo.getName());

                String details = "Full Name: " + uInfo.getName() + "\nEmployeeID: " + uInfo.getEmployeeID() +"\nAge: "+uInfo.getAge() +
                        "\nGender: "+uInfo.getGender()+"\nAadhar: "+uInfo.getAadhar()+"\nPhone: "+uInfo.getPhone();

                textViewEmployeeDetails.setText(details);
                Log.v("ViewEmployeeDetails", details);
                return;
            }
        }

    }

    private void toastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonEmployeeMain){
            finish();
            startActivity(new Intent(this, EmployeeMain.class));
        } else if (v == buttonUpdateEmployeeDetails){
            startActivity(new Intent(this, EmployeeUpdateDetails.class));
        }
    }
}

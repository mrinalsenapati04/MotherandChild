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

public class ViewUserDetails extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    private DatabaseReference databaseReference;
    private String userID;

    private Button buttonUserMain;
    private TextView textViewPersonalDetails;
    private Button buttonUpdateUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        textViewPersonalDetails = findViewById(R.id.textview_personal_details);


        buttonUserMain = findViewById(R.id.button_back);
        buttonUserMain.setOnClickListener(this);

        buttonUpdateUserDetails = findViewById(R.id.user_update_details);
        buttonUpdateUserDetails.setOnClickListener(this);


        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                userID = user.getUid();
                if (user != null) {
                    // User is signed in
                    Log.v("ViewUserDetails", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage(ViewUserDetails.this, "Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("ViewUserDetails", "onAuthStateChanged:signed_out");
                    toastMessage(ViewUserDetails.this, "Successfully signed out.");
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
            UserInformation uInfo = new UserInformation();
            uInfo = ds.child(userID).getValue(UserInformation.class);

            if (uInfo != null) {
                //display all the information
                Log.v("ViewDetailsActivity", "showData: name: " + uInfo.getName());
                Log.v("ViewDetailsActivity", "showData: address: " + uInfo.getAddress());

                String details = "Full Name: " + uInfo.getName() + "\nAddress: " + uInfo.getAddress() +"\nAge: "+uInfo.getAge() +
                        "\nGender: "+uInfo.getGender()+"\nAadhar: "+uInfo.getAadhar()+"\nPhone: "+uInfo.getPhone()+"\nPrevious History: "+uInfo.getPreviousHistory();

                textViewPersonalDetails.setText(details);
                Log.v("ViewDetailsActivity", details);
                return;
            }
        }

    }

    private void toastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUserMain){
            finish();
            startActivity(new Intent(this, UserMain.class));
        }else if (v == buttonUpdateUserDetails){
            startActivity(new Intent(this, UserUpdateDetails.class));
        }

    }
}

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

import com.google.android.gms.stats.internal.G;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMain extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUserLogout;

    private Button buttonUserViewDetails;
    private TextView textViewUserWelcome;
    private Button buttonGovernmentScheme;
    private Button buttonFoodAndNutrition;
    private Button buttonRemainders;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    private DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        textViewUserWelcome = findViewById(R.id.user_welcome);

        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                userID = user.getUid();
                // User is signed in
                Log.v("UserMain", "onAuthStateChanged:signed_in:" + user.getUid());
                toastMessage("Successfully signed in with: " + user.getEmail());
            } else {
                // User is signed out
                Log.d("UserMain", "onAuthStateChanged:signed_out");
                firebaseAuth.signOut();
                toastMessage("Successfully signed out.");
                startActivity(new Intent(UserMain.this, LoginUser.class));
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


        buttonUserLogout = findViewById(R.id.user_logout);
        buttonUserLogout.setOnClickListener(this);

        buttonUserViewDetails = findViewById(R.id.user_view_details);
        buttonUserViewDetails.setOnClickListener(this);

        buttonFoodAndNutrition = findViewById(R.id.food_nutrition);
        buttonFoodAndNutrition.setOnClickListener(this);

        buttonGovernmentScheme = findViewById(R.id.govt_scheme);
        buttonGovernmentScheme.setOnClickListener(this);

        buttonRemainders = findViewById(R.id.remainders);
        buttonRemainders.setOnClickListener(this);
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();
            Log.v("UserMain", "userID: " + userID);
            if(userID != null) {
                uInfo = ds.child(userID).getValue(UserInformation.class);
            }

            if (uInfo != null) {
                //display all the information
                Log.v("UserMain", "showData: name: " + uInfo.getName());
                Log.v("UserMain", "showData: type: " + uInfo.getType());

                if(!uInfo.getType().equals("user")){
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                    toastMessage("Not a User..");
                    Log.v("UserMain", "Not a User");

                }

                String message = "Welcome " +uInfo.getType()+", "+ uInfo.getName();
                textViewUserWelcome.setText(message);
                Log.v("UserMain", message);
                return;
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUserLogout){
            firebaseAuth.signOut();
            toastMessage("Signing Out...");
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }  else if (v == buttonUserViewDetails){
            startActivity(new Intent(this, ViewUserDetails.class));
        }else if (v == buttonGovernmentScheme){
            startActivity(new Intent(this, UserGovernmentSchemes.class));
        }else if (v == buttonFoodAndNutrition){
            startActivity(new Intent(this, UserFoodAndNutrition.class));
        }else if (v == buttonRemainders){
            startActivity(new Intent(this, UserRemainders.class));
        }

    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

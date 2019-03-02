package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserGovernmentSchemes extends AppCompatActivity implements View.OnClickListener  {

    private Button buttonctc1;
    private Button buttonctc2;
    private Button buttonctc3;
    private Button buttonctc4;
    private Button buttonctc5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_government_schemes);

        buttonctc1= findViewById(R.id.c1);
        buttonctc2= findViewById(R.id.c2);
        buttonctc3= findViewById(R.id.c3);
        buttonctc4= findViewById(R.id.c4);
        buttonctc5= findViewById(R.id.c5);


        buttonctc1.setOnClickListener(this);
        buttonctc2.setOnClickListener(this);
        buttonctc3.setOnClickListener(this);
        buttonctc5.setOnClickListener(this);
        buttonctc4.setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {

        if (v == buttonctc1){
            startActivity(new Intent(this, GovtSchemeJSSK.class));
        }else if (v == buttonctc2){
            startActivity(new Intent(this, GovtSchemeFBNC.class));
        }else if (v == buttonctc3){
            startActivity(new Intent(this, GovtSchemeHBNC.class));
        }else if (v == buttonctc4){
            startActivity(new Intent(this, GovtSchemeINAP.class));
        }else if (v == buttonctc5){
        startActivity(new Intent(this, GovtSchemeRBSK.class));}

    }
}

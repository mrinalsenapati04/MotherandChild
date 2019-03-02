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

    private Button buttonhth1;
    private Button buttonhth2;
    private Button buttonhth3;
    private Button buttonhth4;
    private Button buttonhth5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_government_schemes);

        buttonctc1= findViewById(R.id.c1);
        buttonctc2= findViewById(R.id.c2);
        buttonctc3= findViewById(R.id.c3);
        buttonctc4= findViewById(R.id.c4);
        buttonctc5= findViewById(R.id.c5);

        buttonhth1= findViewById(R.id.apply_govtscheme_jssk);
        buttonhth2= findViewById(R.id.apply_govtscheme_fbnc);
        buttonhth3= findViewById(R.id.apply_govtscheme_hbnc);
        buttonhth4= findViewById(R.id.apply_govtscheme_inap);
        buttonhth5= findViewById(R.id.apply_govtscheme_rbsk);


        buttonctc1.setOnClickListener(this);
        buttonctc2.setOnClickListener(this);
        buttonctc3.setOnClickListener(this);
        buttonctc5.setOnClickListener(this);
        buttonctc4.setOnClickListener(this);

        buttonhth1.setOnClickListener(this);
        buttonhth2.setOnClickListener(this);
        buttonhth3.setOnClickListener(this);
        buttonhth5.setOnClickListener(this);
        buttonhth4.setOnClickListener(this);






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
        else if (v == buttonhth1){
            startActivity(new Intent(this, ApplySchemeJSSK.class));
        }else if (v == buttonhth2){
            startActivity(new Intent(this, ApplySchemeFBNC.class));
        }else if (v == buttonhth3){
            startActivity(new Intent(this, ApplySchemeHBNC.class));
        }else if (v == buttonhth4){
            startActivity(new Intent(this, ApplySchemeINAP.class));
        }else if (v == buttonhth5){
            startActivity(new Intent(this, ApplySchemeRBSK.class));}

    }
}

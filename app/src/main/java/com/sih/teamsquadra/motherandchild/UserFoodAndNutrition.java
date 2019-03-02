package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserFoodAndNutrition extends AppCompatActivity implements View.OnClickListener{


    private Button buttonfirsttri;
    private Button buttonsecondtri;
    private Button buttonthirdtri;
    private Button buttonfirstmnth;
    private Button buttonsecondmnth;
    private Button buttonthirdmnth;
    private Button buttonfourthmnth;
    private Button buttonfifthmnth;
    private Button buttonfoodavoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food_and_nutrition);


        buttonfirsttri= findViewById(R.id.button_mother_first);
        buttonsecondtri= findViewById(R.id.button_mother_second);
        buttonthirdtri= findViewById(R.id.button_mother_third);

        buttonfirstmnth= findViewById(R.id.button_child_first);
        buttonsecondmnth= findViewById(R.id.button_child_second);
        buttonthirdmnth= findViewById(R.id.button_child_third);
        buttonfourthmnth= findViewById(R.id.button_child_fourth);
        buttonfifthmnth= findViewById(R.id.button_child_fifth);

        buttonfoodavoid = findViewById(R.id.food_avoid);



        buttonfirsttri.setOnClickListener(this);
        buttonsecondtri.setOnClickListener(this);
        buttonthirdtri.setOnClickListener(this);
        buttonfirstmnth.setOnClickListener(this);
        buttonsecondmnth.setOnClickListener(this);
        buttonthirdmnth.setOnClickListener(this);
        buttonfourthmnth.setOnClickListener(this);
        buttonfifthmnth.setOnClickListener(this);
        buttonfoodavoid.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {


        if (v == buttonfirsttri){
            startActivity(new Intent(this, FirstTrimester.class));
        }else if (v == buttonsecondtri){
            startActivity(new Intent(this, SecondTrimester.class));
        }else if (v == buttonthirdtri){
            startActivity(new Intent(this, ThirdTrimester.class));
        }else if (v == buttonfirstmnth){
            startActivity(new Intent(this, Firstmonthset.class));
        }else if (v == buttonsecondmnth){
            startActivity(new Intent(this, Secondmonthset.class));}
        else if (v == buttonthirdmnth){
            startActivity(new Intent(this, Thirdmonthset.class));
        }else if (v == buttonfourthmnth){
            startActivity(new Intent(this, Fourthmonthset.class));
        }else if (v == buttonfifthmnth){
            startActivity(new Intent(this, Fifthmonthset.class));
        } else if (v == buttonfoodavoid) {
            startActivity(new Intent(this, AvoidFood.class));
        }

    }
}

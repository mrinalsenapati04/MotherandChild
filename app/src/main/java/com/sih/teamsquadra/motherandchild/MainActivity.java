package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButtonUser;
    private ImageButton imageButtonDoctor;
    private ImageButton imageButtonEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageButtonUser = findViewById(R.id.imagebutton_mother);
        imageButtonDoctor = findViewById(R.id.imagebutton_doctor);
        imageButtonEmployee = findViewById(R.id.imagebutton_employee);

        imageButtonUser.setOnClickListener(this);
        imageButtonDoctor.setOnClickListener(this);
        imageButtonEmployee.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageButtonUser) {
            //finish(); // to close current activity
            startActivity(new Intent(this, LoginUser.class));
        } else if (v == imageButtonDoctor) {
            //finish(); // to close current activity
            startActivity(new Intent(this, LoginDoctor.class));
        } else if (v == imageButtonEmployee) {
            //finish(); // to close current activity
            startActivity(new Intent(this, LoginEmployee.class));
        }
    }
}
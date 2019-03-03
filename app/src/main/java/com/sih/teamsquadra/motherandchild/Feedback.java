package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Feedback extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSubmit;
    private int radioFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        buttonSubmit = findViewById(R.id.submit);
        buttonSubmit.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.good:
                if (checked) {
                    radioFeedback = 4;
                    break;
                }

            case R.id.satisfactory:
                if (checked) {
                    radioFeedback = 3;
                    break;
                }
            case R.id.average:
                if (checked) {
                    radioFeedback = 2;
                    break;
                }

            case R.id.belowaverage:
                if (checked) {
                    radioFeedback = 1;
                    break;
                }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == buttonSubmit) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}

package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class TransportFacility extends AppCompatActivity implements AdapterView.OnItemSelectedListener , View.OnClickListener{

    private Button buttonCallDriver;
    private boolean radioCheck;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_facility);

        spinner = findViewById(R.id.spinner_driver_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.privatevehicle,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        buttonCallDriver = findViewById(R.id.button_call_driver);
        buttonCallDriver.setOnClickListener(this);
    }

    public void onRadioButtonClicked (View view){

        boolean checked = ((RadioButton)view).isChecked();

        switch(view.getId()){

            case R.id.call_ambulance:
                if (checked){
                    radioCheck = false;
                    break;
                }

            case R.id.call_private:
                if (checked){
                    radioCheck = true;
                    break;
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT ).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v == buttonCallDriver){

            Log.v("TransportFacility", "radioCheck is "+radioCheck);
            if(radioCheck) {
                Log.v("TransportFacility", spinner.getSelectedItem().toString());
                dialPhoneNumber(spinner.getSelectedItem().toString());
            }else{
                dialPhoneNumber("108");
            }
        }

    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

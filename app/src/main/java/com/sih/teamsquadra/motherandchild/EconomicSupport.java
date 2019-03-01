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
import android.widget.Spinner;

public class EconomicSupport extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private Button buttonCallNGO;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic_support);

        spinner = findViewById(R.id.support_type);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this,R.array.ngoSupport,android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter7);
        spinner.setOnItemSelectedListener(this);

        buttonCallNGO = findViewById(R.id.button_call_ngo);
        buttonCallNGO.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v == buttonCallNGO){
            Log.v("TransportFacility", spinner.getSelectedItem().toString());
            dialPhoneNumber(spinner.getSelectedItem().toString());
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

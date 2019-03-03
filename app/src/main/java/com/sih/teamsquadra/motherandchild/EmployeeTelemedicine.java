package com.sih.teamsquadra.motherandchild;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeTelemedicine extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Button buttonVideoCall;
    private String selected;
    private DatabaseReference db;
    private Spinner spinnerDoctorName;
    private HashMap<String, String> hash_map = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_telemedicine);

        spinnerDoctorName = (Spinner) findViewById(R.id.doctor_name);

        final Spinner spinner = findViewById(R.id.doctor_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.doctorstype,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //Onclick Listener for Doctor Type Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                setDoctorNameList(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //OnClickListen for Doc_name Spinner

       /* spDocName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s=parent.getItemAtPosition(position).toString();
                getSkypeId(s);
            }
        });*/
/*
        spDocName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=parent.getItemAtPosition(position).toString();
                Toast.makeText(EmployeeTelemedicine.this,"s is "+ s,Toast.LENGTH_LONG).show();
                //Log.v("EmployeeTelemedicine","selected skype id  here is " + selectedSkypeId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        buttonVideoCall = findViewById(R.id.video_call);
        buttonVideoCall.setOnClickListener(this);

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
        if(v== buttonVideoCall){
            String docName = spinnerDoctorName.getSelectedItem().toString().trim();
            String skypeId = hash_map.get(docName);
            Log.v("EmployeeTelemedicine", "hash map value is  " + hash_map.get(docName));
            initiateSkypeUri(this, Uri.parse("skype:" + "live:" + skypeId + "?call&video=true").toString());

        }

    }


    /**
     * Initiate the actions encoded in the specified URI.
     */
    public void initiateSkypeUri(Context myContext, String mySkypeUri) {

        // Make sure the Skype for Android client is installed.
        if (!isSkypeClientInstalled(myContext)) {
            goToMarket(myContext);
            Log.i("MainActiviy", "Skype is Not Installed");
            return;
        }

        // Create the Intent from our Skype URI.
        Uri skypeUri = Uri.parse(mySkypeUri);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);

        // Restrict the Intent to being handled by the Skype for Android client only.
        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Initiate the Intent. It should never fail because you've already established the
        // presence of its handler (although there is an extremely minute window where that
        // handler can go away).
        myContext.startActivity(myIntent);

        return;
    }

    /*
     * Determine whether the Skype for Android client is installed on this device.
     */
    public boolean isSkypeClientInstalled(Context myContext) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    /*
     * Install the Skype client through the market: URI scheme.
     */
    public void goToMarket(Context myContext) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);

        return;
    }

    private void setDoctorNameList(final String s) {

        db = FirebaseDatabase.getInstance().getReference();

        db.child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> namelist = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String docT = ds.child("speciality").getValue(String.class);
                    //  String docSI=ds.child("skypeID").getValue(String.class);
                    //  editTextSkypeId.set

                    if (docT.equals(s)) {
                        String name = ds.child("name").getValue(String.class);
                        String sId = ds.child("skypeID").getValue(String.class);
                        hash_map.put(name, sId);

                        namelist.add(name);
                    }

                }
                //   spDocName=(Spinner) findViewById(R.id.doctor_name);
                ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(EmployeeTelemedicine.this, android.R.layout.simple_spinner_item, namelist);
                namesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoctorName.setAdapter(namesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

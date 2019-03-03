package com.sih.teamsquadra.motherandchild;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeBookAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener , View.OnClickListener{

    private Spinner spDocName;
    private Spinner spinner;
    private String docType;
    private String selected;

    private EditText Date;
    private EditText Time;
    private EditText Venue;
    private EditText doc_aadhar;
    private EditText user_aadhar;

    private DatabaseReference databaseReference;




    private DatabaseReference db;




    private Button buttonBookAppointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_book_appointment);


        //  Spinner spinner1 = findViewById(R.id.doctor_name);
        //  ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.doctorsname,android.R.layout.simple_spinner_item);
        //   adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // spinner1.setAdapter(adapter1);
        //  spinner1.setOnItemSelectedListener(this);


        spinner = findViewById(R.id.doctor_type);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.doctorstype,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                //Toast.makeText(EmployeeBookAppointment.this, "Selected item is " + selected, Toast.LENGTH_LONG).show();
                // Log.v("EmployeeBookAppointment","Selected String is " + selected);
                setDoctorNameList(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Setting Doctor Name List according to doctor type;
        // setDoctorNameList(docType);


        buttonBookAppointment = findViewById(R.id.button_book_appointment);
        buttonBookAppointment.setOnClickListener(this);

        Date = (EditText) findViewById(R.id.Date);
        Time = (EditText) findViewById(R.id.time);

        Venue = (EditText) findViewById(R.id.editText);
        doc_aadhar = (EditText) findViewById(R.id.editText);
        user_aadhar = (EditText) findViewById(R.id.editText);

        databaseReference = FirebaseDatabase.getInstance().getReference();



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
        if(v == buttonBookAppointment){
            bookAppointment();
            finish(); // to close current activity
            toastMessage(this," Doctor Appointment Request Send");
            startActivity(new Intent(this,EmployeeMain.class));

        }
    }

    private void toastMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }

    private void setDoctorNameList(final String s) {
        // final String docType1=spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();

        //  final Spinner sp=findViewById(R.id.doctor_type);
        //final String docType=spinner.getSelectedItem().toString();
        db = FirebaseDatabase.getInstance().getReference();

        db.child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> namelist = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String dT = ds.child("speciality").getValue(String.class);
                    // Log.v("EmployeeBookAppointment","dT is " + dT + " docType is " + docType1);
                    if (dT.equals(s)) {
                        String name = ds.child("name").getValue(String.class);
                        namelist.add(name);
                    }

                }
                spDocName = (Spinner) findViewById(R.id.doctor_name);
                ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(EmployeeBookAppointment.this, android.R.layout.simple_spinner_item, namelist);
                namesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDocName.setAdapter(namesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void bookAppointment() {

        // String docType=spinner.getSelectedItem().toString();
        String docName = spDocName.getSelectedItem().toString();
        String date = Date.getText().toString();
        String time = Time.getText().toString();
        String dAdhar = doc_aadhar.getText().toString();
        String uAdhar = user_aadhar.getText().toString();


        EmployeeBookAppointmentClass employeeAppoinmentObj = new EmployeeBookAppointmentClass(docName, date, time, dAdhar, uAdhar);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
        databaseReference.child("Appointments").child(formattedDate).setValue(employeeAppoinmentObj);


    }


}


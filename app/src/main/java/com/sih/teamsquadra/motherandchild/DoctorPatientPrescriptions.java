package com.sih.teamsquadra.motherandchild;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DoctorPatientPrescriptions extends AppCompatActivity implements View.OnClickListener {


    private static final int PICK_IMAGE_REQUEST = 1234;

    private Button buttonChoose;
    private Button buttonUpload;
    private EditText editAadhar;
    private EditText editDesc;
    private String uniqueId;

// public Button buttonImageList;

    private ImageView imageView;

    private Uri filePath;


    StorageReference storageReference;
    DatabaseReference databaseReference;
    DatabaseReference userDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_prescriptions);


        //getting views from layout
        buttonChoose = (Button) findViewById(R.id.buttonImage);
        buttonUpload = (Button) findViewById(R.id.buttonSubmit);

        editAadhar = (EditText) findViewById(R.id.editTextAadhar);
        editDesc = (EditText) findViewById(R.id.editTextDesc);

        imageView = (ImageView) findViewById(R.id.imageView);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);


    }

    //buttonImageList.setOnClickListener(this);
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == buttonChoose) {
            showFileChooser();
        }
        //if the clicked button is upload
        else if (view == buttonUpload) {


            final String aadharno = editAadhar.getText().toString();
            // final String uId;

            FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String uniqueId1 = null;

                    for (DataSnapshot userId : dataSnapshot.getChildren()) {

                        uniqueId = userId.getKey();
                        UserInformation userInfo = userId.getValue(UserInformation.class);

                        if (userInfo.getAadhar().equals(aadharno)) {
                            //  Log.v("PatientDetails", " Here Aadhar no is  " + userInfo.getAadhar());
                            //Log.v("PatientDetails", " here Node Uniqueid is " + uniqueId);
                            if (filePath != null) {

                                Log.v("PatientDetails", "  Uniqueid is under filepath  " + uniqueId);
                                final String uId = uniqueId;
                                uniqueId1 = uId;
                                //displaying a progress dialog while upload is going on
                                final ProgressDialog progressDialog = new ProgressDialog(DoctorPatientPrescriptions.this);
                                progressDialog.setTitle("Uploading");
                                progressDialog.show();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                final String formattedDate = df.format(c.getTime());

                                String image_path = editAadhar.getText().toString() + "/" + formattedDate;
                                // final String patient_path=editAadhar.getText().toString();
                                StorageReference riversRef = storageReference.child(image_path);
                                riversRef.putFile(filePath)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                //Log.v("PatientDetails", "  Uniqueid is under onSuccesss  " + uniqueId);
                                                //Log.v("PatientDetails", "  uId is under onSuccesss  " + uId);
                                                //if the upload is successfull
                                                //hiding the progress dialog
                                                progressDialog.dismiss();

                                                Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                                                //and displaying a success toast

                                                //  Log.v("PatientDetails", "Here before patient Image ");
                                                final PatientImage patientimage = new PatientImage(editAadhar.getText().toString(), editDesc.getText().toString(), taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());


                                                //Log.v("PatientDetails", "Here before on DataChange ");


                                                //Log.v("PatientDetails", "uid before database entry " + uId);
                                                userDatabaseReference.child(uId).child("Previous Records").child(formattedDate).setValue(patientimage);

                                                finish();
                                                Toast.makeText(DoctorPatientPrescriptions.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(DoctorPatientPrescriptions.this, DoctorMain.class));


                                                //Log.v("PatientDetails", "uid after database entry " + uId);


                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                //if the upload is not successfull
                                                //hiding the progress dialog
                                                progressDialog.dismiss();

                                                //and displaying error message
                                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                //calculating progress percentage
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                                //displaying percentage in progress dialog
                                                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                            }
                                        });
                            }
                            //if there is not any file
                            else {
                                //you can display an error toast
                            }
                            return;

                        }
                    }
                    if (uniqueId1 == null) {
                        finish();
                        Toast.makeText(DoctorPatientPrescriptions.this, "AadharId does not exists", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DoctorPatientPrescriptions.this, DoctorMain.class));


                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }

    private void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}



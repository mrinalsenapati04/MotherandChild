package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecordVideo extends AppCompatActivity {


    private Uri VideoUri;
    private static final int REQUEST_CODE = 101;
    private StorageReference vedioReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);


        vedioReference = FirebaseStorage.getInstance().getReference().child("videos");
    }

    public void Upload(View view) {
        if (VideoUri != null) {

            //FirebaseStorage vedioref=vedioReference.
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String formattedDate = df.format(c.getTime());

            UploadTask uploadTask = vedioReference.child(formattedDate).putFile(VideoUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    Toast.makeText(RecordVideo.this, "Uploaded Successfully!!", Toast.LENGTH_LONG).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //if the upload is not successfull
                    //hiding the progress dialog

                    //and displaying error message
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //calculating progress percentage
                    updateProgress(taskSnapshot);

                }
            });

        }
    }

    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot) {


        long fileSize = taskSnapshot.getTotalByteCount();
        long uploadBytes = taskSnapshot.getBytesTransferred();

        long progress = (100 * uploadBytes) / fileSize;
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbar);
        progressBar.setProgress((int) progress);


    }

    public void Record(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void download(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());

        try {

            final File localFile = File.createTempFile("userintro", "3gp");
            vedioReference.child(formattedDate).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RecordVideo.this, "Download Successfull", Toast.LENGTH_LONG).show();
                    final VideoView videoView = (VideoView) findViewById(R.id.videoView);
                    videoView.setVideoURI(Uri.fromFile(localFile));
                    videoView.start();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        } catch (Exception e) {

            Toast.makeText(RecordVideo.this, "failled to create Temp File :" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();


        }

    }


    public void onActivityResult(int requestcode, int resultcode, Intent data) {

        VideoUri = data.getData();
        if (requestcode == REQUEST_CODE) {
            if (resultcode == RESULT_OK) {
                Toast.makeText(RecordVideo.this, "Video Saved to :\n" + VideoUri, Toast.LENGTH_LONG).show();

            } else if (resultcode == RESULT_CANCELED) {
                Toast.makeText(RecordVideo.this, "Video Recording Cancelled to :\n", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RecordVideo.this, "Failed To record Video", Toast.LENGTH_LONG).show();
            }
        }

    }
}

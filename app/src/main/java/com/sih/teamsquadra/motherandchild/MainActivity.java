package com.sih.teamsquadra.motherandchild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButtonUser;
    private ImageButton imageButtonDoctor;
    private ImageButton imageButtonEmployee;
    private Button buttonGuest;
    private Button buttonEmergency;


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        // Hook up the VideoView to our UI.
        videoBG = (VideoView) findViewById(R.id.videoView);

        // Build your video Uri
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.coffee); // and then finally add your video resource. Make sure it is stored
        // in the raw folder.

        // Set the new Uri to our VideoView
        videoBG.setVideoURI(uri);
        // Start the VideoView
        videoBG.start();

        // Set an OnPreparedListener for our VideoView. For more information about VideoViews,
        // check out the Android Docs: https://developer.android.com/reference/android/widget/VideoView.html
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });


        imageButtonUser = findViewById(R.id.imagebutton_mother);
        imageButtonDoctor = findViewById(R.id.imagebutton_doctor);
        imageButtonEmployee = findViewById(R.id.imagebutton_employee);
        buttonGuest = findViewById(R.id.button_guest);
        buttonEmergency = findViewById(R.id.button_emergency);

        imageButtonUser.setOnClickListener(this);
        imageButtonDoctor.setOnClickListener(this);
        imageButtonEmployee.setOnClickListener(this);
        buttonGuest.setOnClickListener(this);
        buttonEmergency.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
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
        } else if (v == buttonGuest) {
            //finish(); // to close current activity
            startActivity(new Intent(this, EmployeeTelemedicine.class));
        } else if (v == buttonEmergency) {
            //finish(); // to close current activity
            dialPhoneNumber("112");
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
package com.sih.teamsquadra.motherandchild;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserRemainders extends AppCompatActivity implements View.OnClickListener {

    Button buttonSendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_remainders);

        buttonSendNotification = findViewById(R.id.button_send_notifications);
        buttonSendNotification.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        // handleIntent();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSendNotification) {
            NotificationReceiver.setupAlarm(getApplicationContext());
            toastMessage(this, "Reminder Request Sent");
        }

    }

    private void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

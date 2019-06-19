package com.example.sol.sba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class NotificationActivity extends AppCompatActivity {

    private Toolbar versionToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        versionToolbar = findViewById(R.id.notification_toolbar);
        setSupportActionBar(versionToolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

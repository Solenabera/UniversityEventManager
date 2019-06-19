package com.example.sol.sba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ActivityVersion extends AppCompatActivity {
    private Toolbar versionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        versionToolbar = findViewById(R.id.version_toolbar);
        setSupportActionBar(versionToolbar);
        getSupportActionBar().setTitle("Version");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

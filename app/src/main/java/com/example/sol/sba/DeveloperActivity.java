package com.example.sol.sba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DeveloperActivity extends AppCompatActivity {
    private Toolbar developerToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        developerToolbar = findViewById(R.id.developer_toolbar);
        setSupportActionBar(developerToolbar);
        getSupportActionBar().setTitle("Developer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

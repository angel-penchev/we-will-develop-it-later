package com.wewilldevelopitlater.readability;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OpenDocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_document);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();
    }

    public void previousScreen(View v) {
        Intent intent = new Intent(OpenDocumentActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

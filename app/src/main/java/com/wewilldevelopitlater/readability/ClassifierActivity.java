package com.wewilldevelopitlater.readability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ClassifierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();
    }
}

package com.wewilldevelopitlater.readability;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SetupActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();
    }

    public void nextScreen(View v) {
        Intent intent = new Intent(SetupActivity4.this, MainActivity.class);
        startActivity(intent);
    }
}
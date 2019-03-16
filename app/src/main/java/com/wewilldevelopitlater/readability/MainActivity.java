package com.wewilldevelopitlater.readability;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();
    }

    public void nextScreen(View v) {
        Intent intent = new Intent(MainActivity.this, ClassifierActivity.class);
        startActivity(intent);
    }
}

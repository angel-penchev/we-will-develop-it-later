package com.wewilldevelopitlater.readability;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SetupScreen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_screen2);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();
    }
}
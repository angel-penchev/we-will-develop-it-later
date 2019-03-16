package com.wewilldevelopitlater.readability;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class SetupScreen2 extends AppCompatActivity {
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_screen2);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();

        // Creates the view flipper
        viewFlipper = findViewById(R.id.setup_screen_2_view_flipper);
    }

    public void previousView(View v) {
        viewFlipper.showPrevious();
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    public void nextView(View v) {
        viewFlipper.showNext();
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.side_out_left);
    }

    public void nextScreen(View v) {
        Intent intent = new Intent(SetupScreen2.this, SetupScreen3.class);
        startActivity(intent);
    }
}
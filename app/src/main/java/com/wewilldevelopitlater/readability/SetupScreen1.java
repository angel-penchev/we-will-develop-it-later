package com.wewilldevelopitlater.readability;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewFlipper;

public class SetupScreen1 extends AppCompatActivity {
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_screen1);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();

        // Creates the view flipper
        viewFlipper = findViewById(R.id.setup_screen_1_view_flipper);
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
        Intent intent = new Intent(SetupScreen1.this, FetchingActivity.class);
        startActivity(intent);
    }
}

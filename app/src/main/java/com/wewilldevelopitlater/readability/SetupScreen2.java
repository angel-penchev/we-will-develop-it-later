package com.wewilldevelopitlater.readability;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SetupScreen2 extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    public static final String PREFS = "BluetoothPrefs";
    public static final String ADDRESS = "address";
    private Button refreshBtn;
    private ListView devices;
    private BluetoothAdapter adapter;
    private Set<BluetoothDevice> bluetoothDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_screen2);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();

        // Creates the view flipper
        viewFlipper = findViewById(R.id.setup_screen_2_view_flipper);

        devices = findViewById(R.id.devices);
        refreshBtn = findViewById(R.id.refresh);

        adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter == null) {
            Log.e("adapter", "is null");
            finish();
        }

        fetchDevices();
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

    private void fetchDevices() {
        bluetoothDevices = adapter.getBondedDevices();
        List<String> deviceNames = bluetoothDevices.stream().map(BluetoothDevice::getName).collect(Collectors.toList());

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceNames);
        devices.setAdapter(listAdapter);

        devices.setOnItemClickListener((parent, view, position, id) -> {
            // 
            BluetoothDevice reader = bluetoothDevices.stream().filter(device -> device.getName().equals(deviceNames.get(position))).findFirst().get();
            
            // Storing the reader's MAC address in the "Shared Preferences"
            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("macAddressReader", reader.getAddress());
            editor.apply();
        });

    }
}
package com.wewilldevelopitlater.readability;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FetchingActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;

    private Button refreshBtn;
    private ListView devices;

    private BluetoothAdapter adapter;
    private Set<BluetoothDevice> bluetoothDevices;
    public static final String EXTRA = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_fetch);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();

        // Creates the view flipper
        viewFlipper = findViewById(R.id.view_flipper);

        devices = findViewById(R.id.devices);
        refreshBtn = findViewById(R.id.refresh);

        adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter == null) {
            Log.e("adapter","is null");
            finish();
        }

        fetchDevices();// TODO: make in async
    }

    private void fetchDevices() {
        bluetoothDevices = adapter.getBondedDevices();
        List<String> deviceNames = bluetoothDevices.stream().map(BluetoothDevice::getName).collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,deviceNames);
        devices.setAdapter(adapter);
        devices.setOnItemClickListener((parent,view,position,id)->{

            BluetoothDevice dev = bluetoothDevices.stream().filter(device->device.getName().equals(deviceNames.get(position))).findFirst().get();

            Intent i = new Intent(FetchingActivity.this,ClassifierActivity.class);
            i.putExtra(EXTRA,dev.getAddress());
            startActivity(i);
        });

    }

    public void previousView(View v) {
        viewFlipper.showPrevious();
    }

    public void nextView(View v) {
        viewFlipper.showNext();
    }
}

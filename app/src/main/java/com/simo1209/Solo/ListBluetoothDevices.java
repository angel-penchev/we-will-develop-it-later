package com.simo1209.Solo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListBluetoothDevices extends AppCompatActivity {

    public static final String PREFS = "BluetoothPrefs";
    public static final String ADDRESS = "address";
    private Button refreshBtn;
    private ListView devices;
    private BluetoothAdapter adapter;
    private Set<BluetoothDevice> bluetoothDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bluetooth_devices);

        devices = findViewById(R.id.devices);
        refreshBtn = findViewById(R.id.refresh);

        adapter = BluetoothAdapter.getDefaultAdapter();

        if (adapter == null) {
            Log.e("adapter", "is null");
            finish();
        }

        fetchDevices();
    }

    private void fetchDevices() {
        bluetoothDevices = adapter.getBondedDevices();
        List<String> deviceNames = bluetoothDevices.stream().map(BluetoothDevice::getName).collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceNames);
        devices.setAdapter(adapter);
        devices.setOnItemClickListener((parent, view, position, id) -> {

            BluetoothDevice dev = bluetoothDevices.stream().filter(device -> device.getName().equals(deviceNames.get(position))).findFirst().get();

            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ADDRESS, dev.getAddress());
            editor.apply();

            Intent i = new Intent(ListBluetoothDevices.this, MainActivity.class);
            startActivity(i);
        });

    }
}

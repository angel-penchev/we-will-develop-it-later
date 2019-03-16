package com.wewilldevelopitlater.readability;

<<<<<<< HEAD
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.camerakit.CameraKitView;

import java.io.IOException;
import java.util.UUID;

public class ClassifierActivity extends AppCompatActivity {

    private CameraKitView cameraKitView;
    private ImageView preview;
    private Button capture;
    private ProgressBar loading;

    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private String address;
    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        address = i.getStringExtra(FetchingActivity.EXTRA);
        Log.d("got device", address);

        setContentView(R.layout.activity_classifier);

        cameraKitView = findViewById(R.id.camera);
        capture = findViewById(R.id.capture);
        preview = findViewById(R.id.preview);
        loading = findViewById(R.id.progress_loader);

        if (socket == null) {
            new ConnectBT().execute();
        }


        capture.setOnClickListener(v -> {
            cameraKitView.captureImage((cameraKitView, image) -> {
                preview.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            });

            if (socket != null) {
                try {
                    int data = Integer.parseInt("010010", 2); // Binary representation of the wheels rotation
                    Log.d("writing data", String.valueOf(data));
                    socket.getOutputStream().write(data);
                    Log.d("socket", "connected");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class ConnectBT extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loading.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            adapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice d = adapter.getRemoteDevice(address);
            try {
                socket = d.createInsecureRfcommSocketToServiceRecord(uuid);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                socket.connect();
                Log.d("socket", "started");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ClassifierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);

        // Removes the Action Bar from the activity
        this.getSupportActionBar().hide();
>>>>>>> master
    }
}

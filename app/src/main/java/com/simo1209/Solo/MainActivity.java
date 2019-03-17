package com.simo1209.Solo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.camerakit.CameraKitView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    CameraKitView cameraKitView;

    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothSocket socket;
    private static String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(ListBluetoothDevices.PREFS, MODE_PRIVATE);
        address = prefs.getString(ListBluetoothDevices.ADDRESS, null);


        if (socket == null) {
            new ConnectSocket().execute();
        }

        cameraKitView = findViewById(R.id.camera);

        Button capture = findViewById(R.id.capture);
        TextView textView = findViewById(R.id.textView);

        // TODO: https://github.com/CameraKit/camerakit-android/issues/508 either fix or find better way
        capture.setOnClickListener(v -> cameraKitView.captureImage((cameraKitView, bytes) -> {

            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();

            Task<FirebaseVisionText> result = detector.processImage(image)
                    .addOnSuccessListener(firebaseVisionText -> {
                        textView.setText(firebaseVisionText.getText());
                        Log.d("Text", firebaseVisionText.getText());
                        Map<Character, String> brailleMap = new HashMap<>(); // save braille letters from assets/mapping.txt
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("mapping.txt")));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String[] split = line.split("=");
//                                Log.d("split1", split[0]);
//                                Log.d("split1", split[1]);
                                brailleMap.put(split[0].charAt(0), split[1]);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        new Thread(() -> {//Sending the word
                            int pos = 0;
                            while (pos < firebaseVisionText.getText().length()) {
                                try {
                                    String writing = brailleMap.get(firebaseVisionText.getText().toLowerCase().charAt(pos++));
                                    if (writing == null) {
                                        socket.getOutputStream().write("11".getBytes());

                                    } else {
                                        socket.getOutputStream().write(writing.getBytes()); //fixed for all letter cases
                                    }
                                    Log.d("text: ", writing);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        Thread.sleep(2000);// TODO Made it defined by user
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }).start();
                    }).addOnFailureListener(e -> Log.e("Text Recognition", "Failed"));

        }));

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
        if (!socket.isConnected()) {
            try {
                socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        if (socket.isConnected()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static class ConnectSocket extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //TODO: Create vibrating signal or other event to notify the user for successful connection
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice d = adapter.getRemoteDevice(address);
            try {
                socket = d.createInsecureRfcommSocketToServiceRecord(uuid);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

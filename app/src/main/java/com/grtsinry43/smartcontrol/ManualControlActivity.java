package com.grtsinry43.smartcontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManualControlActivity extends AppCompatActivity {
    private BluetoothService bluetoothService;
    private ImageView forwardButton;
    private ImageView backwardButton;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView stopButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_control);

        Intent intent = getIntent();
        String deviceAddress = intent.getStringExtra("device_address");
        if (deviceAddress != null) {
            bluetoothService = BluetoothService.getInstance(getApplicationContext(), deviceAddress);
        } else {
            // Handle the case where deviceAddress is null
        }

        forwardButton = findViewById(R.id.forward_button);
        backwardButton = findViewById(R.id.backward_button);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        stopButton = findViewById(R.id.stop_button);

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothService != null) {
                    bluetoothService.write(hexStringToByteArray("01"));
                } else {
                    // Display an error message
                    Toast.makeText(ManualControlActivity.this, "BluetoothService is not initialized", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothService != null) {
                    bluetoothService.write(hexStringToByteArray("02"));
                } else {
                    // Display an error message
                    Toast.makeText(ManualControlActivity.this, "BluetoothService is not initialized", Toast.LENGTH_SHORT).show();
                }
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothService != null) {
                    bluetoothService.write(hexStringToByteArray("03"));
                } else {
                    // Display an error message
                    Toast.makeText(ManualControlActivity.this, "BluetoothService is not initialized", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothService != null) {
                    bluetoothService.write(hexStringToByteArray("04"));
                } else {
                    // Display an error message
                    Toast.makeText(ManualControlActivity.this, "BluetoothService is not initialized", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothService != null) {
                    bluetoothService.write(hexStringToByteArray("05"));
                } else {
                    // Display an error message
                    Toast.makeText(ManualControlActivity.this, "BluetoothService is not initialized", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView imageView = findViewById(R.id.imageView14);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
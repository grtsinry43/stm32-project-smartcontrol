package com.grtsinry43.smartcontrol;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.UUID;

public class BluetoothService {
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private BluetoothSocket mmSocket;

    private MutableLiveData<String> receivedData = new MutableLiveData<>();

    private BluetoothService(Context context, String deviceAddress) {
        bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager != null) {
            bluetoothAdapter = bluetoothManager.getAdapter();
        }
        if (bluetoothAdapter != null) {
            device = bluetoothAdapter.getRemoteDevice(deviceAddress);
        }
        if (device != null) {
            try {
                mmSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }
    }

    public static BluetoothService getInstance(Context context, String deviceAddress) {
        return new BluetoothService(context, deviceAddress);
    }

    public void connect() {
        try {
            if (mmSocket != null) {
                mmSocket.connect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    public void write(byte[] bytes) {
        try {
            if (mmSocket != null && mmSocket.getOutputStream() != null) {
                mmSocket.getOutputStream().write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    public void cancel() {
        try {
            if (mmSocket != null) {
                mmSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    public void startListening() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            // Read from the InputStream
            try {
                if (mmSocket != null && mmSocket.getInputStream() != null) {
                    bytes = mmSocket.getInputStream().read(buffer);
                    // Send the obtained bytes to the UI activity
                    // Here you should implement your logic to process received bytes
                }
            } catch (IOException e) {
                break;
            }
        }
    }

    public String getDeviceAddress() {
        return device != null ? device.getAddress() : "";
    }

    public MutableLiveData<String> getReceivedData() {
        return receivedData;
    }
}

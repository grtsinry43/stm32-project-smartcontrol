package com.grtsinry43.smartcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private BluetoothService bluetoothService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dateTextView = findViewById(R.id.textView2);
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date date = new Date();
        dateTextView.setText(dateFormat.format(date));

        //点击头像，打开关于页面
        ImageView imageView1 = findViewById(R.id.imageView);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), AboutActivity.class);
                startActivity(intent1);
            }
        });

        //点击通知，打开通知页面
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: 实现这个界面
//                TODO: 实现接到对应的HEX码后，弹出通知,并返回回应指令
            }
        });

        //点击右上角，打开功能页面
        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: 实现这个界面
            }
        });

        //点击加号，打开蓝牙连接页面
        ImageView imageView4 = findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), DeviceList.class);
                startActivity(intent2);
            }
        });

        //功能一，发送Hex码即可
        CardView cardView1 = findViewById(R.id.button1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothService != null) {
                    bluetoothService.write(hexStringToByteArray("05"));
                } else {
                    Toast.makeText(MainActivity.this, "请先连接设备哦~", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //功能二，打开新页面
        CardView cardView2 = findViewById(R.id.button2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(v.getContext(), ManualControlActivity.class);
                startActivity(intent3);
            }
        });

        //功能三，打开新页面
        CardView cardView3 = findViewById(R.id.button3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(v.getContext(), ScanQRCodeActivity.class);
                startActivity(intent4);
            }
        });

        //功能四，发送Hex码并打开新页面
        CardView cardView4 = findViewById(R.id.button4);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String deviceAddress = data != null ? data.getStringExtra("device_address") : null;
            if (deviceAddress != null) {
                bluetoothService = BluetoothService.getInstance(this, deviceAddress);
                bluetoothService.connect();
            }
        }
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
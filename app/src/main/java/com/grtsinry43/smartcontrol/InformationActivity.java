package com.grtsinry43.smartcontrol;

import static com.grtsinry43.smartcontrol.MainActivity.hexStringToByteArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class InformationActivity extends AppCompatActivity implements BluetoothService.BluetoothDataListener{

    private TextView textView;
    private ImageView imageView;
    private CardView cardView1;
    private BluetoothService bluetoothService;
    public String message1 = "到达第一个节点";
    public String message2 = "到达第二个节点";
    public String message3 = "到达第三个节点";
    public String message4 = "到达第四个节点";
    public String message5 = "到达第五个节点";
    public String message6 = "到达第六个节点";
    public String message7 = "已完成";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        textView = findViewById(R.id.ready_information);
        imageView = findViewById(R.id.status_icon);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date time = new Date();

        Intent intent = getIntent();
        String deviceAddress = intent.getStringExtra("device_address");

        if (deviceAddress != null) {
            bluetoothService = BluetoothService.getInstance(getApplicationContext(), deviceAddress);
        } else {
            // Handle the case where deviceAddress is null
            Toast.makeText(InformationActivity.this, "请先连接设备哦~", Toast.LENGTH_SHORT).show();
        }

        if (bluetoothService != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("设备已就绪");
                    imageView.setImageDrawable(getDrawable(R.drawable.ready));
                    cardView1.setCardBackgroundColor(getColor(R.color.light_blue));
                    sendMessage1();
                }
            });
        }

        //处理startListening收到的信息
        bluetoothService.getReceivedData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String newData) {
                // 在这里处理收到的新数据，更新界面
                updateUIWithData(newData);
            }

            private void updateUIWithData(String newData) {
                if (newData.equals("0x03")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用新的 CardView 更新用户界面，其中包含图像、文本和时间
                            addInformationCard(message1, time);
                        }
                    });
                }
                if (newData.equals("0x06")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用新的 CardView 更新用户界面，其中包含图像、文本和时间
                            addInformationCard(message2, time);
                        }
                    });
                }
                if (newData.equals("0x12")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用新的 CardView 更新用户界面，其中包含图像、文本和时间
                            addInformationCard(message3, time);
                            sendMessage2();
                        }
                    });
                }
                if (newData.equals("0x13")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用新的 CardView 更新用户界面，其中包含图像、文本和时间
                            addInformationCard(message4, time);
                        }
                    });
                }
                if (newData.equals("0x16")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用新的 CardView 更新用户界面，其中包含图像、文本和时间
                            addInformationCard(message5, time);
                        }
                    });
                }
                if (newData.equals("0x17")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用新的 CardView 更新用户界面，其中包含图像、文本和时间
                            addInformationCard(message6, time);
                        }
                    });
                }
                if (newData.equals("0x01")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用新的 CardView 更新用户界面，其中包含图像、文本和时间
                            addInformationCard(message7, time);
                        }
                    });
                }
            }

            private void addInformationCard(String messageShow, Date timeShow){
                // 动态创建新的 CardView 并添加到布局中
                CardView notificationCard = new CardView(InformationActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(20, 20, 20, 20);
                notificationCard.setLayoutParams(params);
                notificationCard.setCardBackgroundColor(Color.BLUE); // 设置您想要的颜色

                // 创建 CardView 的布局（ImageView、TextView 和时间 TextView）
                LinearLayout layout = new LinearLayout(InformationActivity.this);
                layout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));
                layout.setOrientation(LinearLayout.HORIZONTAL);

                // ImageView 用于图标
                ImageView icon = new ImageView(InformationActivity.this);
                icon.setLayoutParams(new LinearLayout.LayoutParams(
                        20,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                icon.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                icon.setImageResource(R.drawable.right1); // 设置您想要的图像
                layout.addView(icon);

                // TextView 用于消息
                TextView message = new TextView(InformationActivity.this);
                LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                messageParams.setMargins(10, 0, 0, 0);
                message.setLayoutParams(messageParams);
                message.setText(messageShow); // 设置您想要的消息
                layout.addView(message);

                // TextView 用于时间
                TextView time = new TextView(InformationActivity.this);
                time.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                time.setGravity(Gravity.END);
                timeShow = new Date();
                time.setText(dateFormat.format(timeShow)); // 设置您想要的时间
                layout.addView(time);

                notificationCard.addView(layout);

                // 将 CardView 添加到您的布局中
                LinearLayout mainLayout = findViewById(R.id.info_scrollView); // 用您的主布局 ID 替换
                mainLayout.addView(notificationCard);
            }

        });


    }

    @Override
    public void onDataReceived(String newData) {

    }

    public void sendMessage1() {
        if (bluetoothService != null) {
            bluetoothService.write(hexStringToByteArray("AAAABBAAAA"));
        } else {
            Toast.makeText(InformationActivity.this, "请先连接设备哦~", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage2() {
        if (bluetoothService != null) {
            bluetoothService.write(hexStringToByteArray("AAAABBAABB"));
        } else {
            Toast.makeText(InformationActivity.this, "请先连接设备哦~", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.grtsinry43.smartcontrol;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DisplayJsonActivity extends AppCompatActivity {
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_json);

        textView = findViewById(R.id.box_information);

        String url = getIntent().getStringExtra("url");
        if (url != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL jsonUrl = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) jsonUrl.openConnection();
                        connection.setRequestMethod("GET");

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        Gson gson = new Gson();
                        DataModel dataModel = gson.fromJson(response.toString(), DataModel.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(dataModel.toString());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        ImageView imageView = findViewById(R.id.imageView14);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

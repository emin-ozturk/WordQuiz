package com.example.wordquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    final int SCORE = 1;
    final int MONEY = 2;
    TextView txtAppName, txtMoney, txtScore;
    Typeface typeface;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();
        txtAppName.setTypeface(typeface);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                List<String> list = db.list();
                String[] item = list.get(0).split(" - ");

                txtScore.setText("Puan: " + item[SCORE]);
                txtMoney.setText(item[MONEY]);
            }
        }
    };

    private void load() {
        txtAppName = findViewById(R.id.txtAppName);
        txtScore = findViewById(R.id.txtScore);
        txtMoney = findViewById(R.id.txtMoney);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        db = new DataBase(this);
    }

    public void levels(View v) {
        Intent goLevelPage = new Intent(this, LevelPage.class);
        startActivity(goLevelPage);
    }

    public void missions(View v) {
        Intent goSuccessPage = new Intent(this, MissionPage.class);
        startActivity(goSuccessPage);
    }

    public void settings(View v) {
        Intent goSettingsPage = new Intent(this, SettingsPage.class);
        startActivity(goSettingsPage);
    }
}

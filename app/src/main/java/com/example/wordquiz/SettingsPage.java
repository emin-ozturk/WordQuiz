package com.example.wordquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SettingsPage extends AppCompatActivity {
    TextView txtSoundStatus;
    CheckBox cBoxSound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        txtSoundStatus = findViewById(R.id.txtSoundStatus);
        cBoxSound = findViewById(R.id.cBoxSound);

        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");
        if (Integer.parseInt(item[27]) == 1) { //1 = sound
            txtSoundStatus.setText("Ses açık");
            cBoxSound.setChecked(true);
            db.updateSound(1);
        } else if (Integer.parseInt(item[27]) == 0) { //0 = not sound
            txtSoundStatus.setText("Ses kapalı");
            cBoxSound.setChecked(false);
            db.updateSound(0);
        }
    }

    public void sound(View v) {
        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");
        if (Integer.parseInt(item[27]) == 1) { //1 = sound
            txtSoundStatus.setText("Ses kapalı");
            cBoxSound.setChecked(false);
            db.updateSound(0);
        } else if (Integer.parseInt(item[27]) == 0) { //0 = not sound
            txtSoundStatus.setText("Ses açık");
            cBoxSound.setChecked(true);
            db.updateSound(1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package com.example.wordquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MissionPage extends AppCompatActivity {
    final List<Mission> missions = new ArrayList<>();
    ListView listMission;

    final int MISSIONEASYONE = 30; //Kolay seviyede 30 yıldız topla
    final int MISSIONEASYTWO = 100; //Kolay seviyede 100 soruyu doğru cevapla
    final int MISSIONEASYTHREE = 600; //Kolay seviyede 600 altın kazan
    final int MISSIONEASYFOUR = 10; //Kolay seviyede 10 kez jokersiz tüm soruları doğru cevapla

    final int MISSIONMEDIUMONE = 50; //Orta seviyede 50 yıldız topla
    final int MISSIONMEDIUMTWO = 200; //Orta seviyede 200 soruyu doğru cevapla
    final int MISSIONMEDIUMTHREE = 750; //Orta seviyede 750 altın kazan
    final int MISSIONMEDIUMFOUR = 15; //Orta seviyede 15 kez jokersiz tüm soruları doğru cevapla

    final int MISSIONHARDONE = 75; //Zor seviyede 75 yıldız topla
    final int MISSIONHARDTWO = 300; //Zor seviyede 300 soruyu doğru cevapla
    final int MISSIONHARDTHREE = 1000; //Zor seviyede 1000 altın kazan
    final int MISSIONHARDFOUR = 20; //Zor seviyede 20 kez jokersiz tüm soruları doğru cevapla

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_page);

        listMission = findViewById(R.id.listSuccess);

        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");

        missions.add(new Mission(Integer.parseInt(item[3]) >= MISSIONEASYONE, "Kolay seviyede 30 yıldız topla", item[3] + "/" + MISSIONEASYONE, MISSIONEASYONE, Integer.parseInt(item[3])));
        missions.add(new Mission(Integer.parseInt(item[4]) >= MISSIONEASYTWO, "Kolay seviyede 100 soruyu doğru cevapla", item[4] + "/" + MISSIONEASYTWO, MISSIONEASYTWO, Integer.parseInt(item[4])));
        missions.add(new Mission(Integer.parseInt(item[5]) >= MISSIONEASYTHREE, "Kolay seviyede 600 altın kazan", item[5] + "/" + MISSIONEASYTHREE, MISSIONEASYTHREE, Integer.parseInt(item[5])));
        missions.add(new Mission(Integer.parseInt(item[6]) >= MISSIONEASYFOUR, "Kolay seviyede 10 kez jokersiz tüm soruları doğru cevapla", item[6] + "/" + MISSIONEASYFOUR, MISSIONEASYFOUR, Integer.parseInt(item[6])));

        missions.add(new Mission(Integer.parseInt(item[7]) >= MISSIONMEDIUMONE, "Orta seviyede 50 yıldız topla", item[7] + "/" + MISSIONMEDIUMONE, MISSIONMEDIUMONE, Integer.parseInt(item[7])));
        missions.add(new Mission(Integer.parseInt(item[8]) >= MISSIONMEDIUMTWO, "Orta seviyede 200 soruyu doğru cevapla", item[8] + "/" + MISSIONMEDIUMTWO, MISSIONMEDIUMTWO, Integer.parseInt(item[8])));
        missions.add(new Mission(Integer.parseInt(item[9]) >= MISSIONMEDIUMTHREE, "Orta seviyede 750 altın kazan", item[9] + "/" + MISSIONMEDIUMTHREE, MISSIONMEDIUMTHREE, Integer.parseInt(item[9])));
        missions.add(new Mission(Integer.parseInt(item[10]) >= MISSIONMEDIUMFOUR, "Orta seviyede 15 kez jokersiz tüm soruları doğru cevapla", item[10] + "/" + MISSIONMEDIUMFOUR, MISSIONMEDIUMFOUR, Integer.parseInt(item[10])));

        missions.add(new Mission(Integer.parseInt(item[11]) >= MISSIONHARDONE, "Zor seviyede 75 yıldız topla", item[11] + "/" + MISSIONHARDONE, MISSIONHARDONE, Integer.parseInt(item[11])));
        missions.add(new Mission(Integer.parseInt(item[12]) >= MISSIONHARDTWO, "Zor seviyede 300 soruyu doğru cevapla", item[12] + "/" + MISSIONHARDTWO, MISSIONHARDTWO, Integer.parseInt(item[12])));
        missions.add(new Mission(Integer.parseInt(item[13]) >= MISSIONHARDTHREE, "Zor seviyede 1000 altın kazan", item[13] + "/" + MISSIONHARDTHREE, MISSIONHARDTHREE, Integer.parseInt(item[13])));
        missions.add(new Mission(Integer.parseInt(item[14]) >= MISSIONHARDFOUR, "Zor seviyede 20 kez jokersiz tüm soruları doğru cevapla", item[14] + "/" + MISSIONHARDFOUR, MISSIONHARDFOUR, Integer.parseInt(item[14])));

        CustomAdapterMission adapter = new CustomAdapterMission(this, missions);
        listMission.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

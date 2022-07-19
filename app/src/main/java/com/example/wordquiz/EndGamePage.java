package com.example.wordquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EndGamePage extends AppCompatActivity {
    ImageView imgStarOne, imgStarTwo, imgStarThree;
    TextView txtStatus, txtSuccess, txtMoney, txtScore;
    int money;
    int score;
    int star;
    int[] st;
    final int SCORE = 1;
    final int MONEY = 2;

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

    MediaPlayer mediaPlayer;
    DataBase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame_page);

        load();

        st = getIntent().getIntArrayExtra("data");
        status(st);

        db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");
        updateMoney(Integer.parseInt(item[2]));
        updateScore(item, st[1]);

        if (Integer.parseInt(item[27]) == 1) mediaPlayer.start(); //0 = not sound, 1 = sound
        if (st[0] != -1) controlMission(st[2]);

        missionReward();
    }

    private void load() {
        txtStatus = findViewById(R.id.txtStatus);
        txtSuccess = findViewById(R.id.txtSuccess);
        txtMoney = findViewById(R.id.txtMoney);
        imgStarOne = findViewById(R.id.imgStarOne);
        imgStarTwo = findViewById(R.id.imgStarTwo);
        imgStarThree = findViewById(R.id.imgStarThree);
        txtScore = findViewById(R.id.txtScore);

        mediaPlayer = MediaPlayer.create(this, R.raw.endgame_sound);
    }

    private void updateScore(String[] item, int st) {
        int s = Integer.parseInt(item[1]);
        score = st;

        if (score > s) {
            db.update(SCORE, score);
            txtScore.setText("Yeni Rekor: " + score);
        } else {
            txtScore.setText("Puan: " + score);
        }
    }

    private void updateMoney(int m) {
        money += m;
        db.update(MONEY, money);
    }

    private void status(int[] st) {
        if (st[0] == -1) {
            txtStatus.setText("Daha Çok Çalışmalısın");
            txtSuccess.setTextColor(getResources().getColor(R.color.colorAccent));
            txtSuccess.setText("Süre Bitti");
        } else if (st[0] >= 0 && st[0] <= 4) {
            txtStatus.setText("Daha İyi Yapabilirsin");
            txtSuccess.setText(st[0] + "/10 Başarı");
            txtSuccess.setTextColor(getResources().getColor(R.color.colorWhite));
        } else if (st[0] == 5 || st[0] == 6) {
            txtStatus.setText("Hiç Fena Değilsin");
            txtSuccess.setText(st[0] + "/10 Başarı");
            txtSuccess.setTextColor(getResources().getColor(R.color.colorWhite));
        } else if (st[0] == 7 || st[0] == 9) {
            txtStatus.setText("Çok İyisin");
            txtSuccess.setText(st[0] + "/10 Başarı");
            txtSuccess.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (st[0] == 10) {
            txtStatus.setText("Mükemmel");
            txtSuccess.setText(st[0] + "/10 Başarı");
            txtSuccess.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        money = st[0] * 5 < 0 ? 0 : st[0] * 5;

        txtMoney.setText(String.valueOf(money));

        loadImage(st[0]);
    }

    private void loadImage(int st) {
        if (st == -1) { //0 star
            imgStarOne.setVisibility(View.INVISIBLE);
            imgStarTwo.setImageResource(R.drawable.hourglass);
            imgStarThree.setVisibility(View.INVISIBLE);
            star = 0;
        } else if (st == 0) {
            imgStarOne.setVisibility(View.INVISIBLE);
            imgStarTwo.setImageResource(R.drawable.iconssad);
            imgStarThree.setVisibility(View.INVISIBLE);
            star = 0;
        } else if (st >= 1 && st <= 4) { //1 star
            imgStarOne.setVisibility(View.INVISIBLE);
            imgStarThree.setVisibility(View.INVISIBLE);
            star = 1;
        } else if (st >= 5 && st <= 7) { //2 stars
            imgStarTwo.setVisibility(View.INVISIBLE);
            imgStarTwo.getLayoutParams().width = 0;
            star = 2;
        } else { //3 stars
            star = 3;
        }
    }

    public void newGame(View view) {
        Intent goQuestionPage = new Intent(this, QuestionPage.class);
        goQuestionPage.putExtra("level", st[2]);
        startActivity(goQuestionPage);
        finish();
    }

    public void close(View view) {
        finish();
    }

    private void controlMission(int level) {
        if (level == 1) controlLevelOne();
        if (level == 2) controlLevelTwo();
        if (level == 3) controlLevelThree();
    }

    private void controlLevelOne() {
        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");

        if (Integer.parseInt(item[3]) + star < MISSIONEASYONE) {
            db.update(3, Integer.parseInt(item[3]) + star);
        } else {
            db.update(3, MISSIONEASYONE);
        }

        if (Integer.parseInt(item[4]) + st[0] < MISSIONEASYTWO) {
            db.update(4, Integer.parseInt(item[4]) + st[0]);
        } else {
            db.update(4, MISSIONEASYTWO);
        }

        if (Integer.parseInt(item[5]) + st[0] * 5 < MISSIONEASYTHREE) {
            db.update(5, Integer.parseInt(item[5]) + st[0] * 5);
        } else {
            db.update(5, MISSIONEASYTHREE);
        }

        if (st[3] == 1 && st[0] == 10) {
            if (Integer.parseInt(item[6]) + 1 < MISSIONEASYFOUR) {
                db.update(6, Integer.parseInt(item[6]) + 1);
            } else {
                db.update(6, MISSIONEASYFOUR);
            }
        }
    }

    private void controlLevelTwo() {
        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");

        if (Integer.parseInt(item[7]) + star < MISSIONMEDIUMONE) {
            db.update(7, Integer.parseInt(item[7]) + star);
        } else {
            db.update(7, MISSIONMEDIUMONE);
        }

        if (Integer.parseInt(item[8]) + st[0] < MISSIONMEDIUMTWO) {
            db.update(8, Integer.parseInt(item[8]) + st[0]);
        } else {
            db.update(8, MISSIONMEDIUMTWO);
        }

        if (Integer.parseInt(item[9]) + st[0] * 5 < MISSIONMEDIUMTHREE) {
            db.update(9, Integer.parseInt(item[9]) + st[0] * 5);
        } else {
            db.update(9, MISSIONMEDIUMTHREE);
        }

        if (st[3] == 1 && st[0] == 10) {
            if (Integer.parseInt(item[10]) + 1 < MISSIONMEDIUMFOUR) {
                db.update(10, Integer.parseInt(item[10]) + 1);
            } else {
                db.update(10, MISSIONMEDIUMFOUR);
            }
        }
    }

    private void controlLevelThree() {
        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");

        if (Integer.parseInt(item[11]) + star < MISSIONHARDONE) {
            db.update(11, Integer.parseInt(item[11]) + star);
        } else {
            db.update(11, MISSIONHARDONE);
        }

        if (Integer.parseInt(item[12]) + st[0] < MISSIONHARDTWO) {
            db.update(12, Integer.parseInt(item[12]) + st[0]);
        } else {
            db.update(12, MISSIONHARDTWO);
        }

        if (Integer.parseInt(item[13]) + st[0] * 5 < MISSIONHARDTHREE) {
            db.update(13, Integer.parseInt(item[13]) + st[0] * 5);
        } else {
            db.update(13, MISSIONHARDTHREE);
        }

        if (st[3] == 1 && st[0] == 10) {
            if (Integer.parseInt(item[14]) + 1 < MISSIONHARDFOUR) {
                db.update(14, Integer.parseInt(item[14]) + 1);
                db.update(14, 20);
            } else {
                db.update(14, MISSIONHARDFOUR);
            }
        }
    }

    private void rewardPage(String mission, int rewardMoney) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.mission_completed, null);

        TextView txtMission = view.findViewById(R.id.txtMission);
        TextView txtMoney = view.findViewById(R.id.txtMoney);
        Button btnReward = view.findViewById(R.id.btnReward);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        txtMission.setText(mission);
        txtMoney.setText(String.valueOf(rewardMoney));
        updateMoney(rewardMoney);
        btnReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void missionReward() {
        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");
        if (Integer.parseInt(item[3]) == MISSIONEASYONE && Integer.parseInt(item[15]) == 0) { // 0 = false, 1 = true
            rewardPage("Kolay seviyede 30 yıldız topla", 400);
            db.updateMissionComplete(1);
        }
        if (Integer.parseInt(item[4]) == MISSIONEASYTWO && Integer.parseInt(item[16]) == 0) {
            rewardPage("Kolay seviyede 100 soruyu doğru cevapla", 00);
            db.updateMissionComplete(2);
        }
        if (Integer.parseInt(item[5]) == MISSIONEASYTHREE && Integer.parseInt(item[17]) == 0) {
            rewardPage("Kolay seviyede 600 altın kazan", 600);
            db.updateMissionComplete(3);
        }
        if (Integer.parseInt(item[6]) == MISSIONEASYFOUR && Integer.parseInt(item[18]) == 0) {
            rewardPage("Kolay seviyede 10 kez jokersiz tüm soruları doğru cevapla", 600);
            db.updateMissionComplete(4);
        }

        if (Integer.parseInt(item[7]) == MISSIONMEDIUMONE && Integer.parseInt(item[19]) == 0) {
            rewardPage("Orta seviyede 50 yıldız topla", 800);
            db.updateMissionComplete(5);
        }
        if (Integer.parseInt(item[8]) == MISSIONMEDIUMTWO && Integer.parseInt(item[20]) == 0) {
            rewardPage("Orta seviyede 200 soruyu doğru cevapla", 800);
            db.updateMissionComplete(6);
        }
        if (Integer.parseInt(item[9]) == MISSIONMEDIUMTHREE && Integer.parseInt(item[21]) == 0) {
            rewardPage("Orta seviyede 750 altın kazan", 1000);
            db.updateMissionComplete(7);
        }
        if (Integer.parseInt(item[10]) == MISSIONMEDIUMFOUR && Integer.parseInt(item[22]) == 0) {
            rewardPage("Orta seviyede 15 kez jokersiz tüm soruları doğru cevapla", 1000);
            db.updateMissionComplete(8);
        }

        if (Integer.parseInt(item[11]) == MISSIONHARDONE && Integer.parseInt(item[23]) == 0) {
            rewardPage("Zor seviyede 75 yıldız topla", 1200);
            db.updateMissionComplete(9);
        }
        if (Integer.parseInt(item[12]) == MISSIONHARDTWO && Integer.parseInt(item[24]) == 0) {
            rewardPage("Zor seviyede 300 soruyu doğru cevapla", 1200);
            db.updateMissionComplete(10);
        }
        if (Integer.parseInt(item[13]) == MISSIONHARDTHREE && Integer.parseInt(item[25]) == 0) {
            rewardPage("Zor seviyede 1000 altın kazan", 1500);
            db.updateMissionComplete(11);
        }
        if (Integer.parseInt(item[14]) == MISSIONHARDFOUR && Integer.parseInt(item[26]) == 0) {
            rewardPage("Zor seviyede 20 kez jokersiz tüm soruları doğru cevapla", 1500);
            db.updateMissionComplete(12);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

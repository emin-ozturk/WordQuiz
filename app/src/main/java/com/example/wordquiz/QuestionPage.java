package com.example.wordquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

public class QuestionPage extends AppCompatActivity {
    TextView txtQuestionNow, txtQuestionTotal, txtWord;
    TextView txtOptionA, txtOptionB, txtOptionC, txtOptionD;
    TextView txtTime, txtScore, txtMoney;
    LinearLayout layoutOptionA, layoutOptionB, layoutOptionC, layoutOptionD;
    LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10;
    LinearLayout layoutFiftyFifty, layoutInsertTen, layoutSkip, layoutCorrectAnswer;
    int questionNow = 1;
    int pBarStatus = 0;
    int millis;
    int correct;
    int time;
    int score = 0;
    int levelTime;
    int level;
    int isUsingJoker = 1; //0 = Using joker, 1 = not using joker
    int[] timeOver;
    final int MAXQUESTION = 10;
    final int MAXTIME = 600;
    final int NEXTQUESTION = 600; //bar
    final int MONEY = 2;
    final int PRICEFIFTYFIFTY = 100;
    final int PRICEINSERTTEN = 150;
    final int PRICESKIP = 200;
    final int PRICECORRECTANSWER = 250;
    boolean isCorrect;
    boolean newQuestion = true;
    boolean isClose = false;
    boolean isJokerFiftyFifty, isJokerInsertTen, isJokerSkip, isJokerCorrectAnswer;
    boolean[] isOptionDelete = {false, false, false, false};
    ProgressBar pBarCircle;
    MediaPlayer correctOptionSound, wrongOptionSound;
    Question question;
    String[] item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);

        level = getIntent().getIntExtra("level", 0);
        if (level == 1) levelTime = 45; //seconds
        if (level == 2) levelTime = 30; //seconds
        if (level == 3) levelTime = 20; //seconds
        timeOver = new int[]{-1, 0, level, 0}; //-1 = time over, 0 = score, level = level, 0 = using joker


        /*
         * 600 / 30 = 20   MAXTIME / levelTime = x
         * 1000 / 20 = 50  1000 / x = millis
         */
        millis = 1000 / (MAXTIME / levelTime);
        time = levelTime;

        question = new Question(level);
        question.newQuestion();

        load();
        questionWrite();
        questionBarTime();
        questionTime();

        DataBase db = new DataBase(this);
        List<String> list = db.list();
        item = list.get(0).split(" - ");
        txtMoney.setText(item[MONEY]);

        if (getMoney() - PRICEFIFTYFIFTY < 0) jokerDisable(0);
        if (getMoney() - PRICEINSERTTEN < 0) jokerDisable(1);
        if (getMoney() - PRICESKIP < 0) jokerDisable(2);
        if (getMoney() - PRICECORRECTANSWER < 0) jokerDisable(3);
    }

    private void load() {
        txtQuestionNow = findViewById(R.id.txtQuestionNow);
        txtQuestionTotal = findViewById(R.id.txtQuestionTotal);
        txtWord = findViewById(R.id.txtWord);
        txtOptionA = findViewById(R.id.txtOptionA);
        txtOptionB = findViewById(R.id.txtOptionB);
        txtOptionC = findViewById(R.id.txtOptionC);
        txtOptionD = findViewById(R.id.txtOptionD);
        txtTime = findViewById(R.id.txtTime);
        txtScore = findViewById(R.id.txtScore);
        txtMoney = findViewById(R.id.txtMoney);

        layoutOptionA = findViewById(R.id.layoutOptionA);
        layoutOptionB = findViewById(R.id.layoutOptionB);
        layoutOptionC = findViewById(R.id.layoutOptionC);
        layoutOptionD = findViewById(R.id.layoutOptionD);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        layout6 = findViewById(R.id.layout6);
        layout7 = findViewById(R.id.layout7);
        layout8 = findViewById(R.id.layout8);
        layout9 = findViewById(R.id.layout9);
        layout10 = findViewById(R.id.layout10);
        layoutFiftyFifty = findViewById(R.id.layoutFiftyFifty);
        layoutInsertTen = findViewById(R.id.layoutInsertTen);
        layoutSkip = findViewById(R.id.layoutSkip);
        layoutCorrectAnswer = findViewById(R.id.layoutCorrectAnswer);

        pBarCircle = findViewById(R.id.pBarCircle);

        correctOptionSound = MediaPlayer.create(this, R.raw.correct_option_sound);
        wrongOptionSound = MediaPlayer.create(this, R.raw.wrong_option_sound);
    }

    private void questionWrite() {
        txtWord.setText(question.getQuestion());
        txtOptionA.setText(question.getOptions()[1]);
        txtOptionB.setText(question.getOptions()[2]);
        txtOptionC.setText(question.getOptions()[3]);
        txtOptionD.setText(question.getOptions()[4]);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                txtTime.setText(String.valueOf(time));
                time--;
            }
            if (msg.what == 1) {
                pBarCircle.setProgress(MAXTIME - pBarStatus);
                pBarStatus--;
            }
        }
    };

    private void questionBarTime() {
        pBarStatus = MAXTIME;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pBarStatus > 0 && !isClose) {
                    handler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (pBarStatus == 0) {
                    Intent goEngGamePage = new Intent(QuestionPage.this, EndGamePage.class);
                    goEngGamePage.putExtra("data", timeOver);
                    startActivity(goEngGamePage);
                    finish();
                }
            }
        }).start();
    }

    private void questionTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (time >= 0) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void resetQuestion() {
        layoutOptionA.setBackgroundResource(R.drawable.option_border);
        txtOptionA.setTextColor(getResources().getColor(R.color.colorWhite));

        layoutOptionB.setBackgroundResource(R.drawable.option_border);
        txtOptionB.setTextColor(getResources().getColor(R.color.colorWhite));

        layoutOptionC.setBackgroundResource(R.drawable.option_border);
        txtOptionC.setTextColor(getResources().getColor(R.color.colorWhite));

        layoutOptionD.setBackgroundResource(R.drawable.option_border);
        txtOptionD.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void nextQuestion() {
        if (questionNow < MAXQUESTION) {
            for (int i = 0; i < isOptionDelete.length; i++) isOptionDelete[i] = false;
            questionNow++;
            txtQuestionNow.setText(questionNow < 10 ? "0" + questionNow : String.valueOf(questionNow));
            newQuestion = true;
            resetQuestion();
            question.newQuestion();
            questionWrite();
        } else {
            isClose = true;
            int[] ar = {correct, score, level, isUsingJoker};
            Intent goEndGamePage = new Intent(this, EndGamePage.class);
            goEndGamePage.putExtra("data", ar);
            startActivity(goEndGamePage);
            finish();
        }
    }

    private void correctOrWrong(boolean isCorrect) {
        switch (questionNow) {
            case 1:
                if (isCorrect)
                    layout1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout1.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 2:
                if (isCorrect)
                    layout2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout2.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 3:
                if (isCorrect)
                    layout3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout3.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 4:
                if (isCorrect)
                    layout4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout4.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 5:
                if (isCorrect)
                    layout5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout5.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 6:
                if (isCorrect)
                    layout6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout6.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 7:
                if (isCorrect)
                    layout7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout7.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 8:
                if (isCorrect)
                    layout8.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout8.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 9:
                if (isCorrect)
                    layout9.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout9.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 10:
                if (isCorrect)
                    layout10.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else layout10.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
        newQuestion = false;
    }

    public void answer(View v) {
        int index = 0;
        switch (v.getId()) {
            case R.id.layoutOptionA:
                index = 0;
                break;
            case R.id.layoutOptionB:
                index = 1;
                break;
            case R.id.layoutOptionC:
                index = 2;
                break;
            case R.id.layoutOptionD:
                index = 3;
                break;
        }
        if (newQuestion && !isOptionDelete[index]) {
            if (question.getAnswer() == index) {
                correctAnswer(index);
                correct++;
                isCorrect = true;
                if (Integer.parseInt(item[27]) == 1) correctOptionSound.start(); //0 = not sound, 1 = sound
                score += 50 * level;
            } else {
                correctAnswer(question.getAnswer());
                wrongAnswer(index);
                isCorrect = false;
                if (Integer.parseInt(item[27]) == 1) wrongOptionSound.start(); //0 = not sound, 1 = sound
                score = score - (20 * level) < 0 ? 0 : score - (20 * level);
            }
            correctOrWrong(isCorrect);
            txtScore.setText("Puan: " + score);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                }
            }, NEXTQUESTION);
        }
    }

    private void correctAnswer(int opt) {
        switch (opt) {
            case 0:
                layoutOptionA.setBackgroundResource(R.drawable.option_border_correct);
                txtOptionA.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                layoutOptionB.setBackgroundResource(R.drawable.option_border_correct);
                txtOptionB.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                layoutOptionC.setBackgroundResource(R.drawable.option_border_correct);
                txtOptionC.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                layoutOptionD.setBackgroundResource(R.drawable.option_border_correct);
                txtOptionD.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    private void wrongAnswer(int opt) {
        switch (opt) {
            case 0:
                layoutOptionA.setBackgroundResource(R.drawable.option_border_wrong);
                txtOptionA.setTextColor(getResources().getColor(R.color.colorRed));
                break;
            case 1:
                layoutOptionB.setBackgroundResource(R.drawable.option_border_wrong);
                txtOptionB.setTextColor(getResources().getColor(R.color.colorRed));
                break;
            case 2:
                layoutOptionC.setBackgroundResource(R.drawable.option_border_wrong);
                txtOptionC.setTextColor(getResources().getColor(R.color.colorRed));
                break;
            case 3:
                layoutOptionD.setBackgroundResource(R.drawable.option_border_wrong);
                txtOptionD.setTextColor(getResources().getColor(R.color.colorRed));
                break;
        }
    }

    private void optionDelete(int opt) {
        switch (opt) {
            case 0:
                txtOptionA.setText("");
                break;
            case 1:
                txtOptionB.setText("");
                break;
            case 2:
                txtOptionC.setText("");
                break;
            case 3:
                txtOptionD.setText("");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isClose = true;
        finish();
    }

    public void jokerFiftyFifty(View v) {
        if (!isJokerFiftyFifty) {
            Random random = new Random();
            int[] arr = new int[2];
            while (true) {
                int x1 = random.nextInt(4);
                int x2 = random.nextInt(4);
                if (x1 != x2) {
                    if (x1 != question.getAnswer() && x2 != question.getAnswer()) {
                        arr[0] = x1;
                        arr[1] = x2;
                        isOptionDelete[x1] = true;
                        isOptionDelete[x2] = true;
                        break;
                    }
                }
            }
            optionDelete(arr[0]);
            optionDelete(arr[1]);
            setMoney(PRICEFIFTYFIFTY);
            jokerDisable(0);
            jokerControl();
            isUsingJoker = 0;
        }
    }

    public void jokerInsertTen(View v) {
        /*
         * 600 / (30 / 10) = 2  MAXTIME / (levelTime / 10)
         */
        if (!isJokerInsertTen) {
            pBarStatus += MAXTIME / (levelTime / 10);
            time += 10;
            setMoney(PRICEINSERTTEN);
            jokerDisable(1);
            jokerControl();
            isUsingJoker = 0;
        }
    }

    public void jokerSkip(View v) {
        if (!isJokerSkip) {
            nextQuestion();
            setMoney(PRICESKIP);
            jokerDisable(2);
            jokerControl();
            isUsingJoker = 0;
        }
    }

    public void jokerCorrectAnswer(View v) {
        if (!isJokerCorrectAnswer) {
            correct++;
            correctAnswer(question.getAnswer());
            isCorrect = true;
            if (Integer.parseInt(item[27]) == 1) correctOptionSound.start(); //0 = not sound, 1 = sound
            score += 50 * level;
            correctOrWrong(isCorrect);
            txtScore.setText("Puan: " + score);
            setMoney(PRICECORRECTANSWER);
            jokerDisable(3);
            jokerControl();
            isUsingJoker = 0;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                }
            }, NEXTQUESTION);
        }
    }

    private void jokerControl(){
        if (getMoney() - PRICEFIFTYFIFTY < 0) jokerDisable(0);
        if (getMoney() - PRICEINSERTTEN < 0) jokerDisable(1);
        if (getMoney() - PRICESKIP < 0) jokerDisable(2);
        if (getMoney() - PRICECORRECTANSWER < 0) jokerDisable(3);
    }

    private void jokerDisable(int joker) {
        switch (joker) {
            case 0:
                layoutFiftyFifty.setBackgroundResource(R.drawable.option_border);
                isJokerFiftyFifty = true;
                break;
            case 1:
                layoutInsertTen.setBackgroundResource(R.drawable.option_border);
                isJokerInsertTen = true;
                break;
            case 2:
                layoutSkip.setBackgroundResource(R.drawable.option_border);
                isJokerSkip = true;
                break;
            case 3:
                layoutCorrectAnswer.setBackgroundResource(R.drawable.option_border);
                isJokerCorrectAnswer = true;
                break;
        }

    }
    private void setMoney(int money) {
        int m = getMoney() - money;
        DataBase db = new DataBase(this);
        db.update(MONEY, m);
        txtMoney.setText(String.valueOf(getMoney()));
    }

    private int getMoney() {
        DataBase db = new DataBase(this);
        List<String> list = db.list();
        String[] item = list.get(0).split(" - ");
        return Integer.parseInt(item[MONEY]);
    }
}

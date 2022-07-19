package com.example.wordquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LevelPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_page);
    }

    public void play(View v) {
        Intent goQuestionPage = new Intent(this, QuestionPage.class);
        switch (v.getId()) {
            case R.id.layoutLevelEasy:
                goQuestionPage.putExtra("level", 1);
                break;
            case R.id.layoutLevelMedium:
                goQuestionPage.putExtra("level", 2);
                break;
            case R.id.layoutLevelHard:
                goQuestionPage.putExtra("level", 3);
                break;
        }
        startActivity(goQuestionPage);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

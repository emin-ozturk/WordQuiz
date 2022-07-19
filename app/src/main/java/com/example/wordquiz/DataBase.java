package com.example.wordquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wordQuiz";
    private static final String TABLE_NAME = "word";
    private static final int DATABASE_VERSION = 1;
    private static final String ROW_ID = "id";
    private static final String ROW_SCORE = "score";
    private static final String ROW_MONEY = "money";

    private static final String ROW_MISSIONEASYONE = "missionEasyOne"; //Kolay seviyede 30 yıldız topla
    private static final String ROW_MISSIONEASYTWO = "missionEasyTwo"; //Kolay seviyede 100 soruyu doğru cevapla
    private static final String ROW_MISSIONEASYTHREE = "missionEasyThree"; //Kolay seviyede 600 altın kazan
    private static final String ROW_MISSIONEASYFOUR = "missionEasyFour"; //Kolay seviyede 10 kez jokersiz tüm soruları doğru cevapla

    private static final String ROW_MISSIONMEDIUMONE = "missionMediumOne"; //Orta seviyede 50 yıldız topla
    private static final String ROW_MISSIONMEDIUMTWO = "missionMediumTwo"; //Orta seviyede 200 soruyu doğru cevapla
    private static final String ROW_MISSIONMEDIUMTHREE = "missionMediumThree"; //Orta seviyede 750 altın kazan
    private static final String ROW_MISSIONMEDIUMFOUR = "missionMediumFour"; //Orta seviyede 15 kez jokersiz tüm soruları doğru cevapla

    private static final String ROW_MISSIONHARDONE = "missionHardOne"; //Zor seviyede 75 yıldız topla
    private static final String ROW_MISSIONHARDTWO = "missionHardTwo"; //Zor seviyede 300 soruyu doğru cevapla
    private static final String ROW_MISSIONHARDTHREE = "missionHardThree"; //Zor seviyede 1000 altın kazan
    private static final String ROW_MISSIONHARDFOUR = "missionHardFour"; //Zor seviyede 20 kez jokersiz tüm soruları doğru cevapla

    private static final String ROW_ISMISSIONCOMPLETEEASYONE = "isMissionCompleteEasyOne";
    private static final String ROW_ISMISSIONCOMPLETEEASYTWO = "isMissionCompleteEasyTwo";
    private static final String ROW_ISMISSIONCOMPLETEEASYTHREE = "isMissionCompleteEasyThree";
    private static final String ROW_ISMISSIONCOMPLETEEASYFOUR = "isMissionCompleteEasyFour";

    private static final String ROW_ISMISSIONCOMPLETEMEDIUMONE = "isMissionCompleteMediumOne";
    private static final String ROW_ISMISSIONCOMPLETEMEDIUMTWO = "isMissionCompleteMediumTwo";
    private static final String ROW_ISMISSIONCOMPLETEMEDIUMTHREE = "isMissionCompleteMediumThree";
    private static final String ROW_ISMISSIONCOMPLETEMEDIUMFOUR = "isMissionCompleteMediumFour";

    private static final String ROW_ISMISSIONCOMPLETEHARDONE = "isMissionCompleteHardOne";
    private static final String ROW_ISMISSIONCOMPLETEHARDTWO = "isMissionCompleteHardTwo";
    private static final String ROW_ISMISSIONCOMPLETEHARDTHREE = "isMissionCompleteHardThree";
    private static final String ROW_ISMISSIONCOMPLETEHARDFOUR = "isMissionCompleteHardFour";

    private static final String ROW_SOUND= "sound";

    DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        createTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ROW_SCORE + " INT NOT NULL,"
                + ROW_MONEY + " INT NOT NULL,"

                + ROW_MISSIONEASYONE + " INT NOT NULL,"
                + ROW_MISSIONEASYTWO + " INT NOT NULL,"
                + ROW_MISSIONEASYTHREE + " INT NOT NULL,"
                + ROW_MISSIONEASYFOUR + " INT NOT NULL,"

                + ROW_MISSIONMEDIUMONE + " INT NOT NULL,"
                + ROW_MISSIONMEDIUMTWO + " INT NOT NULL,"
                + ROW_MISSIONMEDIUMTHREE + " INT NOT NULL,"
                + ROW_MISSIONMEDIUMFOUR + " INT NOT NULL,"

                + ROW_MISSIONHARDONE + " INT NOT NULL,"
                + ROW_MISSIONHARDTWO + " INT NOT NULL,"
                + ROW_MISSIONHARDTHREE + " INT NOT NULL,"
                + ROW_MISSIONHARDFOUR + " INT NOT NULL,"

                + ROW_ISMISSIONCOMPLETEEASYONE + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEEASYTWO + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEEASYTHREE + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEEASYFOUR + " INT NOT NULL,"

                + ROW_ISMISSIONCOMPLETEMEDIUMONE + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEMEDIUMTWO + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEMEDIUMTHREE + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEMEDIUMFOUR + " INT NOT NULL,"

                + ROW_ISMISSIONCOMPLETEHARDONE + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEHARDTWO + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEHARDTHREE + " INT NOT NULL,"
                + ROW_ISMISSIONCOMPLETEHARDFOUR+ " INT NOT NULL,"

                + ROW_SOUND + " INT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE İF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    List<String> list() {
        List<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] rows = {ROW_ID, ROW_SCORE, ROW_MONEY,
                    ROW_MISSIONEASYONE, ROW_MISSIONEASYTWO, ROW_MISSIONEASYTHREE, ROW_MISSIONEASYFOUR,
                    ROW_MISSIONMEDIUMONE, ROW_MISSIONMEDIUMTWO, ROW_MISSIONMEDIUMTHREE, ROW_MISSIONMEDIUMFOUR,
                    ROW_MISSIONHARDONE, ROW_MISSIONHARDTWO, ROW_MISSIONHARDTHREE, ROW_MISSIONHARDFOUR,
                    ROW_ISMISSIONCOMPLETEEASYONE, ROW_ISMISSIONCOMPLETEEASYTWO, ROW_ISMISSIONCOMPLETEEASYTHREE,
                    ROW_ISMISSIONCOMPLETEEASYFOUR, ROW_ISMISSIONCOMPLETEMEDIUMONE, ROW_ISMISSIONCOMPLETEMEDIUMTWO,
                    ROW_ISMISSIONCOMPLETEMEDIUMTHREE, ROW_ISMISSIONCOMPLETEMEDIUMFOUR, ROW_ISMISSIONCOMPLETEHARDONE,
                    ROW_ISMISSIONCOMPLETEHARDTWO, ROW_ISMISSIONCOMPLETEHARDTHREE, ROW_ISMISSIONCOMPLETEHARDFOUR,
                    ROW_SOUND
            };
            Cursor cursor = db.query(TABLE_NAME, rows, null, null, null, null, null);
            while (cursor.moveToNext()) {
                data.add(cursor.getInt(0)
                        + " - "
                        + cursor.getInt(1)
                        + " - "
                        + cursor.getInt(2)

                        + " - "
                        + cursor.getInt(3)
                        + " - "
                        + cursor.getInt(4)
                        + " - "
                        + cursor.getInt(5)
                        + " - "
                        + cursor.getInt(6)
                        + " - "
                        + cursor.getInt(7)
                        + " - "
                        + cursor.getInt(8)
                        + " - "
                        + cursor.getInt(9)
                        + " - "
                        + cursor.getInt(10)
                        + " - "
                        + cursor.getInt(11)
                        + " - "
                        + cursor.getInt(12)
                        + " - "
                        + cursor.getInt(13)
                        + " - "
                        + cursor.getInt(14)

                        + " - "
                        + cursor.getInt(15)
                        + " - "
                        + cursor.getInt(16)
                        + " - "
                        + cursor.getInt(17)
                        + " - "
                        + cursor.getInt(18)
                        + " - "
                        + cursor.getInt(19)
                        + " - "
                        + cursor.getInt(20)
                        + " - "
                        + cursor.getInt(21)
                        + " - "
                        + cursor.getInt(22)
                        + " - "
                        + cursor.getInt(23)
                        + " - "
                        + cursor.getInt(24)
                        + " - "
                        + cursor.getInt(25)
                        + " - "
                        + cursor.getInt(26)
                        + " - "
                        + cursor.getInt(27));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return data;
    }

    private void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_SCORE, 0);
            cv.put(ROW_MONEY, 3000);

            cv.put(ROW_MISSIONEASYONE, 0);
            cv.put(ROW_MISSIONEASYTWO, 0);
            cv.put(ROW_MISSIONEASYTHREE, 0);
            cv.put(ROW_MISSIONEASYFOUR, 0);

            cv.put(ROW_MISSIONMEDIUMONE, 0);
            cv.put(ROW_MISSIONMEDIUMTWO, 0);
            cv.put(ROW_MISSIONMEDIUMTHREE, 0);
            cv.put(ROW_MISSIONMEDIUMFOUR, 0);

            cv.put(ROW_MISSIONHARDONE, 0);
            cv.put(ROW_MISSIONHARDTWO, 0);
            cv.put(ROW_MISSIONHARDTHREE, 0);
            cv.put(ROW_MISSIONHARDFOUR, 0);

            cv.put(ROW_ISMISSIONCOMPLETEEASYONE, 0);
            cv.put(ROW_ISMISSIONCOMPLETEEASYTWO, 0);
            cv.put(ROW_ISMISSIONCOMPLETEEASYTHREE, 0);
            cv.put(ROW_ISMISSIONCOMPLETEEASYFOUR, 0);

            cv.put(ROW_ISMISSIONCOMPLETEMEDIUMONE, 0);
            cv.put(ROW_ISMISSIONCOMPLETEMEDIUMTWO, 0);
            cv.put(ROW_ISMISSIONCOMPLETEMEDIUMTHREE, 0);
            cv.put(ROW_ISMISSIONCOMPLETEMEDIUMFOUR, 0);

            cv.put(ROW_ISMISSIONCOMPLETEHARDONE, 0);
            cv.put(ROW_ISMISSIONCOMPLETEHARDTWO, 0);
            cv.put(ROW_ISMISSIONCOMPLETEHARDTHREE, 0);
            cv.put(ROW_ISMISSIONCOMPLETEHARDFOUR, 0);
            cv.put(ROW_SOUND, 1);

            db.insert(TABLE_NAME, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    void update(int row, int data) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            switch (row) {
                case 1:
                    cv.put(ROW_SCORE, data);
                    break;
                case 2:
                    cv.put(ROW_MONEY, data);
                    break;

                case 3:
                    cv.put(ROW_MISSIONEASYONE, data);
                    break;
                case 4:
                    cv.put(ROW_MISSIONEASYTWO, data);
                    break;
                case 5:
                    cv.put(ROW_MISSIONEASYTHREE, data);
                    break;
                case 6:
                    cv.put(ROW_MISSIONEASYFOUR, data);
                    break;

                case 7:
                    cv.put(ROW_MISSIONMEDIUMONE, data);
                    break;
                case 8:
                    cv.put(ROW_MISSIONMEDIUMTWO, data);
                    break;
                case 9:
                    cv.put(ROW_MISSIONMEDIUMTHREE, data);
                    break;
                case 10:
                    cv.put(ROW_MISSIONMEDIUMFOUR, data);
                    break;

                case 11:
                    cv.put(ROW_MISSIONHARDONE, data);
                    break;
                case 12:
                    cv.put(ROW_MISSIONHARDTWO, data);
                    break;
                case 13:
                    cv.put(ROW_MISSIONHARDTHREE, data);
                    break;
                case 14:
                    cv.put(ROW_MISSIONHARDFOUR, data);
                    break;
            }

            String where = ROW_ID + " = '1'";
            db.update(TABLE_NAME, cv, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    void updateMissionComplete(int row) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            switch (row) { // 0 = false, 1 = true
                case 1:
                    cv.put(ROW_ISMISSIONCOMPLETEEASYONE, 1);
                    break;
                case 2:
                    cv.put(ROW_ISMISSIONCOMPLETEEASYTWO, 1);
                    break;
                case 3:
                    cv.put(ROW_ISMISSIONCOMPLETEEASYTHREE, 1);
                    break;
                case 4:
                    cv.put(ROW_ISMISSIONCOMPLETEEASYFOUR, 1);
                    break;

                case 5:
                    cv.put(ROW_ISMISSIONCOMPLETEMEDIUMONE, 1);
                    break;
                case 6:
                    cv.put(ROW_ISMISSIONCOMPLETEMEDIUMTWO, 1);
                    break;
                case 7:
                    cv.put(ROW_ISMISSIONCOMPLETEMEDIUMTHREE, 1);
                    break;
                case 8:
                    cv.put(ROW_ISMISSIONCOMPLETEMEDIUMFOUR, 1);
                    break;

                case 9:
                    cv.put(ROW_ISMISSIONCOMPLETEHARDONE, 1);
                    break;
                case 10:
                    cv.put(ROW_ISMISSIONCOMPLETEHARDTWO, 1);
                    break;
                case 11:
                    cv.put(ROW_ISMISSIONCOMPLETEHARDTHREE, 1);
                    break;
                case 12:
                    cv.put(ROW_ISMISSIONCOMPLETEHARDFOUR, 1);
                    break;
            }

            String where = ROW_ID + " = '1'";
            db.update(TABLE_NAME, cv, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    void updateSound(int sound) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            switch (sound) { //0 = Not sound, 1 = sound
                case 0:
                    cv.put(ROW_SOUND, 0);
                    break;
                case 1:
                    cv.put(ROW_SOUND, 1);
                    break;
            }
            String where = ROW_ID + " = '1'";
            db.update(TABLE_NAME, cv, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

}

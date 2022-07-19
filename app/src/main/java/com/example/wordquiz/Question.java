package com.example.wordquiz;

import java.util.Random;

class Question {
    private int[] opt;
    private String[][] word;
    private String[] option;
    private String optionCorrect;
    private int level;

    Question(int level) {
        this.level = level;
    }

    void newQuestion() {
        EnglishWords englishWords = new EnglishWords(level);
        word = englishWords.getWords();

        Random random = new Random();

        int r1 = random.nextInt(word.length);

        int r2 = random.nextInt(word.length);
        int r3 = random.nextInt(word.length);
        int r4 = random.nextInt(word.length);

        do {
            if (r1 == r2) r2 = random.nextInt(word.length);
            if (r1 == r3) r3 = random.nextInt(word.length);
            if (r1 == r4) r4 = random.nextInt(word.length);
            if (r2 == r3) r3 = random.nextInt(word.length);
            if (r2 == r4) r4 = random.nextInt(word.length);
            if (r3 == r4) r4 = random.nextInt(word.length);

        } while (r1 == r2 || r1 == r3 || r1 == r4 || r2 == r3 || r2 == r4 || r3 == r4);

        opt = new int[]{r1, r2, r3, r4};

        mixArray(opt);

        option = new String[] {
                word[r1][0],
                word[opt[0]][1],
                word[opt[1]][1],
                word[opt[2]][1],
                word[opt[3]][1]
        };

        optionCorrect = word[r1][1]; //correct option
    }

    private void mixArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    String getQuestion() {
        return option[0];
    }

    String[] getOptions() {
        return option;
    }

    int getAnswer() {
        for (int i = 0; i < opt.length; i++) {
            if (word[opt[i]][1].equals(optionCorrect)) {
                return i;
            }
        }
        return -1;
    }
}

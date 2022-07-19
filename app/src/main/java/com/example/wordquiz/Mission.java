package com.example.wordquiz;

public class Mission {
    private boolean success;
    private String mission, statusText;
    private int pBarMax, pBarProgress;

    Mission(boolean success, String mission, String statusText, int pBarMax, int pBarProgress) {
        this.success = success;
        this.mission = mission;
        this.statusText = statusText;
        this.pBarMax = pBarMax;
        this.pBarProgress = pBarProgress;
    }

    boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean cupImage) {
        this.success = success;
    }

    String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    int getPBarMax() {
        return pBarMax;
    }

    public void setPBarMax(int pBarMax) {
        this.pBarMax = pBarMax;
    }

    public int getPBarProgress() {
        return pBarProgress;
    }

    public void setPBarProgress(int pBarProgress) {
        this.pBarProgress = pBarProgress;
    }
}

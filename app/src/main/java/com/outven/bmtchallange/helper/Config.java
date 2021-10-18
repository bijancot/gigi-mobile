package com.outven.bmtchallange.helper;

public class Config {
    public static final String IS_LOGGIN_IN = "isLoggedIn";
    public static final String USER_EMAIL = "email";
    public static final String USER_NAME = "name";
    public static final String USER_TIER = "tier";

    // Use in MyAdapter for Time Tracker
    private String mTimeTracker;
    private String videPath;

    public String getmTimeTracker() {
        return mTimeTracker;
    }

    public void setmTimeTracker(String mTimeTracker) {
        this.mTimeTracker = mTimeTracker;
    }

    public String getVidePath() {
        return videPath;
    }

    public void setVidePath(String videPath) {
        this.videPath = videPath;
    }
}

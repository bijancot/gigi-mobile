package com.outven.bmtchallange.helper;

import android.net.Uri;

import java.io.File;

public class Config {
    public static final String IS_LOGGIN_IN = "isLoggedIn";
    public static final String IS_START = "mulaiApp";
    public static final String TIME_DAY = "day";
    public static final String TIME_NIGHT = "night";

    public static final String USER_NISN = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_GENDER = "gender";
    public static final String USER_LAHIR = "lahir";
    public static final String USER_PHONE = "phone";
    public static final String USER_CLASS = "class";

    public static final String USER_REPORT_ID = "report_id";
    public static final String USER_REPORT_ENTRY = "report_entry";
    public static final String USER_REPORT_TIME = "report_time";
    public static final String USER_DAY = "report_day";
    public static final String USER_REPORT_STATUS = "report_status";

    public static File[] files = new File[2];
    public static String[][] listUpload = new String[2][2];

    //Date Month Fomat
    public static final String[] arrBulan = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December"};

    // Use in MyAdapter for Time Tracker
    private static String messageTracker;
    public static String videPath;
    private static String categoryUpload;
    private static Uri fileBefore;
    private static Uri fileAfter;
    private static boolean point;
    private static boolean finished;

    public static String getVidePath() {
        return videPath;
    }

    public static void setVidePath(String videPath) {
        Config.videPath = videPath;
    }

    public static String getCategoryUpload() {
        return categoryUpload;
    }

    public static void setCategoryUpload(String categoryUpload) {
        Config.categoryUpload = categoryUpload;
    }

    public static Uri getFileBefore() {
        return fileBefore;
    }

    public static void setFileBefore(Uri fileBefore) {
        Config.fileBefore = fileBefore;
    }

    public static Uri getFileAfter() {
        return fileAfter;
    }

    public static void setFileAfter(Uri fileAfter) {
        Config.fileAfter = fileAfter;
    }

    public static String getMessageTracker() {
        return messageTracker;
    }

    public static void setMessageTracker(String messageTracker) {
        Config.messageTracker = messageTracker;
    }

    public static boolean isPoint() {
        return point;
    }

    public static void setPoint(boolean point) {
        Config.point = point;
    }

    public static boolean isFinished() {
        return finished;
    }

    public static void setFinished(boolean finished) {
        Config.finished = finished;
    }
}

package com.outven.bmtchallange.helper;

import android.net.Uri;

import java.io.File;
import java.util.Date;

public class Config {
    public static final String IS_LOGGIN_IN = "isLoggedIn";
    public static final String IS_START = "mulaiApp";
    public static final String TIME_DAY = "day";
    public static final String TIME_NIGHT = "night";

    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";

    public static final String USER_REPORT_ID = "report_id";
    public static final String USER_REPORT_ENTRY = "report_entry";
    public static final String USER_REPORT_TIME = "report_time";
    public static final String USER_DAY = "report_day";
    public static final String USER_REPORT_STATUS = "report_status";


    public static Date appCurTime;
    public static File[] files = new File[2];
    public static String[][] listUpload = new String[2][2];

    //Date Month Fomat
    public static final String[] arrBulan = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December"};

    // Use in MyAdapter for Time Tracker
    private static String messageTracker;
    public static String videPath;
    private static String reportStatus;
    private static String mediaPath;
    private static String categoryUpload;
    private static String statusUpload;
    private static Uri fileBefore;
    private static Uri fileAfter;
    private static int userTrackerDay;

    public static String getVidePath() {
        return videPath;
    }

    public static void setVidePath(String videPath) {
        Config.videPath = videPath;
    }

    public static String getReportStatus() {
        return reportStatus;
    }

    public static void setReportStatus(String reportStatus) {
        Config.reportStatus = reportStatus;
    }

    public static int getUserTrackerDay() {
        return userTrackerDay;
    }

    public static void setUserTrackerDay(int userTrackerDay) {
        Config.userTrackerDay = userTrackerDay;
    }

    public static String getMediaPath() {
        return mediaPath;
    }

    public static void setMediaPath(String mediaPath) {
        Config.mediaPath = mediaPath;
    }

    public static String getCategoryUpload() {
        return categoryUpload;
    }

    public static void setCategoryUpload(String categoryUpload) {
        Config.categoryUpload = categoryUpload;
    }

    public static String getStatusUpload() {
        return statusUpload;
    }

    public static void setStatusUpload(String statusUpload) {
        Config.statusUpload = statusUpload;
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
}

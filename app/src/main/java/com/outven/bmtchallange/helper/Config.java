package com.outven.bmtchallange.helper;

import android.net.Uri;

import java.util.Date;

public class Config {
    public static final String IS_LOGGIN_IN = "isLoggedIn";
    public static final String HAVE_REPORT_TODAY = "haveReportToday";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_TIER = "tier";
    public static final String USER_REPORT_STATUS = "report_status";
    public static final String USER_REPORT_ID = "report_id";
    public static final String USER_OPEN_APP_DATE = "userOpenAppDate";

    public static Date appCurTime;

    //Date Month Fomat
    public static final String[] arrBulan = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December"};

    // Use in MyAdapter for Time Tracker
    private static String mTimeTracker;
    public static String videPath;
    private static String reportStatus;
    private static int userTrackerDay;
    private static Uri fileBefore;
    private static Uri fileAfter;

    public static String getVidePath() {
        return videPath;
    }

    public static void setVidePath(String videPath) {
        Config.videPath = videPath;
    }

    public static String getmTimeTracker() {
        return mTimeTracker;
    }

    public static void setmTimeTracker(String mTimeTracker) {
        Config.mTimeTracker = mTimeTracker;
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
}

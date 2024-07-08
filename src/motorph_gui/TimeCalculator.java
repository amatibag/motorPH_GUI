/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_gui;

/**
 *
 * @author amatibag
 */
public class TimeCalculator {
    private static final int MINUTES_IN_HOUR = 60;
    private static final int WORK_START_HOUR = 8;
    private static final int WORK_START_MINUTE = 0;
    private static final int GRACE_PERIOD_MINUTES = 10;
    private static final int REGULAR_WORK_HOURS = 8;

    public static double calculateTotalHours(String loginTimeText, String logoutTimeText) {
        int loginHour = parseHour(loginTimeText);
        int loginMinute = parseMinute(loginTimeText);
        int logoutHour = parseHour(logoutTimeText);
        int logoutMinute = parseMinute(logoutTimeText);

        // Apply grace period adjustment
        if (loginHour == WORK_START_HOUR && loginMinute <= GRACE_PERIOD_MINUTES) {
            loginHour = WORK_START_HOUR;
            loginMinute = WORK_START_MINUTE;
        }

        // Calculate total minutes worked
        int totalMinutes = (logoutHour - loginHour) * MINUTES_IN_HOUR + (logoutMinute - loginMinute);

        // Calculate total hours worked
        double totalHours = totalMinutes / (double) MINUTES_IN_HOUR;
        return totalHours;
    }

    public static double calculateOvertimeHours(String loginTimeText, String logoutTimeText) {
        double totalHours = calculateTotalHours(loginTimeText, logoutTimeText);
        double overtimeHours = Math.max(totalHours - REGULAR_WORK_HOURS, 0);
        return overtimeHours;
    }

    private static int parseHour(String timeText) {
        return Integer.parseInt(timeText.split(":")[0]);
    }

    private static int parseMinute(String timeText) {
        return Integer.parseInt(timeText.split(":")[1]);
    }
}


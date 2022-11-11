package com.Jongyeol.hshsmenuWeb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Day {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private String title, previous, next, today;
    public Day() {
        setting(new Date());
    }
    public Day(int day) throws ParseException {
        setting(dateFormat.parse(day + ""));
    }

    private void setting(Date date) {
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("E");
        String todateweek = switch (dateFormat3.format(date)) {
            case "Mon" -> " (월)";
            case "Tue" -> " (화)";
            case "Wed" -> " (수)";
            case "Thu" -> " (목)";
            case "Fri" -> " (금)";
            case "Sat" -> " (토)";
            case "Sun" -> " (일)";
            default -> "";
        };
        if (todateweek.equals("")) {
            todateweek = " (" + dateFormat3.format(date) + ")";
        }
        title = dateFormat2.format(date) + todateweek + " 급식";
        Date date1 = new Date(date.getTime());
        date1.setDate(date1.getDate() - 1);
        previous = dateFormat.format(date1);
        Date date2 = new Date(date.getTime());
        date2.setDate(date2.getDate() + 1);
        next = dateFormat.format(date2);
        today = dateFormat.format(date);
    }

    public String getTitle() {
        return title;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }

    public String getToday() {
        return today;
    }
}

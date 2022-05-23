package edu.skku.cs.todocalendar.Classes;

public class Plan {
    String title;
    int year;
    int month;
    int day;
    int hour;
    int minute;
    Boolean need_alarm;
    String color;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Boolean getNeed_alarm() {
        return need_alarm;
    }

    public void setNeed_alarm(Boolean need_alarm) {
        this.need_alarm = need_alarm;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

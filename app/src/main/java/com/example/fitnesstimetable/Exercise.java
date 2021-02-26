package com.example.fitnesstimetable;

public class Exercise {
    private String name;
    private String startTime;
    private String endTime;
    private String teacher;
    private String place;
    private String description;
    private int weekDay;
    private int availability;

    public Exercise(String name, String startTime, String endTime, String teacher, String place, String description, int weekDay, int availability) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacher = teacher;
        this.place = place;
        this.description = description;
        this.weekDay = weekDay;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}

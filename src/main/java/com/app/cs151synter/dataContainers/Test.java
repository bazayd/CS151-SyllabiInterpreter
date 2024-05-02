package com.app.cs151synter.dataContainers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class Test implements DatedSyllabusEntity, Comparable<Test> {

    private TestType testType;
    private String title;
    private Calendar date;
//    private LocalDateTime datetime;

    public Test(TestType testType, String title) {
        this.testType = testType;
        this.title = title;
    }
//    public Test (TestType testType, String title, LocalDateTime datetime) {
//        this.testType = testType;
//        this.title = title;
////        this.datetime = datetime;
//    }
    public Test (TestType testType, String title, Calendar date) {
        this.testType = testType;
        this.title = title;
        this.date = date;
    }

    public String[] getInfo() {

        String[] testInfo = new String[3];

        testInfo[0] = getTitle();
        testInfo[1] = getDueDate().toString();
        testInfo[2] = testType.toString();

        return testInfo;
    }


    public String getTitle() {
        if (testType == TestType.FINAL) {
            return "Final Exam: " + title;
        }else if (testType == TestType.MIDTERM) {
            return "Midterm Exam: " + title;
        }else if (testType == TestType.QUIZ) {
            return "Quiz: " + title;
        }else {
            return "Test: " + title;
        }
    }

    public Calendar getDueDate() {
        return date;
    }

    public void setDueDate(Calendar date) {
        this.date = date;
    }

    @Override
    public String getDescription() {
        ZonedDateTime a = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return testType.name() + " DATE: " + a.getMonthValue()  + "/" + a.getDayOfMonth() + "\n";
    }

//    public void setTestType(TestType testType) {
//        this.testType = testType;
//    }

    public TestType getType () {
        return testType;
    }

    public int compareTo(Test o) {
        return -1;
    }
    @Override
    public String toString () {
        ZonedDateTime a = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return "DATE: " + a.getMonthValue()  + "/" + a.getDayOfMonth() +
                "\nTITLE: " + title;
    }
}

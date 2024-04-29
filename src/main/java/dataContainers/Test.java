package dataContainers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Test implements DatedSyllabusEntity, Comparable<Test> {

    private TestType testType;
    private String title;
    private String date;
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
    public Test (TestType testType, String title, String date) {
        this.testType = testType;
        this.title = title;
        this.date = date;
    }

    public String[] getInfo() {

        String[] testInfo = new String[3];

        testInfo[0] = getTitle();
        testInfo[1] = getDueDate();
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

    public ZonedDateTime getDueDate() {
        return date;
    }

    public void setDueDate(String date) {
        this.date = date;
    }

    @Override
    public String getDescription() {
        return "";
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
        return "DATE: " + date +
                "\nTITLE: " + title +
                "\nTYPE: " + testType;
    }
}

package dataContainers;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Test implements DatedSyllabusEntities, Comparable<Test> {

    private TestType testType;
    private String title;
    private String date; // i'll get rid of this later
    private LocalDateTime datetime;

    public Test(TestType testType, String title) {
        this.testType = testType;
        this.title = title;
    }
    public Test (TestType testType, String title, LocalDateTime datetime) {
        this.testType = testType;
        this.title = title;
        this.datetime = datetime;
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

    public String getDueDate() {
        return null;
    }

    public void setDueDate(String date) {
        this.date = date;
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
                "\nTITLE: " + title;
    }
}

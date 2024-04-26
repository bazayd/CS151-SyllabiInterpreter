package dataContainers;

import java.util.Arrays;

public class Test implements DatedSyllabusEntities, Comparable<Test> {

    private TestType testType;
    private String title;

    public Test(TestType testType, String title) {
        this.testType = testType;
        this.title = title;
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

//    public void setTestType(TestType testType) {
//        this.testType = testType;
//    }

    public TestType getType () {
        return testType;
    }

    public int compareTo(Test o) {
        return -1;
    }
}

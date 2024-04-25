package dataContainers;

import java.util.Arrays;

public class Test implements SyllabusEntities, Comparable<Test> {

    private TestType testType;

    public Test(TestType testType) {
        this.testType = testType;
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
            return "Final Exam";
        }else if (testType == TestType.MIDTERM) {
            return "Midterm Exam";
        }else if (testType == TestType.QUIZ) {
            return "Quiz";
        }else {
            return "Test";
        }
    }

    public String getDueDate() {
        return null;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public TestType getType () {
        return testType;
    }

    public int compareTo(Test o) {
        return -1;
    }
}

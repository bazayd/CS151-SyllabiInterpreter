package dataContainers;

public class Test implements SyllabusEntities, Comparable<Test> {
    public static void main(String[] args) {

    }

    public String[] getInfo() {
        return new String[0];
    }

    public String getTitle() {
        return null;
    }

    public String getDueDate() {
        return null;
    }

    public TestType getType () {
        return TestType.DEFAULT_TEST;
    }

    public int compareTo(Test o) {
        return -1;
    }
}

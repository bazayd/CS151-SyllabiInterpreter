import dataContainers.Assignment;
import dataContainers.Syllabus;
import dataContainers.Test;
import dataContainers.TestType;
import org.bouncycastle.tsp.TSPUtil;

public class Main {
    public static void main(String[] args) {
        Syllabus syllabus = new Syllabus("First.Last");
        Test test1 = new Test(TestType.MIDTERM, "Blank");
        Test test2 = new Test(TestType.FINAL, "Blank");
        Test test3 = new Test(TestType.MIDTERM, "Blank");



        syllabus.addTest(test1);
        syllabus.addTest(test2);
        syllabus.addTest(test3);

        syllabus.printAllTests();

    }
}

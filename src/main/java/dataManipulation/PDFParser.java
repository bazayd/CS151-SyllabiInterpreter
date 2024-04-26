package dataManipulation;
import dataContainers.Syllabus;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFParser {
    public static void main(String[] args) {

    }

    public static Syllabus generateSyllabus (PDDocument PDF) {
        // TODO decide return format (tabula creates tables)
        return new Syllabus("First.Last");
    }
}

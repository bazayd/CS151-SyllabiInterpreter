package dataManipulation;
import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import customExceptions.BadPDFFormatException;
import dataContainers.*;
import org.apache.lucene.search.Sort;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFParser {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//            String input = "This is line 1.\n\nThis is line 2.\n\n\nThis is line 3.\n\n";
//
//            String[] parts = input.split("(?m)^\\s*$");
//            for (String part : parts) {
//                System.out.println("Part: [" + part + "]");
//            }
//
//            String[] test = {"March 10, Sunday   Daylight saving time starts (2 AM -> 3 AM)\n\n",
//                    "Apr. 19, Friday   Last day to late drop/withdraw/enrollment",
//                    "2/22/2222 Blah blah"
//            };
//
//            String sentence = test[2];
//        String dateTimePattern = "\\b((?:\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2}|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\. \\d{1,2}, [a-zA-Z]+|January \\d{1,2}, [a-zA-Z]+|February \\d{1,2}, [a-zA-Z]+|March \\d{1,2}, [a-zA-Z]+|April \\d{1,2}, [a-zA-Z]+|May \\d{1,2}, [a-zA-Z]+|June \\d{1,2}, [a-zA-Z]+|July \\d{1,2}, [a-zA-Z]+|August \\d{1,2}, [a-zA-Z]+|September \\d{1,2}, [a-zA-Z]+|October \\d{1,2}, [a-zA-Z]+|November \\d{1,2}, [a-zA-Z]+|December \\d{1,2}, [a-zA-Z]+))\\b";
//
//        String timePattern = "\\b(\\d{1,2}:\\d{2}\\s(?:AM|PM))\\b";
//        String weekPattern = "\\bWeek (\\d{1,2})\\b";
//        String dashPattern = " - ";
//
//        sentence = PDFParser.removeUnwantedStuff(sentence, timePattern);
//        sentence = PDFParser.removeUnwantedStuff(sentence, weekPattern);
//        sentence = PDFParser.removeUnwantedStuff(sentence, dashPattern);
//        System.out.println (sentence);
//        Pattern pattern = Pattern.compile(dateTimePattern);
//        Matcher matcher = pattern.matcher(sentence);
//        if (matcher.find()) {
//            String date = matcher.group(1);
//            String restOfSentence = sentence.replaceFirst(dateTimePattern, "");
//
//            System.out.println (date + " |||| " + restOfSentence);
//        }

        //        String[] testsyllabi = {"testsyllabus.pdf", "testsyllabus2.pdf",
//            "testsyllabus3.pdf"};
        generateSyllabus("testsyllabus.pdf");
        generateSyllabus("testsyllabus2.pdf");
        generateSyllabus("testsyllabus3.pdf");

//        String input = "Week 14 04/24/2024 12:00 PM - 1:15 AM Directed graph, Topological Sort ";
//        separateDateTime(input);
//        String input2 = "Week 5 04/25/2024 \n12:00 PM CS programming class ";
//        separateDateTime(input2);
//        String input3 = "dance class \n05/25/2024 1:00 PM  ";
//        separateDateTime(input3);

//        for (int i=0; i<testsyllabi.length; i++) {
//            ClassLoader classLoader = PDFParser.class.getClassLoader();
//            URL resourceUrl = classLoader.getResource("testresources/" + testsyllabi[i]);
//            PDDocument doc = null;
//            try {
//                doc = PDDocument.load(new File(resourceUrl.toURI()));
//            } catch (IOException | URISyntaxException e) {
//                e.printStackTrace();
//            }
//            generateSyllabus(doc);
//
//            System.out.println ("\n\n ============= \n\n");
//        }
//
//        System.out.println(getRawText("testsyllabus.pdf"));
    }

    public static Syllabus generateSyllabus (String filename) throws IOException, ExecutionException, InterruptedException {
        String text = getRawText(filename);

        System.out.println (" ==== PARSED STUFF == =");
        String profName = parseProfName(text);
        if (profName == null)
            throw new BadPDFFormatException("couldn't find prof name");

        Syllabus syllabus = new Syllabus(profName);
        syllabus.setOfficeHours (parseOfficeHours(text));

        // 3: Contact Information, Instructor, Lecture, Course Description,
            // Classroom Protocols, Course Materials, Course Schedule

        // 2: Course Information, Class time: Instructor, Course Schedule
        // 1: Contact Information, class days/time: course schedule

        String mini = "";
        Scanner scanner = new Scanner(text);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.indexOf("Course Schedule") != -1) {
                System.out.println ("**** FOUND COURSE SCHED");
                scanner.nextLine();
                mini = "";
                while (scanner.hasNextLine()) {
//                    System.out.println (scanner.nextLine());
                    mini += scanner.nextLine() + "\n";
                }
                syllabus.addDatedSyllabusEntities(parseCourseSchedule(mini));
                syllabus.printAssignments();
                syllabus.printAllTests();
            }
        }
        scanner.close();
        return new Syllabus("First Last");
    }
    public static List<DatedSyllabusEntity> parseCourseSchedule (String input) {
        String[] parts = input.split("(?m)^\\s*$");

        System.out.println ("---  parse course schedule");
//        System.out.println (Arrays.toString(parts));
//        System.out.println (parts.length);

        List<DatedSyllabusEntity> ans = new LinkedList<>();
        for (String part: parts) {
            if (part.isEmpty())
                continue;
            ans.add(parseSyllabusEntity(part));
        }
        return ans;
    }
    public static DatedSyllabusEntity parseSyllabusEntity (String input) {
        String[] entitydetails = separateDateTime(input);
        if (entitydetails == null)
            return null;

        String title = entitydetails[1].replaceAll("\n", "");
        String date = entitydetails[0].trim();

        Pattern finalPattern = Pattern.compile(".*\\bfinal\\b.*", Pattern.CASE_INSENSITIVE);
        Matcher finalMatcher = finalPattern.matcher(title);
        if (finalMatcher.find()) {
            return new Test(TestType.FINAL, title, date);
        }
        Pattern midtermPattern = Pattern.compile(".*\\bmidterm\\b.*", Pattern.CASE_INSENSITIVE);
        Matcher midtermMatcher = midtermPattern.matcher(title);
        if (midtermMatcher.find()) {
            return new Test(TestType.MIDTERM, title, date);
        }
        return new Assignment(title, date);
    }

    public static String removeUnwantedStuff(String sentence, String pattern) {
        Pattern tpattern = Pattern.compile(pattern);
        Matcher tmatcher = tpattern.matcher(sentence);
        if (tmatcher.find()) {
            String remSentence = sentence.replaceAll(pattern,"");
            return remSentence;
        } else return sentence;
    }

    public static String[] separateDateTime(String sentence) {

//        System.out.println("Input:" + sentence);
//        String dateTimePattern = "\\b(\\d{2}/\\d{2}/\\d{4})\\b";
//        String dateTimePattern = "\\b(\\d{1,2}/\\d{1,2}/\\d{4})\\b";
//        String dateTimePattern = "\\b(\\d{1,2}/\\d{1,2}(?:/\\d{4})?)\\b";
//        String dateTimePattern = "\\b(?:\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2}|[a-zA-Z]+ \\d{1,2}, \\d{4}|[a-zA-Z]{3,9} \\d{1,2}, \\d{4})\\b";
//        String dateTimePattern = "\\b((?:\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2}|[a-zA-Z]+ \\d{1,2}, \\d{4}|[a-zA-Z]{3,9} \\d{1,2}, \\d{4}))\\b";
//        String dateTimePattern = "\\b(?:\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2}|Mon\\. \\d{1,2}, [a-zA-Z]+|January|February|March|April|May|June|July|August|September|October|November|December \\d{1,2}, [a-zA-Z]+)\\b";
//        String dateTimePattern = "\\b((?:\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2}|Mon\\. \\d{1,2}, [a-zA-Z]+|January|February|March|April|May|June|July|August|September|October|November|December \\d{1,2}, [a-zA-Z]+))\\b";
//        String dateTimePattern = "\\b((?:\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2}|Mon\\. \\d{1,2}, [a-zA-Z]+|January \\d{1,2}, [a-zA-Z]+|February \\d{1,2}, [a-zA-Z]+|March \\d{1,2}, [a-zA-Z]+|April \\d{1,2}, [a-zA-Z]+|May \\d{1,2}, [a-zA-Z]+|June \\d{1,2}, [a-zA-Z]+|July \\d{1,2}, [a-zA-Z]+|August \\d{1,2}, [a-zA-Z]+|September \\d{1,2}, [a-zA-Z]+|October \\d{1,2}, [a-zA-Z]+|November \\d{1,2}, [a-zA-Z]+|December \\d{1,2}, [a-zA-Z]+))\\b";

        String dateTimePattern = "\\b((?:\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2}|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\. \\d{1,2}, [a-zA-Z]+|January \\d{1,2}, [a-zA-Z]+|February \\d{1,2}, [a-zA-Z]+|March \\d{1,2}, [a-zA-Z]+|April \\d{1,2}, [a-zA-Z]+|May \\d{1,2}, [a-zA-Z]+|June \\d{1,2}, [a-zA-Z]+|July \\d{1,2}, [a-zA-Z]+|August \\d{1,2}, [a-zA-Z]+|September \\d{1,2}, [a-zA-Z]+|October \\d{1,2}, [a-zA-Z]+|November \\d{1,2}, [a-zA-Z]+|December \\d{1,2}, [a-zA-Z]+))\\b";
        String timePattern = "\\b(\\d{1,2}:\\d{2}\\s(?:AM|PM))\\b";
        String weekPattern = "\\bWeek (\\d{1,2})\\b";
        String dashPattern = " - ";

        sentence = removeUnwantedStuff(sentence, timePattern);
        sentence = removeUnwantedStuff(sentence, weekPattern);
        sentence = removeUnwantedStuff(sentence, dashPattern);

        Pattern pattern = Pattern.compile(dateTimePattern);
        Matcher matcher = pattern.matcher(sentence);
        if (matcher.find()) {
            String date = matcher.group(1);
            String restOfSentence = sentence.replaceFirst(dateTimePattern, "");

//            System.out.println("Date: " + date);
//            System.out.println("Rest of the sentence: " + restOfSentence.trim());
            return new String[] {date, restOfSentence.trim()};
        } else {
//            System.out.println("No date and time found in the sentence.");
            return null;
        }
    }

    private static String getRawText (String filename) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret("uAFXu2J03Pwv6Ys4");
        ConvertApi.convert("pdf", "txt",
                new Param("File", Paths.get("/tmp/" + filename))
        ).get().saveFilesSync(Paths.get("/tmp/"));
        FileInputStream fs = new FileInputStream("/tmp/" + filename.substring(0,filename.lastIndexOf(".")) + ".txt");
        String s = new String((fs.readAllBytes()));
        fs.close();
        return s;
    }

    private static OfficeHours parseOfficeHours(String info) {
        Pattern officeHoursPattern = Pattern.compile("(?i)office\\s*hour(s?)\\s*(.+)", Pattern.CASE_INSENSITIVE);
        Matcher officeHoursMatcher = officeHoursPattern.matcher(info);
        if (officeHoursMatcher.find()) {
            System.out.println("Office Hours: " + officeHoursMatcher.group(2));
            String rawTextDateAndTime = officeHoursMatcher.group(2);
            return new OfficeHours(rawTextDateAndTime); // TODO format this properly
            // TODO include location and stuff
        }
        return null;
    }

    private static String parseProfName (String info) {
        Pattern instructorPattern = Pattern.compile("(?i)instructor\\s*:?\\s*(?:professor\\s*)?(.+)");
        Matcher instructorMatcher = instructorPattern.matcher(info);
        if (instructorMatcher.find()) {
            System.out.println ("Professor: " + instructorMatcher.group(1));
            String rawProfName = instructorMatcher.group(1);
            return rawProfName;
        }
        return null;
    }

    // TRASHED:
    //    private static LocalDate parseDate(String dateString) {
    //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. d, EEEE");
    //        return LocalDate.parse(dateString, formatter);
    //    }
    //    private static LocalDateTime parseDateTime(String dateString, String timeString) {
    //        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    //        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    //        LocalDate date = LocalDate.parse(dateString, dateFormatter);
    //        LocalDateTime time = LocalDateTime.parse(timeString, timeFormatter);
    //        return date.atTime(time.toLocalTime());
    //    }
    //    private static List<DatedSyllabusEntities> parseEvents(String input) {
    //        List<DatedSyllabusEntities> events = new ArrayList<>();
    //        Pattern pattern = Pattern.compile("(\\w+\\.\\s\\d+,\\s\\w+)\\s*(.+?)\n|\n(Week\\s\\d+)\\s(\\d+/\\d+/\\d+)\n(\\d+:\\d+\\s[AP]M\\s-\\s\\d+:\\d+\\s[AP]M)\n(.+)", Pattern.DOTALL);
    //        Matcher matcher = pattern.matcher(input);
    //
    //        while (matcher.find()) {
    //            if (matcher.group(1) != null) {
    //                System.out.println (matcher.group(2) + " " + matcher.group(1));
    //                events.add(new Assignment(matcher.group(2), parseDate(matcher.group(1))));
    //            } else {
    //                System.out.println (matcher.group(6) + " " + matcher.group(3));
    ////                events.add(new Test(TestType.DEFAULT_TEST, matcher.group(6), matcher.group(3), parseDateTime(matcher.group(4), matcher.group(5))));
    //            }
    //        }
    //
    //        return events;
    //    }
}

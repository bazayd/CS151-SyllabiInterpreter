package com.app.cs151synter.dataManipulation;

import com.app.cs151synter.customExceptions.BadPDFFormatException;
import com.app.cs151synter.dataContainers.*;
import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFParser {
    private static final Logger logger = Logger.getLogger(PDFParser.class.getName());

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//        Syllabus s1 = generateSyllabus("testsyllabus.pdf");
//        Syllabus s2 = generateSyllabus("testsyllabus2.pdf");
//        Syllabus s3 = generateSyllabus("testsyllabus3.pdf");
//
//        System.out.println (s3);
//        String input = "Jan. 2, ";
//
//        String mmdd = "(\\d{1,2})[\\/](\\d{1,2})";
//        String monddday = "(Jan.|January|Feb.|February|Mar.|March|Apr.|April|May|June|July|Aug.|August|Sep.|September|Oct.|October|Nov.|November|Dec.|December) (\\d{1,2}), [a-zA-Z]*"; // not going to write out the days
//        Pattern pattern = Pattern.compile(monddday);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            System.out.println (matcher.group(1));
//        } else
//            System.out.println("fail");
//        parseCalendarDate("2/22");
//        parseCalendarDate("Jan. 34, 324");

        Calendar c = parseCalendarDate("Jan. 23, 231");
        System.out.println (c);
    }

    public static Syllabus generateSyllabus (String filename) throws IOException, ExecutionException, InterruptedException {
        String text = getRawText(filename);

        logger.fine (" ==== PARSED STUFF == =");
        String profName = parseProfName(text);
        if (profName == null)
            throw new BadPDFFormatException("couldn't find prof name");

        Syllabus syllabus = new Syllabus(profName);
        syllabus.setOfficeHours (parseOfficeHours(text));

        // 3: Contact Information, Instructor, Lecture, Course Description,
            // Classroom Protocols, Course Materials, Course Schedule

        // 2: Course Information, Class time: Instructor, Course Schedule
        // 1: Contact Information, class days/time: course schedule

        String[] sections = text.split("(?i)(?=Contact Information|Course Description|Classroom Protocols|Course Materials|Course Schedule)");
        for (String section : sections) {
            String[] lines = section.trim().split("\n", 2);
            if (lines.length > 0) {
                switch (lines[0].trim()) {
                    case "Contact Information":
                        syllabus.setContactInformation(parseContactInformation(lines.length > 1 ? lines[1] : ""));
                        break;
                    case "Course Description":
                        syllabus.setCourseDescription(parseCourseDescription(lines.length > 1 ? lines[1] : ""));
                        break;
                    case "Classroom Protocols":
                        syllabus.setClassroomProtocols(parseClassroomProtocols(lines.length > 1 ? lines[1] : ""));
                        break;
                    case "Course Materials":
                        syllabus.setTextbook(parseCourseMaterials(lines.length > 1 ? lines[1] : ""));
                        break;
                    case "Course Schedule":
                        syllabus.addDatedSyllabusEntities(parseCourseSchedule(lines.length > 1 ? lines[1] : ""));
                        break;
                    default:
                        parseUnrecognizedSection(lines[0], lines.length > 1 ? lines[1] : "", syllabus);
                        break;
                }
            }
        }

        return syllabus;
    }

    public static List<DatedSyllabusEntity> parseCourseSchedule (String input) {
        String[] parts = input.split("(?m)^\\s*$");

        logger.fine ("---  parse course schedule");
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
        String dateString = entitydetails[0].trim();
        Calendar date = parseCalendarDate(dateString);

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

    public static Calendar parseCalendarDate (String dateString) {
        String mmdd = "(\\d{1,2})[\\/](\\d{1,2})";
        String monddday = "(Jan.|January|Feb.|February|Mar.|March|Apr.|April|May|June|July|Aug.|August|Sep.|September|Oct.|October|Nov.|November|Dec.|December) (\\d{1,2}), [a-zA-Z]*"; // not going to write out the days

        Pattern mmdddPattern = Pattern.compile(mmdd);
        Matcher mmmddMatcher = mmdddPattern.matcher(dateString);

        Pattern mondddayPattern = Pattern.compile(monddday);
        Matcher mondddayMatcher = mondddayPattern.matcher(dateString);
        if (mmmddMatcher.find()) {
//            System.out.println (mmmddMatcher.group(1));
//            System.out.println (mmmddMatcher.group(2));
            int month = Integer.parseInt(mmmddMatcher.group(1)) -1;
            int day = Integer.parseInt(mmmddMatcher.group(2));
            return new GregorianCalendar(2024, month, day); // TODO hardcoded year
        } else if (mondddayMatcher.find()) {
//            System.out.println (mondddayMatcher.group(1));
//            System.out.println (mondddayMatcher.group(2));
            String monthString = mondddayMatcher.group(1);
            int day = Integer.parseInt (mondddayMatcher.group(2)) -1;
            int month = CalendarHelper.getMonthFromString(monthString);
            return new GregorianCalendar(2024, month, day);
        }
//        parseCalendarDate("2/22");
        return null;
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
        Config.setDefaultSecret("0LZqZF8QIRoQdmMl");
        ConvertApi.convert("pdf", "txt",
                new Param("File", Paths.get("" + filename))
        ).get().saveFilesSync(Paths.get(""));
        FileInputStream fs = new FileInputStream("" + filename.substring(0,filename.lastIndexOf(".")) + ".txt");
        String s = new String((fs.readAllBytes()));
        fs.close();
        return s;
    }

    private static OfficeHours parseOfficeHours(String info) {
        Pattern officeHoursPattern = Pattern.compile("(?i)office\\s*hour(s?)\\s*(.+)", Pattern.CASE_INSENSITIVE);
        Matcher officeHoursMatcher = officeHoursPattern.matcher(info);
        if (officeHoursMatcher.find()) {
            logger.fine("Office Hours: " + officeHoursMatcher.group(2));
            String rawTextDateAndTime = officeHoursMatcher.group(2);
            return new OfficeHours(rawTextDateAndTime, new GregorianCalendar()); // TODO format this properly
            // TODO include location and stuff
        }
        return null;
    }

    private static String parseProfName (String info) {
        Pattern instructorPattern = Pattern.compile("(?i)instructor\\s*:?\\s*(?:professor\\s*)?(.+)");
        Matcher instructorMatcher = instructorPattern.matcher(info);
        if (instructorMatcher.find()) {
            logger.fine ("Professor: " + instructorMatcher.group(1));
            String rawProfName = instructorMatcher.group(1);
            return rawProfName;
        }
        return null;
    }

    private static ClassroomProtocols parseClassroomProtocols(String s) {
        return new ClassroomProtocols(s); // TODO
    }

    private static Textbook parseCourseMaterials(String s) {
        return new Textbook(s); // TODO
    }

    private static CourseDescription parseCourseDescription(String s) {
        return new CourseDescription(s); // TODO
    }

    private static ContactInformation parseContactInformation(String s) {
        return new ContactInformation(s); // TODO
    }

    private static void parseUnrecognizedSection(String line, String s, Syllabus syllabus) {
        // TODO
    }
}

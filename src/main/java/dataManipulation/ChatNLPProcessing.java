package dataManipulation;

import dataContainers.DatedSyllabusEntity;
import dataContainers.Syllabus;

import dataContainers.SyllabusEntity;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import ui.StudentRawInfo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ChatNLPProcessing {
    // key is professor last name
    private Map<String, Syllabus> syllabi = new HashMap<>();
    private StudentRawInfo studentRawInfo;
    private Syllabus testSyllabus;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        ChatNLPProcessing chatBot = new ChatNLPProcessing();

        PDFParser pdfParser = new PDFParser();

        Syllabus syllabus = null;
        try {
            syllabus = PDFParser.generateSyllabus("testsyllabus.pdf");
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("Speak to chat bot (x to quit): ");

        while (!(userInput = scanner.nextLine()).equals("x")) {


            IntentResponse response = chatBot.getIntentResponse(userInput, syllabus);

            if (response != null) {
                System.out.println(response.generateResponse());
            } else {
                System.out.println("Sorry, I didn't understand your question.");
            }
            System.out.println("Speak to chat bot (x to quit): ");
        }

    }

    private List<String> performNLPProcessing(String input) {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize,ssplit,pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);

        Annotation doc = new Annotation(input);
        pipeline.annotate(doc);

        Set<String> stopwords = new HashSet<>(Arrays.asList("\"a\", \"an\", \"the\", \"i\", \"you\", \"can\", \"have\", \"etc...\""));

        List<String> keywords = doc.get(CoreAnnotations.TokensAnnotation.class)
                .stream()
                .filter(token -> !stopwords.contains(token.get(CoreAnnotations.TextAnnotation.class).toLowerCase()))
                .map(token -> token.get(CoreAnnotations.TextAnnotation.class).toLowerCase())
                .collect(Collectors.toList());

        return keywords;
    }

    public IntentResponse getIntentResponse (String input, Syllabus syllabus) {

        List<String> keywords = performNLPProcessing(input);
        PossibleIntents intentCategory = findGeneralIntent(keywords);

        return switch (intentCategory) {
            case GENERATE_TEST_SCHEDULE -> new CalendarResponse(new ArrayList<>());
            case GET_GRADING_POLICY, SYLLABUS_OVERVIEW -> new ListResponse(new ArrayList<>());
            case RECOMMEND_TEXTBOOK -> {
                String materials = String.join(" ", syllabus.getTextbook().getInfo());
                yield new ParagraphResponse(materials);
            }
            case GET_PROFESSOR_INFO ->  {
                String contactInfo = String.join(" ", syllabus.getContactInformation().getInfo());
                yield new ParagraphResponse(contactInfo);
            }
            case COURSE_DESCRIPTION -> {
                String courseDesc = String.join(" ", syllabus.getCourseDescription().getInfo());
                yield new ParagraphResponse(courseDesc);
            }
            case CLASSROOM_PROTOCOL ->  {
                String protocol = String.join(" ", syllabus.getClassroomProtocols().getInfo());
                yield new ParagraphResponse(protocol);
            }
            case SEE_DATES ->  {
                List<DatedSyllabusEntity> classSchedule = syllabus.getDatedSyllabusEntites();

                yield new CalendarResponse(classSchedule);
            }
            case OFFICE_HOUR_TIMES ->  {
                String officeHours = String.join(" ", syllabus.getOfficeHours().toString());
                yield new ParagraphResponse("Office Hours are on: " + officeHours);
            }
            case GREETING -> {
                yield new ParagraphResponse("Hello, how can I help?");
            }
            case GET_LOCATION -> {
                String classLocation = String.join(" ", syllabus.getClassroomLocation());
                yield new ParagraphResponse("Classroom Location: " + classLocation);
            }
            default -> null;
        };
    }

    private List<String> findProfessors (String input) {
        return new LinkedList<>();
    }


    public PossibleIntents findGeneralIntent(List<String> keywords) {

        if (keywords.contains("date") || keywords.contains("schedule")) {
            return PossibleIntents.SEE_DATES;
        } else if (keywords.contains("test") || keywords.contains("exam") || keywords.contains("midterm")) {
            return PossibleIntents.GENERATE_TEST_SCHEDULE;
        } else if (keywords.contains("office")) {
            return PossibleIntents.OFFICE_HOUR_TIMES;
        } else if (keywords.contains("textbook") || keywords.contains("materials") || keywords.contains("textbooks")) {
            return PossibleIntents.RECOMMEND_TEXTBOOK;
        } else if (keywords.contains("professor") || keywords.contains("contact info") || keywords.contains("instructor")) {
            return PossibleIntents.GET_PROFESSOR_INFO;
        } else if (keywords.contains("location") || keywords.contains("classroom location") || keywords.contains("classroom")) {
            return PossibleIntents.GET_LOCATION;
        } else if (keywords.contains("grading policy")) {
            return PossibleIntents.GET_GRADING_POLICY;
        } else if (keywords.contains("syllabus overview")) {
            return PossibleIntents.SYLLABUS_OVERVIEW;
        }else if (keywords.contains("description")) {
            return PossibleIntents.COURSE_DESCRIPTION;
        }else if (keywords.contains("protocol") || keywords.contains("protocols") || keywords.contains("rules")) {
            return PossibleIntents.CLASSROOM_PROTOCOL;
        }else if (keywords.contains("hello") || keywords.contains("hi") || keywords.contains("hey")) {
            return PossibleIntents.GREETING;
        }else {
            return PossibleIntents.UNKNOWN;
        }
    }


    public void addSyllabus (String profName, Syllabus syllabus) {
        syllabi.put (profName, syllabus);
    }

    public void setTestSyllabus(Syllabus testSyllabus) {
        this.testSyllabus = testSyllabus;
    }

    public Syllabus getSyllabus(String profLastName) {
        return syllabi.get(profLastName);
    }

    public void updateSyllabus(String profLastName, Syllabus updatedSyllabus) {
        if (syllabi.containsKey(profLastName)) {
            syllabi.put(profLastName, updatedSyllabus);
        } else {
            System.out.println("Syllabus for given professor does not exist.");
        }
    }

    public void removeSyllabus(String profLastName) {
        syllabi.remove(profLastName);
    }
}

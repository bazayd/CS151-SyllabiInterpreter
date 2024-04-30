package com.app.cs151synter.dataManipulation;

import com.app.cs151synter.dataContainers.Syllabus;
import com.app.cs151synter.ui.StudentRawInfo;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

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
        System.out.println("Speak to chat bot (x to quit): ");

        while (scanner.hasNext()) {
            String userInput = scanner.next();

            if (userInput.equals("x")) {
                break;
            }

            IntentResponse response = chatBot.getIntentResponse(userInput, syllabus);

            if (response instanceof ParagraphResponse) {
                System.out.println(((ParagraphResponse) response).generateResponse());
            } else if (response instanceof CalendarResponse) {
                System.out.println(((CalendarResponse) response).generateResponse());
            } else if (response instanceof TodoListResponse) {
                System.out.println(((TodoListResponse) response).generateResponse());
            } else if (response instanceof ListResponse) {
                System.out.println(((ListResponse) response).generateResponse());
            } else {
                System.out.println("Other response..");
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

        List<String> keywords = new ArrayList<>();
        for (CoreLabel token : doc.get(CoreAnnotations.TokensAnnotation.class)) {
            String word = token.get(CoreAnnotations.TextAnnotation.class);
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            if (pos.startsWith("NN") || pos.startsWith("VB") || pos.startsWith("JJ") || pos.startsWith("RB")) {
                keywords.add(word.toLowerCase());
            }
        }
        return keywords;
    }

    public IntentResponse getIntentResponse (String input, Syllabus syllabus) {

        List<String> keywords = performNLPProcessing(input);

//        for (String keyword : keywords) {
//            System.out.println("Keyword in keywords: " + keyword);
//        }


        PossibleIntents intentCategory = findGeneralIntent(keywords);

        return null;
//        System.out.println("Detected intent category: " + intentCategory);
//
//        return switch (intentCategory) {
//            case GENERATE_TEST_SCHEDULE -> new CalendarResponse(new ArrayList<>());
//            case OFFICE_HOUR_TIMES -> new ParagraphResponse("Office Hour Times: ... ");
//            case GET_LOCATION -> new ParagraphResponse("Classroom Location: ");
//            case GET_GRADING_POLICY, SYLLABUS_OVERVIEW -> new ListResponse(new ArrayList<>());
//            case RECOMMEND_TEXTBOOK -> {
//                String materials = String.join(" ", syllabus.getTextbook().getInfo());
//                yield new ParagraphResponse(materials);
//            }
//            case GET_PROFESSOR_INFO ->  {
//                String contactInfo = String.join(" ", syllabus.getContactInformation().getInfo());
//                yield new ParagraphResponse(contactInfo);
//            }
//            case COURSE_DESCRIPTION -> {
//                String courseDesc = String.join(" ", syllabus.getCourseDescription().getInfo());
//                yield new ParagraphResponse(courseDesc);
//            }
//            case CLASSROOM_PROTOCOL ->  {
//                String protocol = String.join(" ", syllabus.getClassroomProtocols().getInfo());
//                yield new ParagraphResponse(protocol);
//            }
//            case SEE_DATES ->  {
//                List<DatedSyllabusEntity> classSchedule = syllabus.getDatedSyllabusEntites();
//
//                yield new CalendarResponse(classSchedule);
//            }
//            default -> null; // Handle unknown intent
//        };
        // TODO UNCOMMENT AND GET IT WORKING
    }

    private List<String> findProfessors (String input) {
        return new LinkedList<>();
    }


    public PossibleIntents findGeneralIntent(List<String> keywords) {

        if (keywords.contains("date") || keywords.contains("schedule")) {
            return PossibleIntents.SEE_DATES;
        } else if (keywords.contains("test") || keywords.contains("exam") || keywords.contains("midterm")) {
            return PossibleIntents.GENERATE_TEST_SCHEDULE;
        } else if (keywords.contains("office hours")) {
            return PossibleIntents.OFFICE_HOUR_TIMES;
        } else if (keywords.contains("textbook") || keywords.contains("materials")) {
            return PossibleIntents.RECOMMEND_TEXTBOOK;
        } else if (keywords.contains("professor") || keywords.contains("contact")) {
            return PossibleIntents.GET_PROFESSOR_INFO;
        } else if (keywords.contains("location")) {
            return PossibleIntents.GET_LOCATION;
        } else if (keywords.contains("grading policy")) {
            return PossibleIntents.GET_GRADING_POLICY;
        } else if (keywords.contains("syllabus overview")) {
            return PossibleIntents.SYLLABUS_OVERVIEW;
        }else if (keywords.contains("description")) {
            return PossibleIntents.COURSE_DESCRIPTION;
        }else if (keywords.contains("protocol") || keywords.contains("protocols")) {
            return PossibleIntents.CLASSROOM_PROTOCOL;
        } else {
            return PossibleIntents.UNKNOWN;
        }
    }


    /*
    *  Following methods allow for adding a Syllabus, getting access to syllabus,
    * updating Syllabus, and removing syllabus.
    * */
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

package dataManipulation;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import java.util.Properties;

public class NLPHelper {
    static StanfordCoreNLP pipeline;
    public static void init()
    {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse");
        pipeline = new StanfordCoreNLP(props);
    }
    public static void findIntent(String text)
    {
        Annotation annotation = pipeline.process(text);
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {
            SemanticGraph sg =
                    sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            String intent = "It does not seem that the sentence expresses an explicit intent.";
            for (SemanticGraphEdge edge : sg.edgeIterable()) {
                if (edge.getRelation().getLongName() == "direct object"){
                    String tverb = edge.getGovernor().originalText();
                    String dobj = edge.getDependent().originalText();
                    dobj = dobj.substring(0,1).toUpperCase() + dobj.substring(1).toLowerCase();
                    intent = tverb + dobj;
                }
            }
            System.out.println("Sentence:\t" + sentence);
            System.out.println("Intent:\t\t" + intent + "\n");

        }
    }

    public static void main(String[] args) {
        init();
        findIntent("I want to see my course schedules");
    }
}

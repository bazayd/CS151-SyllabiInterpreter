package dataManipulation;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.*;
import java.util.*;

public class NLPIntentClassifier {

    private static final String MODEL_FILE = "intent_model.model";
    private static final String TRAINING_FILE = "trainingdata/chattraining.arff";

    public static void main(String[] args) throws Exception {
        // Step 1: Prepare training data
        Instances trainingData = prepareTrainingData();

        // Step 2: Train the model
        Classifier classifier = trainModel(trainingData);

        // Step 3: Save the model to a file
        saveModel(classifier);

        // Step 4: Load the model from the file
        Classifier loadedClassifier = loadModel();

        // Step 5: Classify new messages
        classifyMessages(loadedClassifier);
    }

    private static Instances prepareTrainingData () throws Exception {
        DataSource source = new DataSource(ClassLoader.getSystemResourceAsStream(TRAINING_FILE));
        return source.getDataSet();
    }

//    private static Instances prepareTrainingData() {
//        // Define attributes (message and intent)
//        FastVector attributes = new FastVector(2);
//        Attribute messageAttr = new Attribute("message", (FastVector) null);
//        FastVector intents = new FastVector();
//        for (PossibleIntents intent : PossibleIntents.values()) {
//            intents.addElement(intent.toString());
//        }
//        Attribute intentAttr = new Attribute("intent", intents);
//        attributes.addElement(messageAttr);
//        attributes.addElement(intentAttr);
//
//        // Create Instances object
//        Instances trainingData = new Instances(TRAINING_FILE, attributes, 0);
//        System.out.println (trainingData);
//        // Add examples (messages and intents)
//        addExample(trainingData, "When are the exams?", PossibleIntents.SEE_DATES.toString());
//        addExample(trainingData, "Generate my to-do list", PossibleIntents.GENERATE_TODO_LIST.toString());
//        // Add more examples as needed
//
//        return trainingData;
//    }

//    private static void addExample(Instances dataset, String message, String intent) {
//        Instance instance = new DenseInstance(2);
//        instance.setValue(0, message);
//        instance.setValue(1, intent);
//        dataset.add(instance);
//    }

    private static Classifier trainModel(Instances trainingData) {
        try {
            // Set the class attribute as the last attribute
            trainingData.setClassIndex(trainingData.numAttributes() - 1);

            // Train a Naive Bayes classifier
            Classifier classifier = new NaiveBayes();
            classifier.buildClassifier(trainingData);

            return classifier;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveModel(Classifier classifier) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MODEL_FILE));
        oos.writeObject(classifier);
        oos.close();
        System.out.println("Model saved to " + MODEL_FILE);
    }

    private static Classifier loadModel() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MODEL_FILE));
        Classifier classifier = (Classifier) ois.readObject();
        ois.close();
        System.out.println("Model loaded from " + MODEL_FILE);
        return classifier;
    }

    private static void classifyMessages(Classifier classifier) throws Exception {
        List<String> messages = Arrays.asList(
                "When are the exams?",
                "Generate my to-do list"
                // Add more example messages here
        );

        for (String message : messages) {
            Instance instance = new DenseInstance(2);
            instance.setValue(0, message);
            DataSource source = new DataSource(ClassLoader.getSystemResourceAsStream(TRAINING_FILE));
            Instances instances = source.getDataSet();
            instance.setDataset(instances); // check later
            double classIndex = classifier.classifyInstance(instance);
            String predictedIntent = instances.classAttribute().value((int) classIndex);
            System.out.println("Message: " + message);
            System.out.println("Predicted Intent: " + predictedIntent);
        }
    }
}

package dataManipulation;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.*;

public class NLPIntentClassifier {

    private static final String TRAINING_FILE = "/trainingdata/chattraining.arff";
    private static final String MODEL_FILE = "/trainingdata/message_classifier.model";

    public static void main(String[] args) {
        try {
            // Step 1: Load training data
            Instances trainingData = loadTrainingData(TRAINING_FILE);

            // Step 2: Train the classifier
            Classifier classifier = trainClassifier(trainingData);

            // Step 3: Save the trained model
            saveModel(classifier, MODEL_FILE);

            System.out.println("Model trained and saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Instances loadTrainingData(String fileName) throws Exception {
        InputStream inputStream = NLPIntentClassifier.class.getResourceAsStream(fileName);
        DataSource source = new DataSource(inputStream);
        Instances data = source.getDataSet();
        data.setClassIndex(data.numAttributes() - 1); // Set the class attribute index
        return data;
    }

    private static Classifier trainClassifier(Instances trainingData) throws Exception {
        Classifier classifier = new NaiveBayes();
        classifier.buildClassifier(trainingData);
        return classifier;
    }

    private static void saveModel(Classifier classifier, String fileName) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(classifier);
        oos.close();
    }
}

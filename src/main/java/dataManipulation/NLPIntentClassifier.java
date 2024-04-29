package dataManipulation;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.*;

public class NLPIntentClassifier {

    private static final String TRAINING_FILE = "/trainingdata/chattraining.arff";
    private static final String MODEL_FILE = "intent_model.model";

    public static void main(String[] args) {
        try {
            // Step 1: Load training data
            Instances trainingData = loadTrainingData(TRAINING_FILE);

            // Step 2: Apply StringToWordVector filter
            Instances filteredData = applyStringToWordVectorFilter(trainingData);

            // Step 3: Train the classifier
            Classifier classifier = trainClassifier(filteredData);

            // Step 4: Save the trained model
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


    private static Instances applyStringToWordVectorFilter(Instances data) throws Exception {
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(data);
        return Filter.useFilter(data, filter);
    }

    private static Classifier trainClassifier(Instances trainingData) throws Exception {
        Classifier classifier = new J48(); // Use J48 decision tree classifier
        classifier.buildClassifier(trainingData);
        return classifier;
    }

    private static void saveModel(Classifier classifier, String fileName) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(classifier);
        oos.close();
    }
}


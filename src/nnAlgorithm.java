import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class nnAlgorithm {

    public static void main(String[] args) throws IOException {
        String trainFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet2.csv";
        String testFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet1.csv";

        ArrayList<ArrayList<Integer>> trainData = GetData(trainFile);
        ArrayList<ArrayList<Integer>> testData = GetData(testFile);

        int correctRecognitions = GetNumOfDigitsRecognised(trainData, testData);
        int numOfRows = trainData.size();

        double accuracy = (correctRecognitions * 100 / numOfRows) ;
        System.out.println("Accuracy: " + accuracy + "%");
    }

    // Gets all the data from the text file and places it into an ArrayList of Arraylists
    public static ArrayList<ArrayList<Integer>> GetData(String filePath) {

        File file = new File(filePath);
        ArrayList<ArrayList<Integer> > fileData = new ArrayList();
        ArrayList<Integer> lineData = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null) {
                lineData = new ArrayList<Integer>();
                String[] strImgData = line.split(",");
                for(int i = 0; i < strImgData.length; i++){
                     lineData.add(Integer.parseInt(strImgData[i]));
                }
                fileData.add(lineData);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return fileData;
    }

    // Loops through all of the rows in the training file whilst looping through the rows in the test file to get
    // the row with the smallest Euclidian distance and checks if the digits from the pair of rows matches once a prediction is made.
    public static Integer GetNumOfDigitsRecognised(ArrayList<ArrayList<Integer>> trainFile, ArrayList<ArrayList<Integer>> testFile) {

        ArrayList<Integer> trainRow;
        ArrayList<Integer> testRow;
        int trainDigit; // The actual number represented by the values in the rows in the train file
        int testDigit; // The actual number represented by the values in the rows in the test file
        double result;
        double totalRowResult = 0;
        int numOfImagesRecognised = 0;


        for(int i = 0; i < trainFile.size(); i++) {
            double bestRowDistance = 1000;
            int bestDigit= 100;

            trainRow= new ArrayList<Integer>();
            trainRow.addAll(trainFile.get(i));
            trainDigit = trainRow.get(trainRow.size() - 1);

            for(int j = 0; j < testFile.size(); j++) {
                testRow = new ArrayList<Integer>();
                testRow.addAll(testFile.get(j));
                testDigit = testRow.get(testRow.size() - 1);
                totalRowResult = 0;

                for(int k = 0, l = 0; k < trainFile.get(i).size() && l <  testFile.get(j).size(); k++, l++) {
                    double trainVal = trainFile.get(i).get(k);
                    double testVal = testFile.get(j).get(l);
                    // Calculates the euclidean distance between each pair of cells in the rows being looped
                    result = (testVal - trainVal) * (testVal - trainVal);
                    totalRowResult = totalRowResult + result;
                }
                totalRowResult = Math.sqrt(totalRowResult);

                // Checks if the row's euclidean distance is the least
                if(totalRowResult < bestRowDistance){
                    bestRowDistance = totalRowResult;
                    bestDigit = testDigit;
                }
            }

            if(trainDigit == bestDigit){
                numOfImagesRecognised++;
            }
        }

        return numOfImagesRecognised;
    }


}

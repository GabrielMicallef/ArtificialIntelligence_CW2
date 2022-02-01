import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class nnAlgorithm {
    public static int numOfImagesRecognised;


    public static void main(String[] args) throws IOException {
        String trainFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet1.csv";
        String testFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet2.csv";

        ArrayList<ArrayList<Integer>> trainData = GetData(trainFile);
        ArrayList<ArrayList<Integer>> testData = GetData(testFile);

        //Loop through each line of the training data and a nested loop which checks the smallest difference from the result of the eclidian distance.

        int correctRecognitions = GetNumOfDigitsRecognised(trainData, testData);
        int numOfRows = trainData.size();

        double accuracy = (correctRecognitions / numOfRows) * 100;
        System.out.println(accuracy);
    }

    public static ArrayList<ArrayList<Integer>> GetData(String filePath) {

        File file = new File(filePath);
        ArrayList<ArrayList<Integer> > fileData = new ArrayList<ArrayList<Integer> >();
        ArrayList<Integer> lineData = new ArrayList<Integer>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null) {
                lineData.clear();
                String[] strImgData = line.split(",");
                for(int i = 0; i < strImgData.length; i++){
                    lineData.add(Integer.parseInt(strImgData[i]));
                }
                System.out.println(lineData);
                fileData.add(lineData);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return fileData;
    }

    public static Integer GetNumOfDigitsRecognised(ArrayList<ArrayList<Integer>> trainFile, ArrayList<ArrayList<Integer>> testFile) {
        ArrayList<Integer> trainRow = new ArrayList();
        ArrayList<Integer> testRow = new ArrayList();
        int trainDigit; // The actual number represented by the values in the rows in the train file
        int testDigit; // The actual number represented by the values in the rows in the test file
        double result;
        double totalRowResult = 100;
        double bestRowDistance = 100;
        int bestRow;
        int bestDigit= 100;


        for(int i = 0; i < trainFile.size(); i++) {
            trainRow.addAll(trainFile.get(i));
            trainDigit = trainRow.get(trainRow.size() - 1);

            for(int j = 0; j < testFile.size(); j++) {
                testRow.addAll(testFile.get(j));
                testDigit = testRow.get(testRow.size() - 1);

                for(int k = 0, l = 0; k < trainFile.get(i).size() && l <  testFile.get(j).size(); k++, l++) {
                    // Calculates the euclidean distance between each pair of cells in the rows being looped
                    result = (testFile.get(j).get(l) - trainFile.get(i).get(k)) * (testFile.get(j).get(l) - trainFile.get(i).get(k));
                    totalRowResult = totalRowResult + result;
                }

                totalRowResult = Math.sqrt(totalRowResult);

                // Checks if the row's euclidean distance is the least
                if(totalRowResult < bestRowDistance){
                    bestRowDistance = totalRowResult;
                    bestRow = j;
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

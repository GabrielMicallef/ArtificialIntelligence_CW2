import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;

public class knnAlgorithm {
    public static ArrayList<NearestNeighbour> nns = new ArrayList();


    public static void main(String[] args) throws IOException {
        String trainFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet1.csv";
        String testFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet2.csv";

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
    // the row with the smallest Euclidean distance and checks if the digits from the pair of rows matches once a prediction is made.
    public static Integer GetNumOfDigitsRecognised(ArrayList<ArrayList<Integer>> trainFile, ArrayList<ArrayList<Integer>> testFile) {
        ArrayList<Integer> trainRow;
        ArrayList<Integer> testRow;
        int trainDigit; // The actual number represented by the values in the rows in the train file
        int testDigit; // The actual number represented by the values in the rows in the test file
        int result;
        int totalRowResult = 0;
        int numOfImagesRecognised = 0;


        for(int i = 0; i < trainFile.size(); i++) {
            int bestDigit= 100;
            nns.clear();

            trainRow= new ArrayList<Integer>();
            trainRow.addAll(trainFile.get(i));
            trainDigit = trainRow.get(trainRow.size() - 1);

            for(int j = 0; j < testFile.size(); j++) {
                int rowNo = j;
                testRow = new ArrayList<Integer>();
                testRow.addAll(testFile.get(j));
                testDigit = testRow.get(testRow.size() - 1);
                totalRowResult = 0;

                for(int k = 0, l = 0; k < trainFile.get(i).size() && l <  testFile.get(j).size(); k++, l++) {
                    int trainVal = trainFile.get(i).get(k);
                    int testVal = testFile.get(j).get(l);
                    // Calculates the euclidean distance between each pair of cells in the rows being looped
                    result = (testVal - trainVal) * (testVal - trainVal);
                    totalRowResult = totalRowResult + result;
                }
                totalRowResult = (int) Math.sqrt(totalRowResult);

                nns.add(new NearestNeighbour(rowNo, totalRowResult, testDigit));
            }

            bestDigit = GetMostLikelyDigit();

            if(trainDigit == bestDigit){
                numOfImagesRecognised++;
            }
        }

        return numOfImagesRecognised;
    }

    // Gets the 5 rows with the smallest Euclidean distance and returns the most common digit from the rows as a prediction.
    public static Integer GetMostLikelyDigit(){
        int n = 5;

        // Sorts the array by Distance (Ascending)
        Collections.sort(nns, new SortByDistance());

        // Array to hold the objects with the smallest distances
        ArrayList<NearestNeighbour> bestNNs = new ArrayList();

        // Adds the best objects to an array of objects NearestNeighbour
        for(int i = 0; i < n; i++){
            bestNNs.add(new NearestNeighbour(nns.get(i).rowID, nns.get(i).dist, nns.get(i).testDigit));
        }

        //create a hashmap to store the count of each element . key is number and value is count for the number
        HashMap<Integer, Integer> numberMap = new HashMap<>();

        int result = -1; //result will hold the most frequent element for the given array
        int frequency = -1; //frequency is the count for the most frequent element

        int value;

        for (int i = 0; i < bestNNs.size(); i++) { //scan all elements of the array one by one

            value = -1;
            // set value as -1
            if (numberMap.containsKey(bestNNs.get(i).testDigit)) {
                value = numberMap.get(bestNNs.get(i).testDigit); // if the element is in the map, get the count
            }
            if (value != -1) {
                // value is not -1 , that means the element is in the map. Increment the value and check if it is
                // greater than the maximum
                value += 1;
                if (value > frequency) {
                    //if the value is greater than frequency, it means it is the maximum value
                    // till now. store it
                    frequency = value;
                    result = bestNNs.get(i).testDigit;
                }

                numberMap.put(bestNNs.get(i).testDigit, value); // put the updated value in the map
            } else {
                //element is not in the map. put it with value or count as 1
                numberMap.put(bestNNs.get(i).testDigit, 1);
            }

        }

        if (frequency == 1)
            return -1;

        return result;
    }

}

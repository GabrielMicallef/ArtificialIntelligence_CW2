import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class knnAlgorithm {
    public static ArrayList<NearestNeighbour> nns = new ArrayList();


    public static void main(String[] args) throws IOException {
        String trainFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet1.csv";
        String testFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet2.csv";

        ArrayList<ArrayList<Integer>> trainData = GetData(trainFile);
        ArrayList<ArrayList<Integer>> testData = GetData(testFile);

        int correctRecognitions = GetNumOfDigitsRecognised(trainData, testData);
        int numOfRows = trainData.size();

        float accuracy = (correctRecognitions * 100 / numOfRows) ;
        System.out.println("Accuracy: " + accuracy + "%");
    }

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
                System.out.println(lineData);
                fileData.add(lineData);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return fileData;
    }

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

                // Checks if the row's euclidean distance is the least
                //if(totalRowResult < bestRowDistance){
                //    bestRowDistance = totalRowResult;
                //    bestRow = j;
                //    bestDigit = testDigit;
                //}
            }

            bestDigit = GetMostLikelyDigit();

            if(trainDigit == bestDigit){
                numOfImagesRecognised++;
            }
        }

        return numOfImagesRecognised;
    }

    public static Integer GetMostLikelyDigit(){
        int n = 10;

        Collections.sort(nns, new SortByDistance());

        ArrayList<NearestNeighbour> bestNNs = new ArrayList();

        for(int i = 0; i < n; i++){
            bestNNs.add(new NearestNeighbour(nns.get(i).rowID, nns.get(i).dist, nns.get(i).testDigit));
        }

        Map<Integer, Integer> hp = new HashMap<>();

        for(int i = 0; i < n; i++)
        {
            int key = bestNNs.get(i).dist;
            if(hp.containsKey(key))
            {
                int freq = hp.get(key);
                freq++;
                hp.put(key, freq);
            }
            else
            {
                hp.put(key, 1);
            }
        }

        int max_count = 0, result = -1;

        for(Entry<Integer, Integer> val : hp.entrySet())
        {
            if (max_count < val.getValue())
            {
                result = val.getKey();
                max_count = val.getValue();
            }
        }
        return result;
    }

}

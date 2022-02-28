import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class kmeansAlgorithm {
    public static void main(String[] args) throws IOException {
        String trainFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet1.csv";
        String testFile = "C:\\Users\\Gabriel\\IdeaProjects\\ArtificialIntelligence_CW2\\Train and Test Files\\DataSet2.csv";

        ArrayList<ArrayList<Integer>> trainData = GetData(trainFile);
        ArrayList<ArrayList<Integer>> testData = GetData(testFile);

        // Pass the test array list to a method to get the average row for each digit 0-9

        // Pass the trail array and the new array list of average rows to a modified GetNumOfDigitsRecognised method



//        float accuracy = (correctRecognitions * 100 / numOfRows) ;
//        System.out.println("Accuracy: " + accuracy + "%");
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

}

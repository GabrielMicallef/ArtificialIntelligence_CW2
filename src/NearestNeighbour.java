public class NearestNeighbour {
    int rowID;
    int dist;
    int testDigit;

    public NearestNeighbour(int id, int dist, int digit){
        this.rowID = id;
        this.dist = dist;
        this.testDigit = digit;
    }

    public int rowID(){
        return this.rowID;
    }
    public int dist(){
        return this.dist;
    }
    public int testDigit(){
        return this.testDigit;
    }
}



import java.util.Comparator;

public class SortByDistance implements Comparator<NearestNeighbour> {
    public int compare(NearestNeighbour a, NearestNeighbour b)
    {
        return a.dist - b.dist;
    }
}

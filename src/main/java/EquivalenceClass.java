import java.util.Random;
/**
 *
 * @author Munkhbilguun Munkhdemberel and Matt Strauss
 */
public class EquivalenceClass  implements Comparable<EquivalenceClass> {
    private final int minimum;
    private final int maximum;
    private final int row;
    private final int region;
    private final Random random = new Random();

    public EquivalenceClass(int minimum, int maximum, int row, int region) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.row = row;
        this.region = region;
    }

    public Boolean inClass(int input) {
        return input >= minimum && input <= maximum;
    }

    public int getRow() {
        return row;
    }

    public int getRegion() {
        return region;
    }

    public int getRandomValueInEquivalenceClass()
    {
        return random.nextInt(maximum + 1 - minimum) + minimum;
    }

    @Override
    public int compareTo(EquivalenceClass ec) {
        return Integer.compare(row, ec.row);
    }

    @Override
    public String toString() {
        return "EquivalenceClass{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                ", row=" + row +
                ", region=" + region +
                '}';
    }
}

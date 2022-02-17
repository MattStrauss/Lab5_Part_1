import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
/**
 *
 * @author Munkhbilguun Munkhdemberel and Matt Strauss
 */
public class Checker {
    File file = new File(String.valueOf(Objects.requireNonNull(getClass().getClassLoader().getResource("Eq.txt")).getFile()));
    Scanner scanner = new Scanner(file);
    PrintWriter output = new PrintWriter("test.txt", StandardCharsets.UTF_8);
    ArrayList<EquivalenceClass> equivalenceClasses = new ArrayList<>();

    private final int maxRow;
    private static int currentRow;

    public Checker() throws IOException {
        createEquivalenceClasses();
        maxRow = Collections.max(equivalenceClasses).getRow();
        createTestCases();
    }

    public int foo(int ...args) {

        if (args.length == 0) {
            return -1;
        }

        currentRow = 0;
        int sum = 0;

        for (int arg : args) {
            sum += check(arg);
            currentRow++;
        }

        return sum;
    }


    private int check(int val) {

        for (EquivalenceClass eq : equivalenceClasses) {
            if (eq.getRow() == currentRow) {
                if (eq.inClass(val)) {
                    return eq.getRegion();
                }
            }
        }

        return -1;
    }

    private void createEquivalenceClasses() {
        String nextLine;
        String[] splitLines, splitNumbers;
        int rowCounter = -1;
        while (scanner.hasNextLine()) {
            rowCounter++;
            nextLine = scanner.nextLine();
            splitLines = nextLine.split(";");

            int regionCounter = 0;

            for (String line : splitLines) {
                regionCounter++;

                splitNumbers = line.split(",");

                EquivalenceClass equivalenceClass =
                        new EquivalenceClass(Integer.parseInt(splitNumbers[0].trim()),
                                Integer.parseInt(splitNumbers[1].trim()),
                                rowCounter, regionCounter);

                equivalenceClasses.add(equivalenceClass);

            }
        }
    }

    private void createTestCases() {

        // init the array list of equivalence classes with all from row 0
        // this essentially saves and awkward if/else in the multiplySets method below
        ArrayList<ArrayList<EquivalenceClass>> originalArray = new ArrayList<>();
        for (EquivalenceClass ec : getAllEquivalenceClassesOfRow(0)) {
            ArrayList<EquivalenceClass> equivalenceClassArrayList = new ArrayList<>();
            equivalenceClassArrayList.add(ec);
            originalArray.add(equivalenceClassArrayList);
        }

        ArrayList<ArrayList<EquivalenceClass>> cartesianSet =
                multiplySets(getAllEquivalenceClassesOfRow(1), originalArray, 2);

        StringBuilder outputString = new StringBuilder();
        int sum = 0;

        for (ArrayList<EquivalenceClass> al : cartesianSet) {
            for (EquivalenceClass ec : al) {
                sum += ec.getRegion();
                outputString.append(ec.getRandomValueInEquivalenceClass()).append(",");
            }
            outputString.append(sum);
            outputString.append("\n");
            sum = 0;
        }

        output.println(outputString);
        output.close();
        System.out.println(outputString);

    }

    private ArrayList<ArrayList<EquivalenceClass>> multiplySets
            (ArrayList<EquivalenceClass> equivalenceClasses,
             ArrayList<ArrayList<EquivalenceClass>> arrayList, int nextRow) {

        if (nextRow > maxRow + 1)
            return arrayList;

        ArrayList<ArrayList<EquivalenceClass>> newArrayList = new ArrayList<>();

        for (ArrayList<EquivalenceClass> al : arrayList) {

            for (EquivalenceClass ec : equivalenceClasses) {

                al.add(ec);
                ArrayList<EquivalenceClass> tempCopy = new ArrayList<>(al);
                newArrayList.add(tempCopy);
                al.remove(ec);

            }
        }

        return multiplySets(getAllEquivalenceClassesOfRow(nextRow), newArrayList, ++nextRow);

    }

    private ArrayList<EquivalenceClass> getAllEquivalenceClassesOfRow(int row) {

        ArrayList<EquivalenceClass> newECList = new ArrayList<>();

        for (EquivalenceClass ec : equivalenceClasses) {
            if (ec.getRow() == row) {
                newECList.add(ec);
            }
        }

        return newECList;
    }
}
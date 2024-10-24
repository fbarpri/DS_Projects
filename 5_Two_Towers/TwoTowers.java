import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Fabiola, 905560 <br>
 * This class computes the best combination of an n number of blocks with varying
 * side lengths of √i (i, each of [1, n]) such that two towers are made with their respective
 * heights being as close as possible. <br>
 * To do this, this class contains only a main function that uses a scanner to ask the user
 * how many blocks to use. As soon as this is given, it starts counting down the runtime of the
 * program. It then gets the square root (√) of each value [1, n] to represent the
 * side lengths of each block and adds all the values to a heights ArrayList. While doing this,
 * it adds up all heights to get an optimal height value = sum of all heights / 2. To find the subset
 * and height that best make up the optimal height, SubsetIterator is called to iterate over heights
 * ArrayList and get each possible 'tower' or subset. It then loops over each subset's blocks to add
 * up its height and find the one that is closest (tallest of subsets that is less than or equal to
 * optimal height). Now that the best subset has been found, it is iterated over to convert back from
 * its height values to its corresponding block values, by doing (√i)^2 for each i, in [1, n]. <br>
 * After completing this process, the printed output consists of: optimal height, best subset
 * (shortest tower), height of the best subset, how far away it is from optimal height, and the
 * time it took for the program to run.
 */
public class TwoTowers {
    public static void main (String[] agrs) {
        // Asks user for number of blocks:
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of blocks: ");
        int blocks = scanner.nextInt();

        // Begins recording runtime:
        long startTime = System.currentTimeMillis();

        // Makes a heights ArrayList by getting the square root of each positive
        // integer from 1 - n (blocks) and gets optimal height by adding heights as
        // they are added to heights ArrayList.
        List<Double> heights = new ArrayList<>();
        double totalHeight = 0;
        for (int i = 1; i <= blocks; i++) {
            double height = Math.sqrt(i);
            heights.add(height);
            totalHeight += height;
        }
        double optimalHeight = (totalHeight / 2);


        SubsetIterator subsetIterator = new SubsetIterator(heights);
        double currHeight; // For sum of current iteration's subset.
        double bestHeight = 0; // For height closest to optimal height found.
        List <Double> bestBlockHeights = new ArrayList<>(); // Best subset configuration.

        /* Uses a SubsetIterator to get all possible tower configurations (subsets).
        While doing so, loops through each subset to get total height sum.
        If a current iteration's height is closer to optimal height than the previous
        iteration's it will update bestHeight and bestBlockHeights: */
        while (subsetIterator.hasNext()) {
            currHeight = 0;
            List <Double> currSubset = subsetIterator.next();
            for (Double blockHeight: currSubset) {
                currHeight += blockHeight;
            }
            if (currHeight > bestHeight && currHeight <= optimalHeight) {
                bestHeight = currHeight;
                bestBlockHeights = currSubset;
            }
        }

        List <Integer> bestBlocks = new ArrayList(); // For the respective blocks of bestBlockHeights.
        for (Double height: bestBlockHeights) {
            int block = (int) Math.round ((height * height));
            bestBlocks.add(block);
        }


        System.out.printf("Target (optimal) height: %.14f \n", optimalHeight);
        System.out.printf("Best subset: %s \n", bestBlocks);
        System.out.printf("Best height: %.14f \n", bestHeight);
        System.out.printf("Distance from optimal: %.19f\n", optimalHeight - bestHeight);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Solve duration: " + duration + " ms");
    }
}


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 905560, Fabiola <br>
 * This class is an iterator that loops through a given list and returns all possible subsets of the
 * list, including an empty subset, creating 2^n possible subsets for an n-sized list. It uses binary
 * to represent inclusive and exclusive indices of a list. For example, 011 would indicate the first
 * two elements of a list of size 3 while 111 would indicate all elements. It uses a long,
 * currentSubset that is initialized to 0 and reaches 2^n - 1 in decimal, or all 1's in binary once
 * all subsets have been found for an n-size list, which is used for its hasNext() verification.
 * (i.e. for a size 3 list, this would be 111 in binary = 7 in decimal). <br>
 * For next(), which returns the next subset possible for a list, it checks the currentSubset
 * value using a bitwise & operator that sees if the binary version of currentSubset indicates
 * an exclusion (0) or inclusion (1) of [0, n-1] indices of a list.
 * The rightmost bit of currentSubset represents index 0 of a list, increasing from right to left.
 * Each time next is called, currentSubset is increased until it reaches 2^n - 1, in which case
 * hasNext() returns false.
 * @param <T> For lists containing any generic type.
 */
public class SubsetIterator<T> implements Iterator<List<T>> {

    /**
     * List to generate all possible subsets of. Provided in constructor.
     */
    private List<T> list;
    /**
     * currentSubset represents the subset next() will return in its next call by
     * showing which of the 2^n possible subsets it is will return next.
     * currentSubset in binary is used to show the inclusion (0) & exclusion (1) of certain indices
     * of a given n-sized list. This is such that each bit represents an index of the list starting
     * from 0 at the rightmost bit and increasing to index n - 1 at the leftmost bit.
     */
    private long currentSubset = 0;

    public SubsetIterator (List<T> list) {
        this.list = list;
    }

    /**
     * Checks if all subsets have been returned by next() by seeing if currentSubset value
     * has not yet reached a 2^n value (indicating it has already returned the last 2^n-1
     * possible subset).
     * @return Whether currentSubset is still less than 2^n.
     */
    @Override
    public boolean hasNext() {
        // if curr is not equal to total num of combinations
        return currentSubset < (1L << list.size());
    }

    /**
     * Gets next possible subset of a given list by seeing if each bit in currentSubset
     * is either 0 or 1. It does this by using a bitwise & operator that compares currentSubset
     * with 1L << i, with i increasing to all possible indexes. If index i is exlusive (0), it will
     * evaluate to 0 and if it is inclusive (1) it will evaluate to a non-zero value. If a given
     * index is found to be inclusive, it is then added to the subset ArrayList that will be returned
     * at the end of the call. currentSubset is then increased to prepare for the next possible subset.
     * @return ArrayList of subset including all elements of the indexes marked as inclusive
     * (=1) on currentSubset.
     */
    @Override
    public List<T> next() {
        List<T> subset = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if ((currentSubset & (1L << i)) != 0) {
                subset.add(list.get(i));
            }
        }
        currentSubset++;
        return subset;
    }

    public static void main (String[] args) {
        // Test 1:
        List<String> test = new ArrayList<>(Arrays.asList("a","b","c"));
        SubsetIterator<String> si1 = new SubsetIterator<>(test);
        while (si1.hasNext()) {
            System.out.println(si1.next());
        }

        // Test 2:
        List<Integer> test2 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
        SubsetIterator<Integer> si2 = new SubsetIterator<>(test2);
        int i = 0;
        while (si2.hasNext()) {
            si2.next();
            i++;
        }
        System.out.println(i);
    }
}

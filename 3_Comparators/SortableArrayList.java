import java.util.Comparator;

/**
 * 905560, Fabiola
 * This class is a subclass of SimpleArrayList. Its function is to sort a given ArrayList
 * using the sort() function defined in this class. It also contains two functions: tiesFirst ()
 * and tiesLast() which return all the first or last values in the case of a tie.
 * @param <T> Generic type parameter of data in list.
 */

public class SortableArrayList<T> extends SimpleArrayList<T> {
    public SortableArrayList () {
        super();
    }

    public SortableArrayList (int startingCapacity) {
        super(startingCapacity);
    }

    /**
     * Sort function goes through each unsorted list index from the end of the list to the beginning.
     * It finds the largest value using Comparator (in the unsorted part of the list) and then
     * swaps it to the furthermost right position that is still unsorted, if it is not already there.
     * @param c Comparator used to sort list.
     */
    public void sort(Comparator<? super T> c) {
        // Loops through each list index from end to beginning.
        for (int i = size() -1; i>=0; i--) {
            T largest = get(i);
            int idx = i;

            // Compares all unsorted indexes from beginning to end until arriving at those already sorted:
            for (int j = 0; j < i; j++) {
                int compare = c.compare(get(j), largest);
                if (compare > 0) {
                    largest = get(j);
                    idx = j;
                }
            }

            // Checks if largest is not already at the right furthermost position.
            if ((c.compare(get(i), largest)) != 0) {
                T temp = get(i);
                set(i, largest);
                set(idx, temp);
            }
        }
    }

    /* later note: ok so this basically compares all elements
    from beg to end and finds largest until a slected interval at the end
    it then compares largest with current index in sorted one.
     */

    /**
     * Checks if there are ties for the 'least' amounts as decided by Comparator.
     * If there are no ties, just returns first element, since the list is in ascending order.
     * In the case of a tie, returns string containing all list elements with same 'least' values.
     * @param c Comparator used to check for ties.
     * @return String containing all tied elements.
     */
    public String tiesFirst(Comparator<? super T> c) {
        String first = get(0).toString();

        // Compares values starting from start of list, excluding first element.
        // Adds to return string while they have the same value as first element:
        int i = 1;
        while (i < size() && c.compare(get(0), get(i)) == 0) {
            first += "\nAND " + get(i).toString();
            i++;
        }

        return first;
    }

    /**
     * Checks if there are ties for the 'largest' value as decided by Comparator.
     * If there are no ties, just returns last element, since the list is in ascending order.
     * In the case of a tie, returns string containing all list elements with same 'largest' values.
     * @param c Comparator used to check for ties.
     * @return String containing all tied elements.
     */
    public String tiesLast(Comparator<? super T> c) {
        String last = get(size()-1).toString();

        // Compares values starting from end of list, excluding last element.
        // Adds to return string while they have the same value as last element:
        int i = size()-2;
        while (i >= 0 && c.compare(get(size()-1), get(i)) == 0) {
            last += "\nAND " + get(i).toString();
            i--;
        }

        return last;
    }

}
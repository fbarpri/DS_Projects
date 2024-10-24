import java.util.Comparator;

/**
 * 905560, Fabiola
 * Class used for the creation of Comparators and to sort a student directory given a criteria,
 * which can be: SU Box number, alphabetical order of last names, and number of vowels in full name.
 * While Comparator classes allow for these criteria to be defined for comparisons, the functions
 * in this class are able to get the smallest and largest values according to which criteria
 * (Comparator) is being referenced.
 */

public class DirectorySort {

    /**
     * Inner class compares SU Box numbers, following natural integer order from smallest to largest.
     */
    private static class suBoxComparator implements Comparator<Student> {
        public int compare(Student a, Student b) {
            if (a.getSuBox() < b.getSuBox()) {
                return -1;
            }
            if (a.getSuBox() > b.getSuBox()) {
                return 1;
            } else return 0;
        }
    }

    /**
     * Calls on sort() function from SortableArrayList class and sorts Student Directory in ascending order
     * using Comparator defined in suBoxComparator class. Then calls on tiesFirst() from SortableArrayList
     * to check for ties.
     * @param list Student directory.
     * @return String containing whichever Student(s) have the smallest SU Box number.
     */
    public static String getSmallestSU (SortableArrayList list) {
        list.sort(new suBoxComparator());
        return list.tiesFirst(new suBoxComparator());
    }

    /**
     * Calls on sort() function from SortableArrayList class and sorts Student Directory in ascending order
     * using Comparator defined in suBoxComparator class. Then calls on tiesLast() from SortableArrayList
     * to check for ties.
     * @param list Student directory.
     * @return String containing whichever Student(s) have the largest SU Box number.
     */
    public static String getLargestSU (SortableArrayList list) {
        list.sort(new suBoxComparator());
        return list.tiesLast(new suBoxComparator());
    }

    /**
     * Inner class compares last names alphabetically.
     */
    private static class lastNameComparator implements Comparator<Student> {
        public int compare(Student a, Student b) {
            return a.getLastName().toLowerCase().compareTo(b.getLastName().toLowerCase()); // do i need to compare in lower case?? is it ordered alphabetically?
        }
    }

    /**
     * Calls on sort() function from SortableArrayList class and sorts Student Directory in ascending order
     * using Comparator defined in lastNameComparator class. Then calls on tiesFirst() from SortableArrayList
     * to check for ties.
     * @param list Student directory.
     * @return String containing whichever Student(s) have their last names first in an alphabetically ordered list.
     */
    public static String getFirstOfLastNames (SortableArrayList list) {
        list.sort(new lastNameComparator());
        return list.tiesFirst(new lastNameComparator());
    }

    /**
     * Calls on sort() function from SortableArrayList class and sorts Student Directory in ascending order
     * using Comparator defined in lastNameComparator class. Then calls on tiesLast() from SortableArrayList
     * to check for ties.
     * @param list Student directory.
     * @return String containing whichever Student(s) have their last names last in an alphabetically ordered list.
     */
    public static String getLastOfLastNames (SortableArrayList list) {
        list.sort(new lastNameComparator());
        return list.tiesLast(new lastNameComparator());
    }


    /**
     * Inner class compares the amount of vowels in Student's full names.
     */
    private static class vowelNameComparator implements Comparator<Student> {
        public int compare(Student a, Student b) {
            String aFullName = a.getFirstName().toLowerCase() + a.getLastName().toLowerCase();
            String bFullName = b.getFirstName().toLowerCase() + b.getLastName().toLowerCase();
            int aVowelCount = 0;
            int bVowelCount = 0;

            // Loops through each character in Student's full name to count how many vowels there are:
            for (char c: aFullName.toCharArray()) {
               if (c=='a' || c=='e' || c=='i' || c=='o' || c=='u') aVowelCount++;
            }

            for (char c: bFullName.toCharArray()) {
               if (c=='a' || c=='e' || c=='i' || c=='o' || c=='u') bVowelCount++;
           }

            if (aVowelCount<bVowelCount) return -1;
            if (aVowelCount>bVowelCount) return 1;
            else return 0;
        }
    }

    /**
     * Calls on sort() function from SortableArrayList class and sorts Student Directory in ascending order
     * using Comparator defined in vowelNameComparator class. Then calls on tiesFirst() from SortableArrayList
     * to check for ties.
     * @param list Student directory.
     * @return String containing whichever Student(s) have the least amount of vowels in their full names.
     */
    public static String getLeastVowels (SortableArrayList list) {
        list.sort(new vowelNameComparator());
        return list.tiesFirst(new vowelNameComparator());
    }

    /**
     * Calls on sort() function from SortableArrayList class and sorts Student Directory in ascending order
     * using Comparator defined in vowelNameComparator class. Then calls on tiesLast() from SortableArrayList
     * to check for ties.
     * @param list Student directory.
     * @return String containing whichever Student(s) have the most amount of vowels in their full names.
     */
    public static String getMostVowels (SortableArrayList list) {
        list.sort(new vowelNameComparator());
        return list.tiesLast(new vowelNameComparator());
    }

    /**
     * Inner class compares the occurrences of any single digit in a phone number.
     */
    private static class phoneComparator implements Comparator<Student> {
        /**
         * Counts the occurrences of a given digit in a given phone number.
         * @param phoneNumber to loop over and count occurrences.
         * @param digit to search for in phone number.
         * @return count of occurrences of given digit
         */
        private int numCount(String phoneNumber, int digit) {
            char charDigit = String.valueOf(digit).charAt(0);
            int count = 0;

            // Loops through all digits in a phone number:
            for (char phoneDigit : phoneNumber.toCharArray()) {
                if (phoneDigit == charDigit) {
                    count++;
                }
            }

            return count;
        }

        public int compare(Student a, Student b) {
            int aCount = 0;
            int bCount = 0;

            // Count occurrences for all possible digits 0-9:
            for (int i = 0; i < 10; i++) {
                int currentACount = numCount(a.getPhone(), i);
                if (currentACount > aCount) {
                    aCount = currentACount;
                }
            }

            for (int i = 0; i < 10; i++) {
                int currentBCount = numCount(b.getPhone(), i);
                if (currentBCount > bCount) {
                    bCount = currentBCount;
                }
            }

            if (aCount<bCount) return -1;
            if (aCount>bCount) return 1;
            else return 0;
        }
    }

    /**
     * Calls on sort() function from SortableArrayList class and sorts Student Directory in ascending order
     * using Comparator defined in phoneComparator class. Then calls on tiesLast() from SortableArrayList
     * to check for ties.
     * @param list Student directory.
     * @return String containing whichever Student(s) has the most occurrences of any digit in their phone number.
     */
    public static String getMostSameDigit (SortableArrayList list) {
        list.sort(new phoneComparator());
        return list.tiesLast(new phoneComparator());
    }

    public static void main (String[]args) {
        // Answers to questions:
        SortableArrayList studentsList = Student.getStudentList();
        System.out.println("\n(a) " + getSmallestSU(studentsList));
        System.out.println("\n(b) " + getLargestSU(studentsList));
        System.out.println("\n(c) " + getFirstOfLastNames(studentsList));
        System.out.println("\n(d) " + getLastOfLastNames(studentsList));
        System.out.println("\n(e) " + getMostVowels(studentsList));
        System.out.println("\n(f) " + getLeastVowels(studentsList));
        System.out.println("\n(g) " + getMostSameDigit(studentsList));
    }

}

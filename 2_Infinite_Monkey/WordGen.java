import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Fabiola, ID: 905560
 * This class is used as the main runner class, which asks user to input a
 * filename and given value of k. Given a filename, it will check if file is valid
 * and will then process file contents by calling on methods in SequenceTable class
 * in order to reproduce a sample text of 500 characters of a similar writing style
 * as the input file given. The class contains methods capable of turning file contents
 * into a String, checking if filename given is valid (file exists and is readable),
 * as well as setting and checking the validity of values of k.
 */

public class WordGen {
    /**
     * Read the contents of a file into a string. If the file does not
     * exist or cannot not be read for any reason, returns null.
     *
     * @param filename The name of the file to read.
     * @return The contents of the file as a string, or null.
     */
    private static String readFileAsString(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Checks if filename given returns a valid file that can be read. If file cannot be found,
     * fileContents will be null, as specified in readFileAsString() method. It uses a
     * boolean to see if file has readable content, which only becomes true upon checking that
     * file content (in String fileContents) is not null. If file content is null, it will ask
     * user to input filename again until a valid filename is given.
     *
     * @param scanner      Used for user input.
     * @param fileContents Initial file contents found using readFileAsString().
     * @param filename     Initial filename given by user.
     */
    public static void checkValidFile(Scanner scanner, String fileContents, String filename) {
        boolean validFile = false;
        while (!validFile) {

            if (fileContents == null) {
                System.out.print ("Please check the directory of the file and try again. Enter file to read: ");
                filename = scanner.nextLine();
                fileContents = readFileAsString(filename);
            } else {
                validFile = true;
            }

        }
    }

    /**
     * Asks user to input a value for k. It then checks if k given is a valid value
     * (whole number in range [0, 10]). It uses a boolean (validK) which only becomes true
     * after checking that k is an integer and between 0-10. If k is an invalid value, it will
     * ask user to input a new value until given one that passes all conditions. After confirming
     * value of k, the function returns the k value given.
     *
     * @param scanner Used for user input.
     * @return Valid value of k.
     */
    public static int setK (Scanner scanner) {
        boolean validK = false;
        int k = 0;
        while (!validK) {
            System.out.print("Enter desired value of k: ");

            // Checks if k value is a whole number:
            if  (scanner.hasNextInt()) {
                k = scanner.nextInt();

                // Also checks if k value is between 0 and 10:
                if (k >= 0 && k <= 10) {
                    validK = true;
                } else {
                    System.out.println("K must be positive and between 0 and 10 (inclusive).");
                }

            } else {
                System.out.println ("K must be an integer.");
                scanner.next(); // Discards real number (previously entered) and waits for next input.
            }
        }
        return k;
    }

    public static void main (String[]args) {
        Scanner scanner = new Scanner(System.in);

        // Reads file, check if valid, and stores contents into a string to be processed:
        System.out.print("Enter file to read: ");
        String filename = scanner.nextLine();
        String fileContents = readFileAsString(filename);
        checkValidFile (scanner, fileContents, filename);

        // Sets k value, verifies it and creates new SequenceTable to return generated text:
        SequenceTable sequenceTable = new SequenceTable(setK(scanner), fileContents);
        sequenceTable.readInput();
        System.out.println(sequenceTable.generateText());
    }

}
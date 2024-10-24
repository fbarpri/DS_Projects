import java.util.HashMap;

/**
 * Fabiola, ID: 905560
 * This class maps k-sequences found in a sample text (from file read in WordGen class)
 * to their corresponding FrequencyMaps. This class is thus capable of reading through the
 * contents of each file and extracting every k-sequence and the character that follows each
 * sequence (nextChar) (input text processing). Using this sequence table map, it is able to
 * generate a random text of 500 characters based on the letter probabilities collected (output generation).
 */

public class SequenceTable {
    /**
     * Maps k-sequences (String) to their corresponding frequency Maps
     */
    private HashMap <String, FrequencyMap> sequenceFreqTable;
    /**
     * K-value for analysis, may be any integer between 0 and 10
     */
    private int k;
    /**
     * String containing all contents from input file.
     */
    String fileContents;

    public SequenceTable (int k, String fileContents) {
        sequenceFreqTable = new HashMap<>();
        this.k = k;
        this.fileContents = fileContents;
    }

    /**
     * For processing of input text:
     * Given a k-sequence from input text (sequence) and the character that follows it (nextChar),
     * it will add the sequence to the Sequence Table if not already there, and create a character
     * frequency map for it as well if it doesn't have one. It will then add the nextChar to the
     * frequency map related to the sequence by calling on addFrequency() from the FrequencyMap class.
     *
     * @param sequence Given k-sequence.
     * @param nextChar Character that follows k-sequence.
     */
    public void addSequence (String sequence, char nextChar) {
        FrequencyMap sequenceMap = sequenceFreqTable.get(sequence);

        // For new k-sequences, creates corresponding Frequency Map and adds key to Sequence Table:
        if (sequenceMap == null) {
            sequenceMap = new FrequencyMap();
            sequenceFreqTable.put(sequence, sequenceMap);
        }

        sequenceMap.addFrequency(nextChar);
    }

    /**
     * For processing of input text:
     * Will loop through all characters of input text and extract k-sequences and their respective
     * following character (nextChar) to be stored in Sequence Table using addSequence(). Prepends last
     * k-value sequence to the text to ensure there is no unique k-sequence encountered.
     */
    public void readInput () {
        String sequence;
        char nextChar;

        // Prepends final k-sequence to file text:
        String finalKSeq = fileContents.substring(fileContents.length()-k, fileContents.length());
        String extendedFileContents = finalKSeq + fileContents;

        // Loops through file contents to extract sequence and nextChar:
        for (int i = 0; i < extendedFileContents.length() - k; i++) {
            sequence = extendedFileContents.substring (i, i+k);
            nextChar = extendedFileContents.charAt (i+k);
            this.addSequence(sequence, nextChar);
        }
    }

    /**
     * For creation of output text:
     * Given a k-sequence, will use the probability computed in getNextChar() of the FrequencyMap
     * class to generate a character that will follow the sequence.
     *
     * @param sequence K-value sequence.
     * @return Generated character that will follow k-sequence.
     */
    public Character getNextChar (String sequence) {
        return sequenceFreqTable.get(sequence).getNextChar();
    }

    /**
     * For the creation of output text:
     * Generates 500-character text using the letter probabilities of input text calculated in FrequencyMap.
     * Starts output text with the initial k-sequence of the input text and appends a new character at the
     * end of each k-sequence encountered, as it moves up one character at a time while it appends new characters.
     *
     * @return Full generated text created using StringBuilder.
     */
    public StringBuilder generateText() {
        StringBuilder generatedText = new StringBuilder(fileContents.substring(0,k));
        int i = 0;

        // Keeps adding a new character at the end of each k-sequence until it reaches 500 characters:
        while (generatedText.length() < 500) {
            String sequence = generatedText.substring(i, i+k);
            char nextChar = getNextChar(sequence);
            generatedText.append(nextChar);
            i ++ ;
        }

        return generatedText;
    }

    /**
     * Returns readable representation of string version of Sequence Table
     * @return
     */
    public String toString () {
        return sequenceFreqTable.toString();
    }

    static public void main (String[]args) {
        // Test code.
        SequenceTable test = new SequenceTable(2, "I am Groot. I am Groot. I am Groot.");
        System.out.println(test);
        test.readInput();
        System.out.println(test);
        System.out.println(test.generateText());
    }

}

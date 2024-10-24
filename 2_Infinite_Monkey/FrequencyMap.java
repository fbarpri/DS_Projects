import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Fabiola, ID: 905560
 * This class's function is to make a Frequency Map for every k sequence that maps each character that has
 * been encountered following a k-sequence in the input file to its frequency.
 */
public class FrequencyMap {
    /**
     * Maps individual characters (referred to as nextChar) to their frequencies (amount of times
     * they've appeared following a k-sequence).
     */
    private HashMap<Character, Integer> charFrequencyMap;

    public FrequencyMap() {
        charFrequencyMap = new HashMap<>();
    }

    /**
     * For the processing of input file:
     * If character has already been encountered in input file processing, simply adds (+1)
     * to a given character's frequency upon encountering it in input file. Otherwise,
     * it adds key (character) and makes frequency value = 1.
     * @param nextChar Character that follows k-sequence.
     */
    public void addFrequency(char nextChar) {
        if (charFrequencyMap.containsKey(nextChar)) {
            charFrequencyMap.put(nextChar, charFrequencyMap.get(nextChar) + 1);
        } else {
            charFrequencyMap.put(nextChar, 1);
        }
    }

    /**
     * For creation of output text:
     * Adds up all frequencies of each character corresponding to a specific k-sequence
     * to use in probability calculations in getNextChar().
     * @return Sum of all frequencies in a given FrequencyMap object.
     */
    public int getCharCumulativeFreq () {
        int cumulativeFrequency = 0;

        for (int frequency: charFrequencyMap.values()) {
            cumulativeFrequency += frequency;
        }

        return cumulativeFrequency;
    }

    /**
     * For creation of output text:
     * Using cumulative frequency from getCumulativeFreq(), creates upper boundary for the generation of a random
     * integer. It will then loop through key,value (character, frequency) pairs of Frequency Map to start adding up
     * the frequencies until it reaches an equal or greater value than the randomly generated integer (it falls within
     * cumulative frequency range). Once it reaches this, it will return the associated value (character), which will
     * be the generated nextCharacter that will follow a given k-sequence.
     * @return Generated character that follows a given k-sequence.
     */
    public Character getNextChar () {
        // Generates random integer using character's cumulative frequency:
        int totalFrequency = this.getCharCumulativeFreq();
        Random random = new Random();
        int randomNumber = random.nextInt(totalFrequency+1);

        // Loops through char, freq pairs adding up frequencies until it reaches random
        // int value and sets corresponding character as nextChar to follow a k-sequence.
        int cumulativeFreq = 0;
        char nextChar = 0;
        for (Map.Entry <Character, Integer> entry : charFrequencyMap.entrySet()) {
            cumulativeFreq += entry.getValue();
            if (cumulativeFreq >= randomNumber) {
                nextChar = entry.getKey();
                break;
            }
        }

        return nextChar;
    }

    public String toString () {
        return charFrequencyMap.toString();
    }

    static public void main (String[]args) {
        // Test code.
        FrequencyMap ab = new FrequencyMap(); // given k=2 sequence 'ab' for example
        System.out.println(ab);
        ab.addFrequency('d');
        System.out.println(ab);
        ab.addFrequency('b');
        System.out.println(ab);
        ab.addFrequency('e');
        System.out.println(ab);
        System.out.println(ab.getCharCumulativeFreq());
        System.out.println(ab.getNextChar());
    }
}
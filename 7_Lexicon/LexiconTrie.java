import java.io.File;
import java.util.*;

/**
 * Fabiola, 905560 <br>
 * This class represents a lexicon trie made up of LexiconNodes. A LexiconTrie has as its root (first
 * LexiconNode), a LexiconNode representing a space character ' '. After the root, it is made up of LexiconNodes
 * representing a given letter in lowercase of the alphabet and connecting to its children, which are the consequent
 * letters that follow in a word contained in a lexicon. A word is marked by its final letter, represented by
 * a LexiconNode, through its isWord boolean attribute. (i.e. for the word 'hello', the LexiconNode representing
 * the 'o' character would have its isWord attribute set to true). As the LexiconTrie is traversed, a prefix is
 * built, which is a string containing all the LexiconNode's letters it has gone through,
 * which only becomes a word once a LexiconNode with a isWord attribute is encountered. <br>
 * Contains methods to see if a word or prefix exists in lexicon containsWord() and containsPrefix().
 * Also allows words to be added manually through addWords(), or addWordsFromFile() to add words from file.
 * Contains an iterator that iterates over all words in lexicon, quantified through the attribute numWords.
 * Also has a suggestCorrections () method which suggest words within a max distance of a target word, and
 * a matchRegex() method which matches a given regex pattern with matches found in lexicon.
 */
public class LexiconTrie implements Lexicon {
    /**
     * This is the root of the Lexicon, a LexiconNode containing only a space character (' ').
     */
    private LexiconNode root = new LexiconNode(' ');
    /**
     * Number of words in lexicon.
     */
    private int numWords;

    @Override
    public int numWords() {
        return numWords;
    }

    /**
     * Adds word to lexicon. If word is already in lexicon, it is not added.
     * Word is also automatically converted to lowercase and numWords attribute is increased.
     * After, it calls on helper method, addWordHelper to add word.
     *
     * @param word Word to add to the lexicon.
     * @return If word was added successfully, returns false if word was already in lexicon.
     */
    @Override
    public boolean addWord(String word) {
        if (containsWord(word)) return false; // Word is already in lexicon.
        word = word.toLowerCase();
        numWords++;
        return addWordHelper(word, root); // Calls on helper method.
    }

    /**
     * Helper method for addWord(). It recursively adds a given word's first char to lexicon.
     * For each call it therefore uses the first character of word String.
     * If a LexiconNode already exists to represent the first character it continues to next call.
     * If not, a new LexiconNode child is added representing the first character, and then it continues
     * to next call. For each call, the word substring is reduced by removing the first character which
     * has been added. The node of the firstChar is also passed along so that its children can be modified
     * if needed in the next recursive call.
     *
     * @param word        Word to add to lexicon.
     * @param lexiconNode Parent lexiconNode whose children will be searched for first character of word.
     * @return If adding word was successful.
     */
    private boolean addWordHelper(String word, LexiconNode lexiconNode) {
        // Base case: word is empty as every letter has been added to lexicon.
        if (word.isEmpty()) return true;

        // Finds LexiconNode child corresponding to first character in word.
        LexiconNode firstChar = lexiconNode.getChild(word.charAt(0));

        // If no child node exists for the first character, create one:
        if (lexiconNode.getChild(word.charAt(0)) == null) {
            firstChar = new LexiconNode(word.charAt(0));
            lexiconNode.addChild(firstChar);
        }

        // If down to last character in word, make node true for isWord attribute.
        if (word.length() == 1) {
            firstChar.makeIsWord();
        }

        // Recursively call on word string (with first character deleted, as it was already added)
        // and pass on node of first char added in this call to continue traversing through lexicon.
        return addWordHelper(word.substring(1), firstChar);

    }

    /**
     * Removes a given word from lexicon. If word is not in lexicon, method returns false.
     * Traverses through letters in word to get last node, and set its isWord attribute to false.
     * Reduces number of words in lexicon and calls on helper method using word to remove and the last node
     * of the word.
     *
     * @param word Lowercase word to remove from the lexicon.
     * @return If removal was successful. False if word was not in lexicon.
     */
    @Override
    public boolean removeWord(String word) {
        // If word not in lexicon.
        if (!containsWord(word)) return false;

        // Traverse lexicon to find last node of word.
        LexiconNode curr = root;
        for (char c : word.toCharArray()) {
            curr = curr.getChild(c);
        }
        curr.removeIsWord(); // Remove isWord attribute of last character.
        numWords--;

        // Call helper method with word and last node of word.
        return removeWordHelper(word, curr);
    }

    /**
     * Helper method for removeWord(). It recursively removes the last letter of a word from lexicon.
     * It does this by deleting itself from its parent's children. It continues this until all letters
     * from word have been deleted or the letter of the current call is part of another word (has children),
     * or is the ending of another word (has an isWord attribute).
     *
     * @param word Word to be removed.
     * @param ln   LexiconNode representing current last character of word.
     * @return If removal was successful. Should always return true since possibility for false was checked in
     * removeWord() method.
     */
    private boolean removeWordHelper(String word, LexiconNode ln) {
        // If all characters in word have been removed, ln is part of another word,
        // or ln is the word ending of another word, end recursion.
        if (word.isEmpty() || ln.isWord() || ln.hasChildren()) return true;

        // Removes LexiconNode from list of children, removing the current character.
        LexiconNode parent = ln.getParent();
        parent.removeChild(ln);

        // Continues recursion with reduced word string (last character deleted)
        // and with the parent of the removed letter.
        return removeWordHelper(word.substring(0, word.length() - 1), parent);
    }

    /**
     * Checks if given word exists within lexicon. Calls on helper method
     * containsWordHelper () to achieve this recursively.
     *
     * @param word Lowercase word to lookup in the lexicon.
     * @return If word is contained in lexicon.
     */
    @Override
    public boolean containsWord(String word) {
        return containsWordHelper(word, root);
    }

    /**
     * Helper method for containsWord() method. Checks first character of current word
     * and if no matching LexiconNode is found, returns false.
     * Only if all characters have been found and the last character has an isWord marker,
     * will the method return true.
     * Recursive calls are done with decreasing word String (first character removed) and
     * the node of the first character.
     *
     * @param word   Word to search for.
     * @param parent Parent of first character in word.
     * @return If word is contained in lexicon.
     */
    private boolean containsWordHelper(String word, LexiconNode parent) {
        // Get LexiconNode corresponding to first character.
        LexiconNode firstChar = parent.getChild(word.charAt(0));

        // If no such lexicon node exists, return false.
        if (firstChar == null) {
            return false;
        }

        // If word has been reduced to last letter and last letter has isWord marker, return true.
        // If the last letter doesn't have the marker, it will return false.
        if (word.length() == 1) { // last letter has marker
            return firstChar.isWord();
        }

        // Continue recursive call without first character of word and with the LexiconNode of the first character.
        return containsWordHelper(word.substring(1), firstChar);
    }

    /**
     * Checks if a prefix is contained in the lexicon. To achieve this, it calls on
     * containsPrefix ().
     *
     * @param prefix Lowercase prefix to lookup in the lexicon.
     * @return If the prefix is in the lexicon.
     */
    @Override
    public boolean containsPrefix(String prefix) {
        return prefixHelper(prefix, root);
    }

    /**
     * Helper method for containsPrefix (). Checks if a node exists for the first character of the
     * given prefix. If no such node character exists, returns false. If all leters have a corresponding
     * LexiconNode, returns true.
     *
     * @param prefix Prefix to search for in lexicon.
     * @param parent Parent to be searched for first character.
     * @return If prefix is found in lexicon.
     */
    private boolean prefixHelper(String prefix, LexiconNode parent) {
        // If all the letters of the prefix word have been found, return true.
        if (prefix.isEmpty()) {
            return true;
        }

        // Get node corresponding to first child.
        LexiconNode firstChar = parent.getChild(prefix.charAt(0));
        // If no node found, char in prefix isn't in lexicon, return false.
        if (firstChar == null) {
            return false;
        }

        // Continue recursive call with reduced prefix String (without first char) and first char LexiconNode.
        return prefixHelper(prefix.substring(1), firstChar);
    }

    /**
     * Reads words in text file with a word in every line and adds them to lexicon.
     * Does this by calling addWord() on each line read of the file.
     *
     * @param filename Name of the file to read.
     * @return Number of words added to lexicon from file.
     */
    @Override
    public int addWordsFromFile(String filename) {
        // Initializes scanner for file to be read.
        Scanner scan;
        try {
            scan = new Scanner(new File(filename));
        } catch (Exception e) { // If error encountered in reading file.
            System.out.println("Error! Couldn't read file: " + e.getMessage());
            return -1;
        }

        // Adds word from each line in the file. Every time it does so, adds up to word count.
        int words = 0;
        while (scan.hasNext()) {
            String line = scan.nextLine();
            addWord(line);
            words++;
        }

        scan.close();
        return words;
    }

    /**
     * Reads in all words in lexicon and adds them to a list. Finds all words using helper method
     * iteratorHelper(). Returns iterator for list of words.
     *
     * @return Iterator for list of words.
     */
    @Override
    public Iterator<String> iterator() {
        // List to add words.
        List<String> words = new ArrayList<>();
        // Calls iteratorHelper() for all of the children of the root.
        for (LexiconNode child : root) {
            iteratorHelper("", child, words);
        }
        return words.iterator();
    }

    /**
     * Helper method for iterator().
     *
     * @param prefix Prefix created through all LexiconNodes traversed.
     * @param ln     LexiconNode currently at.
     * @param words  List of words in lexicon.
     */
    public void iteratorHelper(String prefix, LexiconNode ln, List<String> words) {
        // Adds letter of LexiconNode to word.
        prefix += ln.getLetter();

        // If LexiconNode is a word, adds currWord to list of words.
        if (ln.isWord()) {
            words.add(prefix);
        }

        // Continues search for all LexiconNode children of the lexicon.
        for (LexiconNode child : ln) {
            iteratorHelper(prefix, child, words);
        }
    }

    /**
     * Suggests words that are within a specified maxDistance of a given target word.
     * Allows for alternatives to be provided for possibly misspelled words. Does this by
     * searching through all words in lexicon, done by calling the helper method suggCorrectionsHelper()
     * on all the children of the root. Adds all suggestions to a set.
     *
     * @param target      Target word to be corrected.
     * @param maxDistance Maximum word distance of suggested corrections.
     * @return Set of suggestions within maximum distance of given words.
     */
    @Override
    public Set<String> suggestCorrections(String target, int maxDistance) {
        // Set to add suggestions.
        Set<String> suggestions = new HashSet<>();
        // Calls helper method on all the children of the root to search for words.
        for (LexiconNode child : root) {
            suggCorrectionsHelper(target, maxDistance, "", suggestions, child);
        }
        return suggestions;
    }

    /**
     * Adds found suggestions for target word to Set of suggestions if a word is found within th maximum
     * distance. Does this by adding a current LexiconNode's letter to a prefix and checking if it matches
     * the target word at its position. If not, decreases maxDistance allowed and continues traversing
     * through lexicon and adding letters to prefix through recursive calls. If maxDistance drops below 0,
     * it is no longer a valid suggestion and that recursion is ended.
     *
     * @param target      Target word to search suggestions for.
     * @param maxDistance Maximum distance allowed between target and found (prefix) word.
     * @param prefix      Word built up so far through recursive calls.
     * @param suggestions Set of suggestions.
     * @param ln          Current LexiconNode.
     */
    private void suggCorrectionsHelper(String target, int maxDistance, String prefix, Set<String> suggestions, LexiconNode ln) {
        // If no longer a valid distance away from target or not the length of the target, break recursion.
        if (maxDistance < 0 || prefix.length() >= target.length()) return;

        // Add letter of current Lexicon Node to prefix:
        prefix += ln.getLetter();

        // Compares the last character of the prefix with the character at the same position
        // of the target word.
        boolean sameChar = prefix.charAt(prefix.length() - 1) == target.charAt(prefix.length() - 1);

        // If not same characters:
        if (!sameChar) {
            // If the maxDistance is at 0, return since all characters must be the same at this distance.
            if (maxDistance == 0) return; // For 0, the last (target) char have to be the same.
            // If distance more than 0, simply decrease the distance.
            maxDistance--;
        }

        // If prefix length has reached same length as target and prefix is a valid word, add to suggestions.
        if (prefix.length() == target.length() && containsWord(prefix)) {
            suggestions.add(prefix);
            return;
        }

        // Recurse through all child nodes.
        for (LexiconNode child : ln) {
            suggCorrectionsHelper(target, maxDistance, prefix, suggestions, child);
        }
    }

    /**
     * Matches a given regex pattern to possible matches found in lexicon. Makes use
     * of regexHelper () which is called on all child nodes of the root. Once a match is found
     * using regex helper it is added to matches Set.
     *
     * @param pattern Regular expression pattern to match.
     * @return Set of matches found.
     */
    @Override
    public Set<String> matchRegex(String pattern) {
        // Set to store matches in.
        Set<String> matches = new HashSet<>();
        // Calls helper function on all the children of the root.
        for (LexiconNode child : root) {
            regexHelper(pattern, "", matches, child);
        }
        return matches;
    }

    private void regexHelper(String pattern, String prefix, Set<String> matches, LexiconNode ln) {
        if (pattern.length() == 0) return; // The entire pattern has been matched.

        // Adds LexiconNode's letter to prefix.
        prefix += ln.getLetter(); // Adds LexiconNode's letter to prefix.

        // First character of pattern String:
        char patternFirst = pattern.charAt(0);

        // Last character of prefix String added using current LexiconNode:
        char prefixLast = prefix.charAt(prefix.length() - 1);

            // If first char of pattern is *.
        if (patternFirst == '*') {
            // If * is last char and current prefix is word, add to matches.
            if (pattern.length() == 1 && containsWord(prefix)) {
                matches.add(prefix);
                // If char after * matches last char of prefix just added, can remove both * and the char from pattern.
            } else if (pattern.length() > 1 && pattern.charAt(1) == prefixLast) {
                pattern = pattern.substring(2);
                // If after removing, pattern length is 0 and prefix is a valid word, add to matches.
                if (pattern.length() == 0 && containsWord(prefix)) {
                    matches.add(prefix);
                }
            } // If doesn't fulfill any of the if statements, pattern remains unchanged, and it
              // should continue adding words to prefix until one of the above if statements is fulfilled.

            // If pattern's first char and prefix's last char are the same or pattern's first char is _ .
            } else if (patternFirst == prefixLast || patternFirst == '_') {
                // If at last character and prefix is a valid word, add to matches.
                if (pattern.length() == 1 && containsWord(prefix)) {
                    matches.add(prefix);
                }
                // Remove the first character of pattern which has been matched when adding a letter to prefix.
                pattern = pattern.substring(1);

            // If first char of pattern is '?'.
            } else if (patternFirst == '?') {
                // If '?' is last char and prefix is a valid word, add to matches.
                if (pattern.length() == 1 && containsWord(prefix)) { // If ? is last char.
                    matches.add(prefix);
                }
                // If prefix's last char just added matches char after '?', add to matches.
                if (pattern.length() == 2 && pattern.charAt(1) == prefixLast && containsWord(prefix)) {
                    matches.add(prefix);
                    pattern = pattern.substring(1); // Remove '?', will remove letter in next line.
                }
                pattern = pattern.substring(1); // Remove '?'.
            }

            // If after pattern has been changed in above code, and length is now 1, and the remaining char is either ? or *.
            // The reason this only works after the pattern is changed and not before the letter is added to the prefix
            // is because the function wouldn't be called again for words of one letter.
            if (pattern.length() == 1 && (pattern.charAt(0) == '?' || pattern.charAt(0) == '*')) {
                // If is valid word, add to matches.
                if (containsWord(prefix)) {
                    matches.add(prefix);
                }
            }

            // Continue recursion through all child nodes.
            for (LexiconNode child : ln) {
                regexHelper(pattern, prefix, matches, child);
            }
    }

    /**
     * Gets number of children of a given LexiconNode.
     * @param parent LexiconNode to find number of children.
     * @return Number of children.
     */
    public int numChildren (LexiconNode parent){
        return parent.getNumChildren();
    }
}

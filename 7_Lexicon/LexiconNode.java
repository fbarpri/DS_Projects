import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Fabiola, 905560 <br>
 * This LexiconNode class represents a node of a LexiconTrie. Each node contains a letter,
 * whether or not the letter is the end of a word, and its parent LexiconNode. Each LexiconNode
 * also has an ArrayList of the LexiconNode's children.
 */
public class LexiconNode implements Iterable<LexiconNode> {
    /**
     * Character representing the letter of the alphabet corresponding to the LexiconNode.
     */
    private char letter;
    /**
     * An ArrayList representing all the LexiconNode children of the given LexiconNode.
     * There can be any number of children from 0 to 26 (all possible letters of the alphabet).
     */
    private List<LexiconNode> children = new ArrayList<>();
    /**
     * Boolean shows if a LexiconNode represents the end of a word at that node.
     */
    private boolean isWord;
    /**
     * LexiconNode that is the parent of given LexiconNode object. A parent can either be a LexiconNode
     * representing a letter that goes before another given LexiconNode letter in a word or a root
     * LexiconNode containing only a space character, showing that its children are the first letters
     * of a word.
     */
    private LexiconNode parent;

    public LexiconNode (char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }


    public int getNumChildren() {
        return children.size();
    }

    public boolean isWord() {
        return isWord;
    }
    public boolean hasChildren () {
        return !children.isEmpty();
    }

    public LexiconNode getParent() {
        return parent;
    }

    /**
     * Adds child to list of children, maintaining the list ordered alphabetically.
     * Loops through children until a position is found. If given LexiconNode letter is already in
     * children it is not added. After being added to children list, it connects to its parent, which
     * is the current instance of LexiconNode.
     * @param newChild New LexiconNode to add to children of current LexiconNode instance.
     */
    public void addChild (LexiconNode newChild) {
        // Loops through all current children.
        for (int i = 0; i < children.size(); i++) {
            // If newChild's letter should go before current iteration's letter, add here:
            if (newChild.letter < children.get(i).letter) {
                newChild.parent = this; // Connect current object instance as parent.
                children.add(i, newChild); // Adds at index. Current object at index is moved to the right.
                return;
            }
            // If newChild's letter is already in children, it should not be added to avoid duplicates:
            if (newChild.letter == children.get(i).letter) {
                return;
            }
        }
        // If newChild's letter should be added at the end of sorted children list:
        newChild.parent = this;
        children.add(newChild);
    }

    /**
     * Gets the LexiconNode child object associated with a given letter character.
     * Achieves this by looping through all the children and comparing letter attributes
     * until a match is found.
     * @param childLetter Letter character to find corresponding LexiconNode of.
     * @return LexiconNode object corresponding to childLetter.
     */
    public LexiconNode getChild (char childLetter) {
        // Loops through all LexiconNode children:
        for (LexiconNode lexiconNode: children) {
            // If letter match is found, return match:
            if (lexiconNode.letter == childLetter) {
                return lexiconNode;
            }
        }
        return null; // No match is found.
    }

    /**
     * Removes a given LexiconNode from list of children. If there is no such LexiconNode in
     * list of children, returns false.
     * @param nodeToRemove LexiconNode to be removed.
     * @return If the removal was successful, returns false if LexiconNode to remove was not in children.
     */
    public boolean removeChild (LexiconNode nodeToRemove) {
        // If node to remove doesn't exist, return false.
        if (getChild(nodeToRemove.letter) == null) return false;
        // Else, remove and return true.
        children.remove(nodeToRemove);
        return true;
    }

    public void makeIsWord () {
        isWord = true;
    }

    public void removeIsWord () {
        isWord = false;
    }

    @Override
    public Iterator<LexiconNode> iterator() {
        return children.iterator();
    }

    public String toString() {
        String result = String.valueOf(letter);
        result += ", children: [";
        if (!children.isEmpty()) {
            for (LexiconNode child : children) {
                result += (child.getLetter()) +", ";
            }
            result += "]";
        }
        return result;
    }
}

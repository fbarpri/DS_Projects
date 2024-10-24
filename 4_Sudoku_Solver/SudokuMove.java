/**
 * Fabiola, 905560 <br>
 * SudokuMove class represents a move made while solving a Sudoku Puzzle in SudokuSolver.
 * It has the attributes of row, column and digit of a given move.
 */
public class SudokuMove {
    /**
     * Row of move.
     */
    private int row;
    /**
     * Column of move.
     */
    private int column;
    /**
     * Digit to be placed in cell by move.
     */
    private int digit;

    public SudokuMove (int row, int column, int digit) {
        this.row = row;
        this.column = column;
        this.digit = digit;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getDigit() {
        return digit;
    }
}

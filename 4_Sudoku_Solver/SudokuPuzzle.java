import java.io.File;
import java.util.Scanner;

/**
 * Fabiola, 905560. <br>
 * SudokuPuzzle class creates a configuration representing a Sudoku board.
 * This board can represent a starting board, intermediate or a completed game.
 * It saves the digit of each cell by storing it in a 2D array of [row] [column]. <br>
 * Aside from creating the Sudoku board, it uses equals() to compare two Sudoku Puzzles
 * and if the solved Sudoku is correct. It also has helper methods that allow SudokuSolver
 * to solve a given Sudoku board such as isMoveValid() which checks digits in other cells to
 * see if given move is possible, replaceDigit() which places a digit in a cell, isEmpty()
 * which sees if a cell is empty (= 0), and makeEmpty() which reverts a cell back to 0.
 */
public class SudokuPuzzle {
    /**
     * 2D integer Array that stores the digits of each cell using [row] [column].
     * Makes up basic configuration of all Sudoku boards.
     */
    private int[][] puzzle = new int [9] [9];

    public SudokuPuzzle (String filename){
        // Scans Sudoku puzzle from file:
        Scanner scan;
        try {
            scan = new Scanner(new File(filename));
        } catch (Exception e) {
            System.out.println ("Error reading file!");
            return;
        }

        // Loops through integers in file and adds them to puzzle Array:
        while (scan.hasNext()) {
            for (int row = 0; row < puzzle.length; row++) {
                for (int column = 0; column < puzzle[row].length; column++) {
                    int i = scan.nextInt();
                    puzzle [row] [column] = i;
                }
            }
        }
        scan.close();
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 9; row++) {
            // Adds divider in between box rows:
            if (row % 3 == 0) {
                sb.append("+---------+---------+---------+\n");
            }

            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0) { // For inter-box column divisions
                    sb.append("|");
                }

                sb.append(" ");
                if (puzzle[row][column] == 0) {
                    sb.append("_");
                } else sb.append(puzzle[row][column]);
                sb.append(" ");

                if (column == 8) {
                    sb.append("|"); // Adds division after digit
                }
            }
            sb.append("\n");
        }

        sb.append("+---------+---------+---------+\n");
        return sb.toString();
    }


    /**
     * Check if two Sudoku Puzzles are equal. Is used for comparing program-generated solutions
     * with provided solutions. First type casts provided Object to SudokuPuzzle if is a
     * SudokuPuzzle object to acces the attributes of the given SudokuPuzzle. It then loops
     * through its elements to ensure each digit is identical.
     * @param obj SudokuPuzzle object to compare.
     * @return Whether both Sudoku Puzzles are identical.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SudokuPuzzle) {
            SudokuPuzzle solution = (SudokuPuzzle) obj;
            // Loops through rows, columns of Sudoku Puzzles to compare each individual digit:
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    if (this.puzzle [row][column] != solution.puzzle [row][column]) {
                        System.out.println("Oh no! Solution is incorrect D:");
                        return false;
                    }
                }
            }
            System.out.println("Yay! Solution is correct :D");
            return true;

        }
        return false;
    }

    /**
     * Checks if a given move is valid by making sure the digit of the move
     * does not appear in the cell's column, row, or box.
     * @param sudokuMove Move to see if valid.
     * @return Whether it is a valid move.
     */
    public boolean isMoveValid (SudokuMove sudokuMove) {
        int moveRow = sudokuMove.getRow();
        int moveColumn = sudokuMove.getColumn();
        int moveDigit = sudokuMove.getDigit();

        // Loops through both row and column of move's cell.
        for (int i = 0; i < 9; i++) {
            if (this.puzzle [i] [moveColumn] == moveDigit || this.puzzle [moveRow] [i] == moveDigit) {
                return false;
            }
        }

       return checkBox(moveRow, moveColumn, moveDigit);
    }

    /**
     * Checks the box of a given cell to ensure the digit of an intentended move doesn't
     * reappear within the box. Used by isMoveValid() to check box.
     * @param moveRow Row of move.
     * @param moveColumn Column of move.
     * @param moveDigit Digit of move.
     * @return If move is valid within cell's box.
     */
    public boolean checkBox (int moveRow, int moveColumn, int moveDigit) {
        int startRow = (moveRow/3) * 3;
        int startColumn = (moveColumn/3) * 3;

        // Loops through box of move:
        for (int row = startRow; row < startRow + 3; row++) {
            for (int column = startColumn; column < startColumn + 3; column++) {
                if (this.puzzle [row] [column] == moveDigit) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Chcks if a given cell is empty by seeing if its digit = 0.
     * Used by SudokuSolver to identify empty cells.
     * @param row Row to check.
     * @param column Column to check.
     * @return Whether it is empty.
     */
    public boolean isEmpty (int row, int column) {
        return puzzle[row][column] == 0;
    }

    /**
     * Makes a given Sudoku cell empty by reverting its value to 0.
     * @param row Row of cell.
     * @param column Column of cell.
     */
    public void makeEmpty (int row, int column) {
        puzzle [row] [column] = 0;
    }

    /**
     * Replaces the digit currently in a cell with the one given
     * by a SudokuMove.
     * @param sudokuMove Gives column, row and new digit for cell.
     */
    public void replaceDigit (SudokuMove sudokuMove) {
        int row = sudokuMove.getRow();
        int column = sudokuMove.getColumn();
        int digit = sudokuMove.getDigit();
        puzzle [row] [column] = digit;
    }

}

import java.util.ArrayDeque;

/**
 * Fabiola, 905560 <br>
 * SudokuSolver class takes in a SudokuSolver attribute and solves it.
 * It has an ArrayDeque that stores the moves it makes while solving the sudoku.
 * Its main method, solve() solves a sudoku using getEmptyCell() to loop through
 * SudokuPuzzle, getMove() to add an initial digit and finally, if it has no more valid
 * moves for a cell, backtrack() and changeMove() to go back until an alternative move
 * is found and change the digit of previous cell.
 */
public class SudokuSolver {
    /**
     * Sudoku Puzzle to solve.
     */
    private SudokuPuzzle sudokuPuzzle;
    /**
     * ArrayDeque for storing moves made while solving sudoku.
     */
    private ArrayDeque<SudokuMove> moves = new ArrayDeque<>();

    public SudokuSolver (SudokuPuzzle sudokuPuzzle) {
        this.sudokuPuzzle = sudokuPuzzle;
    }

    /**
     * Solves a given Sudoku Puzzle. Calls on getEmptyCell() to get the location
     * of the next empty cell in a Sudoku. While there are empty cells to solve
     * inputs a digit using getMove() and replaceDigit() (from SudokuPuzzle). If
     * it encounters a cell where no legal moves are possible, it backtracks ()
     * until a cell is found with an alternative possible move. Whenever it makes
     * a move, it adds it to moves ArrayDeque.
     */
    public void solve () {
        while (getEmptyCell()[0] != -1) {
            int row = getEmptyCell()[0];
            int column = getEmptyCell()[1];
            SudokuMove move = getMove(row, column);
            if (move != null) {
                moves.addFirst(move);
                sudokuPuzzle.replaceDigit(move);
            } else backtrack(getEmptyCell());
        }
    }

    /**
     * Called when a cell is encountered with no more legal moves.
     * It goes back until it finds a cell with an alternative move,
     * making all cells it passes through empty. Uses moves ArrayDeque
     * to get previous moves and remove (undo) them if necessary.
     * Once it finds a valid alternative move, it uses replaceDigit()
     * from SudokuPuzzle to change it and adds move to moves ArrayDeque.
     * @param nullPosition Cell position [row, column] where no more moves were possible.
     */
    public void backtrack (int[] nullPosition) {
        int row = nullPosition[0];
        int column = nullPosition[1];
        SudokuMove move = getMove(row, column);
        // Goes back until valid alternative move is found:
        while (move == null) {
            sudokuPuzzle.makeEmpty(row, column);
            SudokuMove prevMove = moves.peekFirst();
            moves.removeFirst();
            row = prevMove.getRow();
            column = prevMove.getColumn();
            move = changeMove(prevMove);
        }
        sudokuPuzzle.replaceDigit(move);
        moves.addFirst(move);
    }

    /**
     * Loops through Sudoku Puzzle to find and return the position of empty cells.
     * If Sudoku is solved, the first element in array is set to -1.
     * @return Position of an empty cell as an integer array [row, column].
     * Row = -1 if no more empty cells are found (sudoku is solved).
     */
    public int [] getEmptyCell () {
        int[] rowColumn = new int[2];
        // Loops through rows and columns of Sudoku Puzzle:
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (sudokuPuzzle.isEmpty(row, column)) {
                    rowColumn[0] = row;
                    rowColumn[1] = column;
                    return rowColumn;
                }
            }
        }
        rowColumn[0] = -1;
        return rowColumn;
    }

    /**
     * Loops through possible numbers [1,9] to find and return a valid move for a cell.
     * Calls on isMoveValid() from SudokuPuzzle to check legality of move.
     * If no legal move is found, returns null.
     * @param row Row of cell.
     * @param column Column of cell.
     * @return Valid move if found. Otherwise, returns null.
     */
    private SudokuMove getMove(int row, int column) {
        // Loops through possible numbers from smallest to largest:
        for (int i = 1; i <= 9; i++) {
            SudokuMove sudokuMove = new SudokuMove(row, column, i);
            if (sudokuPuzzle.isMoveValid(sudokuMove)) return sudokuMove;
        }
        return null;
    }

    /**
     * Changes a move if previous one was incorrect. Chooses next possible digit
     * (tries digits from smallest to largest). If no more possible moves are left, returns null.
     * @param previousMove Move to be changed.
     * @return New move or null if none found.
     */
    private SudokuMove changeMove (SudokuMove previousMove) {
        // Loops through possible digits starting from one already tried in previous move:
        for (int i = previousMove.getDigit() + 1; i <= 9; i++) {
            SudokuMove sudokuMove = new SudokuMove(previousMove.getRow(), previousMove.getColumn(), i);
            if (sudokuPuzzle.isMoveValid(sudokuMove)) return sudokuMove;
        }
        return null;
    }
}



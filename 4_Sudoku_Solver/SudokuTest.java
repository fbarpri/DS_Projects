import java.util.Scanner;

/**
 * Fabiola, 905560 <br>
 * Reads a user's file containing a Sudoku puzzle and makes SudokuPuzzle object
 * and SudokuSolver to solve the puzzle. Also asks user for an optional
 * solution file from user to compare generated solved sudoku to.
 */
public class SudokuTest {
    public static void main (String[]args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter filename of puzzle: ");
        String puzzleFile = scanner.nextLine();
        System.out.print("Enter filename of solution (optional): ");
        String solutionFile = scanner.nextLine();
        scanner.close();

        SudokuPuzzle puzzle = new SudokuPuzzle(puzzleFile);
        System.out.println("\nStarting puzzle:");
        System.out.println(puzzle);

        System.out.println("Solved puzzle: ");
        SudokuSolver sudokuSolver = new SudokuSolver(puzzle);
        sudokuSolver.solve();
        System.out.println(puzzle);

        if (!solutionFile.isEmpty()) {
            SudokuPuzzle solution = new SudokuPuzzle(solutionFile);
            puzzle.equals(solution);
        }
    }
}

import java.util.Random;

/**
 * Fabiola, ID: 905560
 * Class used to represent game state. Creates Coin Strip for Silver Dollar game.
 * Has gameOver attribute for when game is finished, coins for total number of coins on Strip,
 * current player's turn, and a coinPositions array to indicate current coin positions on Strip.
 * Coins can be moved to the left within the strip spaces as long as no other coin is in the way.
 * Class also checks if said movement is legal given the dimensions and rules of the game.
 */
public class CoinStrip {
    /** Whether someone has won the game yet or not. */
    private boolean gameOver;
    /** Number of coins on CoinStrip.*/
    private int coins;
    /** Current positions on CoinStrip of each one of the coins. Ordered from left to right. */
    private int[] coinPositions;
    /** Current player's turn in game. Can be either 1 or 2.*/
    private int player;

    public boolean isGameOver() {
        return gameOver;
    }

    public int getPlayer() {
        return player;
    }

    public CoinStrip (int coins) {
        this.coins = coins;
        player = 1;
        coinPositions = new int[coins];
        Random random = new Random();

        for (int i = 0; i < coins; i++) {
            int randSpaces = random.nextInt(6);
            if (i == 0) {
                coinPositions[i] = randSpaces;
            } else {
                coinPositions[i] = randSpaces + coinPositions[i-1] + 1;
                // +1 since random starts at 0, avoid same location for different coin
            }
        }
    }

    public String toString() {
        String board = "";

        for (int i = 0; i <= coinPositions[coins-1]; i++) {
            if (i == 0) {
                board += "+---+";
            } else {
                board += "---+";
            }
        }

        for (int i = 0; i < coins; i++) {
            if (i == 0) {
                board += "\n|";
                board += "   |".repeat(coinPositions[i]);
                board += String.format(" %d |", i);
            } else {
                board += "   |".repeat(coinPositions[i] - coinPositions[i-1] - 1);
                board += String.format(" %d |", i);
            }
        }

        for (int i = 0; i <= coinPositions[coins-1]; i++) {
            if (i == 0) {
                board += "\n+---+";
            } else {
                board += "---+";
            }
        }

        return board;
    }

    /** Checks if movement is legal. Only legal if: coin # exists in game, and
     * there is no other coin blocking the way with the given number of spaces
     * the coin has to move to the left.
     * */
    public boolean isLegal (int coin, int spaces) {
        if (coin>coins-1) {
            return false;
        }

        int availableSpaces;
        if (coin != 0) {
            availableSpaces = coinPositions[coin] - coinPositions[coin-1] - 1;
            // -1 so not overlapping previous coin
        } else {
            availableSpaces = coinPositions[coin];
        }

        if (spaces <= availableSpaces) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Allows selected coin to move a given number of spaces to the left.
     * @param coin - coin to be moved.
     * @param spaces - number of spaces to move to the left.
     * @return - CoinStrip board showing updated coin locations.
     */
    public String move (int coin, int spaces) {
        coinPositions[coin] -= spaces;
        if (coinPositions[coins-1] == coins-1) {
            gameOver = true;
        }
        return toString();
    }

    /**
     * Switches players (1 or 2) after each game turn.
     */
    public void changePlayer () {
        if (player == 1) {
            player = 2;
        } else {
            player = 1;
        }
    }
}

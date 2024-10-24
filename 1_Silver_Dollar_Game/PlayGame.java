import java.util.Scanner;
/**
 * Fabiola, ID: 905560
 * Runner class for Silver Dollar game. Running the code allows two users to
 * play by selecting a desired number of coins to randomly place on Coin Strip.
 * Each player will alternate turns by picking a coin and moving it "x" places to the left.
 * Whoever places the last coin of "n" coins and makes it so all "n" leftmost spaces are full, wins.
 */

public class PlayGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of coins: ");
        int coins = scanner.nextInt();
        CoinStrip game = new CoinStrip(coins);
        System.out.printf("This is your CoinStrip:\n%s\n", game.toString());

        while (!game.isGameOver()) {
            System.out.printf("Player %d, enter coin and spaces to move: ", game.getPlayer());
            int coin = scanner.nextInt();
            int spaces = scanner.nextInt();

            if (game.isLegal(coin, spaces)) {
                System.out.println(game.move(coin, spaces));
                if (game.isGameOver()) {
                    break; // end loop to maintain current value of player (for winner)
                }
            } else {
                System.out.println("Sorry! Illegal move. Try again.");
                continue; // skip to next loop iteration
            }
            game.changePlayer();
        }

        System.out.printf("Congratulations! Player %d wins!", game.getPlayer());
    }
}

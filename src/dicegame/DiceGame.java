
package dicegame;


import java.util.Random;
import java.util.Scanner;

public class DiceGame {
    private static final int ROUNDS = 11;       // Number of rounds
    private static final int COLUMNS = 11;      // Number of columns in table
    private static final int MIN_COLUMN = 2;    // Starting value in the table

    private int[][] scoreTable;                 // Track each player's scores
    private int[] finalScores;                  // Array to store final scores

    public DiceGame() {
        scoreTable = new int[3][COLUMNS];       // 3 players
        finalScores = new int[3];               // Final scores for 3 players
    }

    public static void main(String[] args) {
        DiceGame game = new DiceGame();         
        game.playGame();                        // Start the game
    }

    private void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Dice Game!");

        // Game loop for each round
        for (int round = 1; round <= ROUNDS; round++) {
            System.out.printf("\n--- Round %d ---\n", round);

            // Each player takes turns every round
            for (int player = 0; player < 3; player++) {
                int rollTotal = rollDice();      // Roll dice for the current player
                displayScoreTable();             // Display current scores
                System.out.printf("Player %d rolled a total of %d. Choose a column (2-12) to place your score: ", player + 1, rollTotal);

                // Check if column chosen is valid
                int column = scanner.nextInt() - MIN_COLUMN;
                while (column < 0 || column >= COLUMNS || scoreTable[player][column] != 0) {
                    System.out.print("Invalid or already chosen column. Choose a different column (2-12): ");
                    column = scanner.nextInt() - MIN_COLUMN;
                }

                // Update the score table for the player
                scoreTable[player][column] = rollTotal;
                System.out.printf("Player %d placed %d in column %d.\n", player + 1, rollTotal, column + MIN_COLUMN);
            }
        }

        determineWinners();                     // Determine the winner for each column
        displayFinalResults();                  // Display the final scores and announce the winner
        scanner.close();
    }

    // Method to simulate dice rolling
    private int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1 + rand.nextInt(6) + 1; 
    }

    // Method to display the current score table
    private void displayScoreTable() {
        System.out.print("\nScore Table:\n   ");
        for (int i = MIN_COLUMN; i <= 12; i++) {
            System.out.printf("%4d", i);
        }
        System.out.println();

        for (int player = 0; player < 3; player++) {
            System.out.printf("P%d ", player + 1);
            for (int column = 0; column < COLUMNS; column++) {
                if (scoreTable[player][column] == 0) {
                    System.out.print("    ");
                } else {
                    System.out.printf("%4d", scoreTable[player][column]);
                }
            }
            System.out.println();
        }
    }

    // Method to determine the winner for each column and update final scores
    private void determineWinners() {
        for (int column = 0; column < COLUMNS; column++) {
            int maxScore = 0;
            int winner = -1;
            boolean tie = false;

            // Check scores for each player in the current column
            for (int player = 0; player < 3; player++) {
                if (scoreTable[player][column] > maxScore) {
                    maxScore = scoreTable[player][column];
                    winner = player;
                    tie = false;
                } else if (scoreTable[player][column] == maxScore) {
                    tie = true;
                }
            }

            // Update scores if no tie and a winner exists
            if (!tie && winner != -1) {
                finalScores[winner] += column + MIN_COLUMN;
                System.out.printf("Column %d won by Player %d with %d points.\n", column + MIN_COLUMN, winner + 1, column + MIN_COLUMN);
            } else {
                System.out.printf("Column %d is tied. No points awarded.\n", column + MIN_COLUMN);
            }
        }
    }

    // Method to display the final results and declare the winner
    private void displayFinalResults() {
        System.out.println("\nFinal Scores:");
        for (int player = 0; player < 3; player++) {
            System.out.printf("Player %d: %d points\n", player + 1, finalScores[player]);
        }

        int maxScore = Math.max(finalScores[0], Math.max(finalScores[1], finalScores[2]));
        boolean tie = false;
        int winner = -1;

        // Determine if there's a tie or a single winner
        for (int player = 0; player < 3; player++) {
            if (finalScores[player] == maxScore) {
                if (winner == -1) {
                    winner = player;
                } else {
                    tie = true;
                }
            }
        }

        if (tie) {
            System.out.println("The game is a tie!");
        } else {
            System.out.printf("Player %d wins the game!\n", winner + 1);
        }
    }
}

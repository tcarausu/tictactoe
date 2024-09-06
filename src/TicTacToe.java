import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
    private final char[][] board;
    private final int size;
    private static final char EMPTY = '-';
    private static final char PLAYER = 'X';
    private static final char COMPUTER = 'O';
    private final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            // check for valid input
            System.out.println("Choose a game mode: 1. 3x3  2. 4x4  3. Exit");
            choice = scanner.nextInt();

            if (choice == 3) {
                System.out.println("Exiting game.");
                return;
            }

            if (choice != 1 && choice != 2) {
                System.out.println("Invalid Input. Please enter 1 for 3x3 or 2 for 4x4.");
                continue;
            }

            // if the choice is 1 play 3x3, if not play 4x4 (game size)
            int gameSize = (choice == 1) ? 3 : 4;
            TicTacToe game = new TicTacToe(gameSize);
            game.playGame();

            System.out.println("Game over. Returning to main menu.\n\n");
        }
    }

    public TicTacToe(int size) {
        this.size = size;
        board = new char[size][size];
        prepareGameBoard();
    }

    private void prepareGameBoard() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = EMPTY;
    }

    private void printGameBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    private boolean isBoardFilled() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == EMPTY)
                    return false;

        return true;
    }

    private boolean isWinnerStatus(char player) {
        // check rows and columns
        for (int i = 0; i < size; i++) {
            boolean rowWin = true;
            boolean colWin = true;
            for (int j = 0; j < size; j++) {
                //if any cell in the current row does not contain the player, set rowWin to false.
                if (board[i][j] != player) rowWin = false;
                //if any cell in the current column does not contain the player, set colWin to false.
                if (board[j][i] != player) colWin = false;
            }

            if (rowWin || colWin) return true;
        }

        // check diagonals
        boolean diag1Win = true;
        boolean diag2Win = true;
        for (int i = 0; i < size; i++) {
            // For the main diagonal (from the top-left to bottom-right),
            // if any cell does not contain the player, set diag1Win to false.
            if (board[i][i] != player) diag1Win = false;
            //For the anti-diagonal (from the top-right to bottom-left),
            // if any cell does not contain the player, set diag2Win to false.
            if (board[i][size - 1 - i] != player) diag2Win = false;
        }

        return diag1Win || diag2Win;
    }

    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < size) && (col >= 0 && col < size) && board[row][col] == EMPTY;
    }

    private void playerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        while (true) {
            System.out.println("Enter your move (row and column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();

            if (isValidMove(row, col)) {
                board[row][col] = PLAYER;
                break;
            } else {
                System.out.println("This move is not valid. Please try again.");
            }
        }
    }

    private void computerMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == EMPTY) {
                    // check if Computer can win
                    board[i][j] = COMPUTER;
                    if (isWinnerStatus(COMPUTER))
                        return;
                    board[i][j] = EMPTY;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == EMPTY) {
                    // check if you have to block player movement
                    board[i][j] = PLAYER;
                    if (isWinnerStatus(PLAYER)) {
                        board[i][j] = COMPUTER;
                        return;
                    }
                    board[i][j] = EMPTY;
                }
            }
        }

        // create a random move (in case there are no immediate ones), normally first few moves
        int row, col;
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (!isValidMove(row, col));

        board[row][col] = COMPUTER;
    }

    private void playGame() {
        while (true) {
            // Display game board
            printGameBoard();

            // player movement
            playerMove();
            if (isWinnerStatus(PLAYER)) {
                printGameBoard();
                System.out.println("You win!");
                break;
            } else if (isBoardFilled()) {
                printGameBoard();
                System.out.println("Draw!");
                break;
            }

            // computer movement
            computerMove();
            if (isWinnerStatus(COMPUTER)) {
                printGameBoard();
                System.out.println("You Lose!");
                break;
            } else if (isBoardFilled()) {
                printGameBoard();
                System.out.println("Draw!");
                break;
            }
        }
    }
}

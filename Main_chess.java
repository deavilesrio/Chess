import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main_chess {
    public static void main(String[] arg) throws FileNotFoundException {
        // Reads the file
        File file = new File("C:\\Users\\diego\\OneDrive\\Desktop\\CS Lab\\Adv_Obj\\chess.txt");
        Scanner fileReader = new Scanner(file);
        String pieceName;
        String pieceColor;
        int x;
        String y;
        int[][] board = new int[8][8];

        int[] next_move = { 0, 0 };
        System.out.println("Prueba");
        Dictionary<String, Integer> dict = new Hashtable<>();
        dict.put("A", 1);
        dict.put("B", 2);
        dict.put("C", 3);
        dict.put("D", 4);
        dict.put("E", 5);
        dict.put("F", 6);
        dict.put("G", 7);
        dict.put("H", 8);

        Dictionary<Integer, String> reverse_Dict = new Hashtable<>();
        reverse_Dict.put(1, "A");
        reverse_Dict.put(2, "B");
        reverse_Dict.put(3, "C");
        reverse_Dict.put(4, "D");
        reverse_Dict.put(5, "E");
        reverse_Dict.put(6, "F");
        reverse_Dict.put(7, "G");
        reverse_Dict.put(8, "H");
        // Read file to display menu to user
        while (fileReader.hasNext()) {
            pieceName = fileReader.next();
            pieceColor = fileReader.next();
            y = fileReader.next(); // D
            x = fileReader.nextInt(); // 1

            // Assigns each piece their aspects
            Chess_Piece piece = new Chess_Piece(pieceName, pieceColor, x, dict.get(y));
            System.out.println(piece.name + " " + piece.color + " " + piece.x + ", " + piece.y);
            // adds the piece to the board
            board[8 - piece.x][piece.y - 1] = 1;
            // checks if the piece can move

            // Pawn Piece
            if (piece.name.equals("Pawn")) {
                next_move = pawnCheck(piece.color, piece.x, piece.y, board);
                if (next_move[0] == 0 && next_move[1] == 0) {
                    System.out.println("Pawn at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can't move ");
                } else {
                    System.out.println("Pawn at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can move to "
                            + reverse_Dict.get(next_move[1]) + "," + next_move[0]);
                }
            }

            // esta madre ni corre
            // Knight piece
            else if (piece.name.equals("Knight")) {
                next_move = isValidKnightMove(board, 8 - piece.x, piece.y - 1);
                System.out.println("Knight at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can move to "
                        + reverse_Dict.get(next_move[1]) + "," + next_move[0]);

            } else if (piece.name.equals("Bishop")) {
                next_move = isValidBishopMove(board, 8 - piece.x, piece.y - 1);
                System.out.println("Bishop at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can move to "
                        + reverse_Dict.get(next_move[1]) + "," + next_move[0]);

            }

            // prints the board
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }

        }

    }

    public static int[] pawnCheck(String color, int x, int y, int[][] board) {

        int[] next_position = { 0, 0 };
        if (color.equals("Black")) {

            if (7 == x) { // Starting line

                if (board[8 - x + 1][y - 1] == 0) {
                    next_position[0] = x + 1;
                    next_position[1] = y;
                    return next_position;

                } else if (board[8 - x + 2][y - 1] == 0) {
                    next_position[0] = x + 2;
                    next_position[1] = y;
                    return next_position;
                }
            } else { // it isn't at the starting line
                if ((8 - x + 1) == 8) {
                    return next_position;
                } else if (board[8 - x + 1][y - 1] == 0) {
                    next_position[0] = x + 1;
                    next_position[1] = y;
                    return next_position;
                }
            }

        } else if (color.equals("White")) {

            if (2 == x) { // Starting line

                if (board[8 - x - 1][y - 1] == 0) {
                    next_position[0] = x - 1;
                    next_position[1] = y;
                    return next_position;

                } else if (board[8 - x - 2][y - 1] == 0) {
                    next_position[0] = x - 2;
                    next_position[1] = y;
                    return next_position;
                }
            } else {// it isn't at the starting line
                if ((8 - x - 1) == -1) {
                    return next_position;
                } else if (board[8 - x - 1][y - 1] == 0) {
                    next_position[0] = x - 1;
                    next_position[1] = y;
                    return next_position;
                }
            }

        }
        return next_position;
    }

    public static int[] isValidKnightMove(int[][] board, int currentRow, int currentCol) {
        int[][] moves = { { -2, -1 }, { -1, -2 }, { 1, -2 }, { 2, -1 }, { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 } };
        int[] next_position = { 0, 0 };

        for (int[] move : moves) {

            int newRow = currentRow + move[0];
            int newCol = currentCol + move[1];

            if (isValidSquare(newRow, newCol) && board[newRow][newCol] == 0) {
                // Valid move if the new position is within the board and not occupied
                next_position[0] = 8 - newRow;
                next_position[1] = newCol + 1;

                return next_position;
            }
        }

        // Knight cannot move to any valid positions
        return next_position;
    }

    public static int[] isValidBishopMove(int[][] board, int currentRow, int currentCol) {
        int[][] left_dia_moves_up = { { -1, -1 }, { -2, -2 }, { -3, -3 }, { -4, -4 }, { -5, -5 }, { -6, -6 },
                { -7, -7 }, { -8, -8 } };
        int[][] right_dia_moves_down = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 },
                { 8, 8 } };
        int[][] left_dia_moves_down = { { 1, -1 }, { 2, -2 }, { 3, -3 }, { 4, -4 }, { 5, -5 }, { 6, -6 }, { 7, -7 },
                { 8, -8 } };
        int[][] right_dia_moves_up = { { -1, 1 }, { -2, 2 }, { -3, 3 }, { -4, 4 }, { -5, 5 }, { -6, 6 }, { -7, 7 },
                { -8, 8 } };
        int[] next_position = { 0, 0 };
        // Checks each possible diagonal move and returns as soons as it founds one
        for (int[] move : left_dia_moves_up) {

            int newRow = currentRow + move[0];
            int newCol = currentCol + move[1];

            if (isValidSquare(newRow, newCol) && board[newRow][newCol] == 0) {
                // Valid move if the new position is within the board and not occupied
                next_position[0] = 8 - newRow;
                next_position[1] = newCol + 1;

                return next_position;
            } else {
                break;
            }
        }
        for (int[] move : right_dia_moves_down) {

            int newRow = currentRow + move[0];
            int newCol = currentCol + move[1];

            if (isValidSquare(newRow, newCol) && board[newRow][newCol] == 0) {
                // Valid move if the new position is within the board and not occupied
                next_position[0] = 8 - newRow;
                next_position[1] = newCol + 1;

                return next_position;
            } else {
                break;
            }
        }
        for (int[] move : left_dia_moves_down) {

            int newRow = currentRow + move[0];
            int newCol = currentCol + move[1];

            if (isValidSquare(newRow, newCol) && board[newRow][newCol] == 0) {
                // Valid move if the new position is within the board and not occupied
                next_position[0] = 8 - newRow;
                next_position[1] = newCol + 1;

                return next_position;
            } else {
                break;
            }
        }
        for (int[] move : right_dia_moves_up) {

            int newRow = currentRow + move[0];
            int newCol = currentCol + move[1];

            if (isValidSquare(newRow, newCol) && board[newRow][newCol] == 0) {
                // Valid move if the new position is within the board and not occupied
                next_position[0] = 8 - newRow;
                next_position[1] = newCol + 1;

                return next_position;
            } else {
                break;
            }
        }

        // Knight cannot move to any valid positions
        return next_position;
    }

    // Function to check if the square is valid (within the chessboard)
    public static boolean isValidSquare(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main_chess {
    public static void main(String[] arg) throws FileNotFoundException {
        Scanner myObj = new Scanner(System.in);
        // Reads the file
        File file = new File("C:\\Users\\diego\\OneDrive\\Desktop\\CS Lab\\Adv_Obj\\chess.txt");
        Scanner fileReader = new Scanner(file);
        
        String temp_position;
        String pieceName;
        String pieceColor;
        String x;
        String y;
        int[][] board = new int[8][8];
        int num;
        boolean next_move = false;
        int next_x = 0;
        int next_y = 0;
        //User input
        System.out.print("Enter postion: \n> "); 
        temp_position = myObj.nextLine();  
        String [] arrOfnextMove = temp_position.split(", ", 2);

        next_x = Integer.parseInt(arrOfnextMove[1]);
        String number = arrOfnextMove[0];
        
        
        Dictionary<String, Integer> dict = new Hashtable<>();
        dict.put("a", 1);
        dict.put("b", 2);
        dict.put("c", 3);
        dict.put("d", 4);
        dict.put("e", 5);
        dict.put("f", 6);
        dict.put("g", 7);
        dict.put("h", 8);
        
        next_y = dict.get(number);

        Dictionary<Integer, String> reverse_Dict = new Hashtable<>();
        reverse_Dict.put(1, "a");
        reverse_Dict.put(2, "b");
        reverse_Dict.put(3, "c");
        reverse_Dict.put(4, "d");
        reverse_Dict.put(5, "e");
        reverse_Dict.put(6, "f");
        reverse_Dict.put(7, "g");
        reverse_Dict.put(8, "h");
        
        
        
        // Read file to display menu to user
        while (fileReader.hasNextLine()) {
            
            String line = fileReader.nextLine();
            String [] arrOfStr = line.split(", ", 4);
            
            pieceName = arrOfStr[0];
            pieceColor = arrOfStr[1];
            y = arrOfStr[2]; // D
            x = arrOfStr[3]; // 1
            num = Integer.parseInt(x);
            
            // Assigns each piece their aspects
            Chess_Piece piece = new Chess_Piece(pieceName, pieceColor, num, dict.get(y));
            // System.out.println(piece.name + " " + piece.color + " " + piece.x + ", " + piece.y); // Prints the instructions
         
         
    
            if(isValidSquare(8 - piece.x, piece.y - 1) && board[8 - piece.x][piece.y - 1] == 0 && isValidSquare(8 - next_x,next_y - 1)  ){
                // adds the piece to the board
                board[8 - piece.x][piece.y - 1] = 1;

                // checks if the piece can move

                // Pawn Piece
                if (piece.name.equals("Pawn")) {
                    next_move = pawnCheck(piece.color, piece.x, piece.y, board, next_x, next_y);
                    if(next_move == true){
                        System.out.println(piece.name + " at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can move to " + reverse_Dict.get(next_y) + "," + next_x + ".");
                    }else{
                        System.out.println(piece.name + " at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can't move to " + reverse_Dict.get(next_y) + "," + next_x + ".");
                    }

                    
                
                }
                if (piece.name.equals("Knight")) {
                    next_move = isValidKnightMove(board, piece.x, piece.y, next_x, next_y);
                    if(next_move == true){
                        System.out.println(piece.name + " at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can move to " + reverse_Dict.get(next_y) + "," + next_x + ".");
                    }else{
                        System.out.println(piece.name + " at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can't move to " + reverse_Dict.get(next_y) + "," + next_x + ".");
                    }

                    
                
                }
                if (piece.name.equals("Bishop")) {
                    next_move = isValidBishopMove(board, 8 - piece.x, piece.y - 1, 8 - next_x, next_y-1);
                    if(next_move == true){
                        System.out.println(piece.name + " at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can move to " + reverse_Dict.get(next_y) + "," + next_x + ".");
                    }else{
                        System.out.println(piece.name + " at " + reverse_Dict.get(piece.y) + ", " + piece.x + " can't move to " + reverse_Dict.get(next_y) + "," + next_x + ".");
                    }

                    
                
                }

                
            
            }else{
               System.out.println("Piece location is not valid"); 
            }
        }

            

    }

    public static boolean pawnCheck(String color, int current_x, int current_y, int[][] board, int next_x,  int next_y) {

        
        if (color.equals("black")) {

            if (7 == current_x) { // Starting line

                if ((next_x == current_x - 1 && next_y == current_y) || (next_x == current_x - 2 && next_y == current_y) )  {
                    return true;
                }
            } else { // it isn't at the starting line
                if (next_x == current_x - 1 && next_y == current_y){
                    return true;
                }
            }

        } else if (color.equals("white")) {

            if (2 == current_x) { // Starting line

                if ((next_x == current_x + 1 && next_y == current_y) || (next_x == current_x + 2 && next_y == current_y) )  {
                    return true;
                }
            } else { // it isn't at the starting line
                if (next_x == current_x + 1 && next_y == current_y){
                    return true;
                }
            }

        }
        return false;
    }

    public static boolean isValidKnightMove(int[][] board, int currentRow, int currentCol, int next_x, int next_y) {
        int[][] moves = { { -2, -1 }, { -1, -2 }, { 1, -2 }, { 2, -1 }, { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 } };
        

        for (int[] move : moves) {

            int newRow = currentRow + move[0];
            int newCol = currentCol + move[1];

            if (isValidSquare(newRow, newCol) && (newRow == next_x && newCol == next_y) ) {
                // Valid move if the new position is within the board and not occupied
    
                return true;
            }
        }

        // Knight cannot move to any valid positions
        return false;
    }

    public static boolean isValidBishopMove(int[][] board, int currentRow, int currentCol, int next_x, int next_y) {
        int[][] left_dia_moves_up = { { -1, -1 }, { -2, -2 }, { -3, -3 }, { -4, -4 }, { -5, -5 }, { -6, -6 },
                { -7, -7 }, { -8, -8 } , { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 },
                { 8, 8 }, { 1, -1 }, { 2, -2 }, { 3, -3 }, { 4, -4 }, { 5, -5 }, { 6, -6 }, { 7, -7 },
                { 8, -8 }, { -1, 1 }, { -2, 2 }, { -3, 3 }, { -4, 4 }, { -5, 5 }, { -6, 6 }, { -7, 7 },
                { -8, 8 } };
            
        // Checks each possible diagonal move and returns as soons as it founds one
        for (int[] move : left_dia_moves_up) {

            int newRow = currentRow + move[0];
            int newCol = currentCol + move[1];

            if (isValidSquare(newRow, newCol) && (newRow == next_x && newCol == next_y) ) {
                // Valid move if the new position is within the board and not occupied
                return true;

            }
        }
        
        // Knight cannot move to any valid positions
        return false;
    }

    // Function to check if the square is valid (within the chessboard)
    public static boolean isValidSquare(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public static void printBoard(int [][] board){
        // prints the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
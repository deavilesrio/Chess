import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Chess{

    public static void main(String[] args) throws IOException{ 
        List<String> listOfChessPieces = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader("Pawn.txt"));

        String line = bf.readLine();

        while(line != null){
            listOfChessPieces.add(line);
            line = bf.readLine();
        }
       bf.close();

       String[] array = listOfChessPieces.toArray(new String [0]);

      
       System.out.println(array[0]);
    }
 
 }
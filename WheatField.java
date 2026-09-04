package wheatfield;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Gian Lardizabal
 */
public class WheatField {

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Random random = new Random();

        int numRows;
        int numCols;
        Sector[][] field;
        int currentRow;
        int currentCol;
        int totalGathered;
        int numGatherings;
        String command;
        boolean playing;

        numRows = readValidSize(keyboard, "rows");
        numCols = readValidSize(keyboard, "columns");

        field = new Sector[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                field[r][c] = new Sector(random.nextInt(1000));
            }
        }

        currentRow = numRows / 2;
        currentCol = numCols / 2;

        totalGathered = 0;
        numGatherings = 0;

        System.out.println();
        displayMap(field, currentRow, currentCol);

        playing = true;
        while (playing) {
            System.out.println();
            System.out.println("Currently in(" + currentRow + "," + currentCol + ")");
            System.out.println("Total gathered so far " + String.format("%,d", totalGathered) + " bushels of wheat grains");
            System.out.println();
            System.out.println("{ navigation N, S, E, W} { actions M, G, J, V, X }");
            System.out.print("Enter command:");
            command = keyboard.next().trim().toUpperCase();

            switch (command) {
                case "N":
                    if (currentRow > 0) {
                        currentRow--;
                    }
                    break;
                case "S":
                    if(currentRow < numRows - 1){
                        currentRow++;
                    }
                    break;
                case "E":
                case "D":
                    if(currentCol < numCols - 1){
                        currentCol++;
                    }
                    break;
                case "W":
                case "A":
                    if(currentCol > 0){
                        currentCol--;
                    }
                    break;
                case "G":
                    int amount = field[currentRow][currentCol].gather();
                    totalGathered = totalGathered + amount;
                    numGatherings++;
                    System.out.println();
                    System.out.println("Just Gathered " + amount + " bushels of wheat grains at (" + currentRow + "," + currentCol + ")");
                    break;
                case "J":
                    currentRow = random.nextInt(numRows);
                    currentCol = random.nextInt(numCols);
                    break;
                case "M":
                    System.out.println();
                    displayMap(field, currentRow, currentCol);
                    break;
                case "V":
                    int vacuumTotal = doVacuum(field, currentRow, currentCol, numRows, numCols);
                    totalGathered = totalGathered + vacuumTotal;
                    numGatherings++;
                    break;
                case "X":
                case "Q":
                    playing = false;
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid command, please try again. ");
                    break;
            }

        }
        
        System.out.println();
        System.out.println("Total Gathered " + String.format("%,d", totalGathered) + " bushels of wheat grains");
        System.out.println("Number of gatherings: " + numGatherings);
        double average = numGatherings == 0 ? 0.0 : (double) totalGathered / numGatherings;
        System.out.printf("Average # of bushels per gathering %.1f%n", average);
 
        keyboard.close();
    }
    
    private static int readValidSize(Scanner keyboard, String dimension){
        int value = -1;
        boolean valid = false;
        
        while(!valid){
            System.out.println("Enter the number of " + dimension + ": (3-12): ");
            if(keyboard.hasNextInt()){
                value = keyboard.nextInt();
                if(value >= 3 && value <= 12){
                    valid = true;
                } else {
                    System.out.println("Please enter a value between 3 and 12");
                }   
            } else {
                keyboard.next();
                System.out.println("Please enter a valid integer");
            }
        }
        
        return value;
    }
    
    private static void displayMap(Sector[][] field, int currentRow, int currentCol){
        int numRows = field.length;
        int numCols = field[0].length;
        
        System.out.println("Wheat Field");
        
        System.out.println("  ");
        for(int c = 0; c < numCols; c++){
            System.out.printf("%5d", c);
        }
        System.out.println();
        
        System.out.println("  ");
        for(int c = 0; c < numCols; c++){
            System.out.printf("%5s", "---");
        }
        System.out.println();
        
        for(int r = 0; r < numRows; r++){
            System.out.printf("%2d", r);
            for(int c = 0; c < numCols; c++){
                int bushels = field[r][c].getNumBushelsOfWheatGrains();
                if(r==currentRow && c == currentCol){
                    System.out.printf("%5s","[" + bushels + "]");
                } else {
                    System.out.printf("%5d", bushels);
                }
            }
            System.out.println();
        }
    }
    
    private static int doVacuum(Sector[][] field, int currentRow, int currentCol, int numRows, int numCols)
    {
        int vacuumTotal = 0;
 
        System.out.println();
        System.out.println("nature abhors a vacuum");
        System.out.println();
 
        int amount = field[currentRow][currentCol].gather(0.9);
        vacuumTotal = vacuumTotal + amount;
        System.out.println("Just Gathered " + amount + " bushels of wheat grains at("
                + currentRow + "," + currentCol + ") - this spot");
        System.out.println();
 
        if (currentRow > 0)
        {
            amount = field[currentRow - 1][currentCol].gather(0.2);
            vacuumTotal = vacuumTotal + amount;
            System.out.println("Just Gathered " + amount + " bushels of wheat grains at("
                    + (currentRow - 1) + "," + currentCol + ") - north");
            System.out.println();
        }
 
        if (currentRow < numRows - 1)
        {
            amount = field[currentRow + 1][currentCol].gather(0.2);
            vacuumTotal = vacuumTotal + amount;
            System.out.println("Just Gathered " + amount + " bushels of wheat grains at("
                    + (currentRow + 1) + "," + currentCol + ") - south");
            System.out.println();
        }
 
        if (currentCol > 0)
        {
            amount = field[currentRow][currentCol - 1].gather(0.2);
            vacuumTotal = vacuumTotal + amount;
            System.out.println("Just Gathered " + amount + " bushels of wheat grains at("
                    + currentRow + "," + (currentCol - 1) + ") - west");
            System.out.println();
        }
 
        if (currentCol < numCols - 1)
        {
            amount = field[currentRow][currentCol + 1].gather(0.2);
            vacuumTotal = vacuumTotal + amount;
            System.out.println("Just Gathered " + amount + " bushels of wheat grains at("
                    + currentRow + "," + (currentCol + 1) + ") - east");
            System.out.println();
        }
 
        System.out.println("the total collected at this spot and the surrounding spots was " + vacuumTotal);
 
        return vacuumTotal;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mincoinsproblem;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * This program is an implementation for one of the popular algorithms for Money Denomination problem
 * The problem is that you need to find the minimum number of coins that makes a given value
 * There are many algorithms for solving this problem. In this solution I have followed the Dynamic Programming Approach
 * @author Mohammed Alghafli
 */
public class MinCoinsProblem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        
        System.out.print("Enter the money amount: ");
        int sum = read.nextInt();
        
        System.out.println("The coins combination is: " + findChange(sum).toString()); // print the result
    }
    
    /**
     * This function will find the smallest number of denominations combination, using Dynamic Programming Approach
     * @param cash the amount of money you have
     * @return 
     */
    public static Hashtable<Integer, Integer> findChange(int cash) {
        int[] denominations = {1, 5, 10, 50, 100}; // These are the money dominations available
        
        int totalCoins = denominations.length;
        // Creating array which stores subproblems' solutions
        double[][] result = new double[totalCoins + 1][cash + 1];

        // Initialising first row with +ve infinity
        for (int j = 0; j <= cash; j++) {
            result[0][j] = Double.POSITIVE_INFINITY;
        }
        // Initialising first column with 0
        for (int i = 1; i <= totalCoins; i++) {
            result[i][0] = 0; // first column should be initialized with zero
        }

        // Implementing the recursive solution
        for (int i = 1; i <= totalCoins; i++) {
            for (int j = 1; j <= cash; j++) {
                if (denominations[i - 1] <= j) {
                    result[i][j] = Math.min(1 + result[i][j - denominations[i - 1]], result[i - 1][j]);
                } else {
                    result[i][j] = result[i - 1][j];
                }
            }
        }

        return extractResult(denominations, result);
    }
    
    /**
     * This method will find the money combination from the array, that is resulted from the algorithm
     * @param denominations is the money denominations available
     * @param resultArray the output of the algorithm
     * @return the dictionary of the number of denominations for each denomination
     */
    public static Hashtable<Integer, Integer> extractResult(int[] denominations, double[][] resultArray){
        Hashtable<Integer, Integer> result = new Hashtable<>();
        // initialize the dectionary with zeros
        for (int i = 0; i < denominations.length; i++) {
            result.put(denominations[i], 0);
        }
        
        int colIndex = resultArray[0].length - 1; // we start from the last column
        int rowIndex = denominations.length;
        while (rowIndex > 0 && colIndex > 0) {
            // System.out.println(rowIndex + " " + colIndex);
            if (resultArray[rowIndex][colIndex] != resultArray[rowIndex - 1][colIndex]) {
                // in this case we have used this coin denomenation
                result.put(denominations[rowIndex - 1], result.get(denominations[rowIndex - 1]) + 1); // increase the previous value of this denomination
                colIndex = colIndex - denominations[rowIndex - 1];
            } else {
                rowIndex--;
            }
        }
        
        return result;
    }
}

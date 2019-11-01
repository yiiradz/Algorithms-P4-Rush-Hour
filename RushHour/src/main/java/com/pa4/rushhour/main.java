/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa4.rushhour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yiradz
 */
public class main {

    static void read_in_file_data(Scanner input) {
        // Read in the number of cars and trucks
        int numOfVehicles = input.nextInt();
        // Every group of 5 is a car or truck so should we loop/5 to take in one at a time or loop by 5 to create a new Vehicle each time?
        
        
        System.out.println(numOfVehicles); // test

    }

    static boolean open_file_with_scanner(String s) {
        File file = new File(s);
        Scanner sc;
        try {
            sc = new Scanner(file);
            read_in_file_data(sc);
            // File opened and read correctly
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            // File not found
            return false;
        }
    }

    public static void main(String[] args) {
        
        boolean gradel_mode = false;
        // If gradel mode, read in from STDIN
        if (gradel_mode) {
            Scanner sc = new Scanner(System.in);
            read_in_file_data(sc);
            // Else read in from a file
        } else {
            String path = "/Users/yiradz/College/JUN_SEM1/GALGORITMS/RushHour/input.txt";
            if (!open_file_with_scanner(path)) {
                System.out.println("Unable to open file.");
            }
        }

    }

}

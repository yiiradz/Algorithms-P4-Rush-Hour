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
 * @author Yayira and Keeton
 */
public class main {
    // Class variables
    private static RushHourGame game;

    static void read_file_data(Scanner input) {
        // Read in the number of cars and trucks
        int total_vehicles = input.nextInt();
        // For each vehicle, read the next 5 lines and create a new Vehicle
        for (int current_vehicle = 0; current_vehicle < total_vehicles; current_vehicle++) {
            // Grab all of the values
            String type = input.next();
            String color = input.next();
            String orientation = input.next();
            int row = input.nextInt() - 1; // Convert to index
            int col = input.nextInt() - 1; // Convert to index
            Vehicle vehicle = new Vehicle(type, color, orientation, row, col);
            vehicle.print();
            // Validate the vehicle, print, and insert
            if(vehicle.validate()) {
                System.out.println("Vehicle is valid!");
                 if (!game.insertVehicle(vehicle)) {
                     System.out.println("Unable to insert vehicle onto the board!");
                }
            }
            System.out.println();
        }
        game.print();
    }

    static void open_file(String s) {
        try {
            Scanner sc = new Scanner(new File(s));
            read_file_data(sc);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        game = new RushHourGame();
        boolean gradel_mode = false;
        // If gradel mode, read in from STDIN
        if (gradel_mode) {
            Scanner sc = new Scanner(System.in);
            read_file_data(sc);
            // Else read in from a file
        } else {
            //String path = "/Users/yiradz/College/JUN_SEM1/GALGORITMS/RushHour/input.txt";
            String path = "/Users/keeton/Documents/Algorithms-P4-Rush-Hour/RushHour/input.txt";
            open_file(path);
        }

    }

}

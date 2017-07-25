package com.company;

import common.helpers.DataBaseHelper;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Joshua.McCann on 7/3/2017.
 */
public class Main {

    final static Logger logger = Logger.getLogger(Main.class);

    public static void main (String[] args){
        DBMenu();
    }

    public static void DBMenu(){
        int i=0;
        Scanner scanner;
        String location;

        try {
            System.out.println("Welcome to Directory Scanner");
            while (i != 6) {
                scanner = new Scanner(System.in);
                System.out.print("Which directory do you want to scan?: ");
                location = scanner.nextLine();
                System.out.println("Attempting to populate database");
                if (DataBaseHelper.PopulateDataBase(new File(location))) {
                    System.out.println("Directory successfully scanned\n");
                    do {
                        System.out.println("Choose from the following list:\n1.  Display directory with most files\n2.  Display directory largest in size\n3.  Display 5 largest files in size\n4.  Display all files of a certain type\n5.  Clear the database and start over\n6.  Exit\n");
                        System.out.print("Choice:  ");
                        i = scanner.nextInt();
                        switch(i) {
                            case 1  :
                                DataBaseHelper.ExecMostFiles();
                                break;
                            case 2  :
                                DataBaseHelper.ExecLargestFolder();
                                break;
                            case 3  :
                                DataBaseHelper.ExecTopFiveFilesBySizes();
                                break;
                            case 4  :
                                DataBaseHelper.ExecAllFilesForType();
                                break;
                            case 5  :
                                DataBaseHelper.ExecClearDB();
                                break;
                            case 6  :
                                break;
                            default :
                                System.out.println("Invalid input");
                                break;
                        }
                    } while (i < 5 || i > 6);
                } else {
                    System.out.println("Directory Not Found!");
                }
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        DataBaseHelper.ExecClearDB();
    }


}

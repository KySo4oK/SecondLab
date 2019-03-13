package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SecondLab {
    private static ArrayList<String> namesOfCountries = new ArrayList();
    private final static String nameOfInputFile = "eurovision.csv";
    private final static String nameOfOutPutFile = "results.csv";
    private static int numberOfCountries;
    private static int[][] marks;

    /* (Kate Gricaenko)
     methods for extra credit:
       readMarksFromConsole - method that reads marks for countries from console. County cannot add mark for itself
       calculateSumForEveryCountry - method that calculate sum of balls for every country
       findWinnersForExtraCredit - find winners and output it`s list to console*/

    public static void main(String[] args) {
        int[][] votes = fillList();
        int[] result = votesToResult(votes);
        findWinnersAndPrint(result);
    }

    public static void readMarksFromConsole() { // method for extra credit
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of countries for your eurovision: ");
        numberOfCountries = scanner.nextInt();
        marks = new int[numberOfCountries][numberOfCountries];
        for (int i = 0; i < numberOfCountries; i++) {
            System.out.println("Enter current country: ");
            namesOfCountries.add(scanner.nextLine());
            for (int j = 0; j < numberOfCountries; j++) {
                if (j != i) {
                    System.out.println(" Enter current mark: ");
                    marks[i][j] = scanner.nextInt();
                }
                else {
                    marks[i][j] = 0;
                }
            }
        }
    }

    public static int[] calculateSumForEveryCountry(){
        int [] result = new int[numberOfCountries];
        for (int i = 0; i < numberOfCountries; i++) {
            result[i] = marks[i][0];
            for (int j = 1; j < numberOfCountries ; j++) {
                result[i]+=marks[i][j];
            }

        }
        return result;
    }

    public static void findWinnersForExtraCredit(){
        readMarksFromConsole();
        int[] finalMarks = calculateSumForEveryCountry();
        int prevMaximum = finalMarks[0];
        int maximum = finalMarks[0];
        int id = 0;

        for (int i = 1; i <= 3; i++) {
            for (int j = 0; j < finalMarks.length ; j++) {
                if(maximum < finalMarks[j] && finalMarks[j] <=prevMaximum){
                    maximum = finalMarks[j];
                    id = j;
                }
            }
            System.out.println(i + ". " + namesOfCountries.get(id) + " balls: " + maximum);
            prevMaximum = maximum;

        }

    }


    public static int[][] fillList() {// method for filling votes
        ArrayList<String> lines = readAndSplitToLines();
        int[][] votes = new int[numberOfCountries][numberOfCountries];
        String[] wordsForCurrLine;
        for (int i = 0; i < lines.size(); i++) {
            wordsForCurrLine = lines.get(i).split(",");
            namesOfCountries.add(wordsForCurrLine[0]);
            for (int j = 1; j < wordsForCurrLine.length; j++) {
                votes[i][j - 1] = Integer.parseInt(wordsForCurrLine[j]);
            }
        }
        return votes;
    }

    public static ArrayList<String> readAndSplitToLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Reader reader = new FileReader(nameOfInputFile)) {
            Scanner scanner = new Scanner(reader);

            numberOfCountries = scanner.nextInt();
            scanner.nextLine();
            int i = 0;
            String line;
            while (i < numberOfCountries) {
                line = scanner.nextLine();
                lines.add(line);
                i++;

            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return lines;
    }

    public static int[] votesToResult(int[][] votes) {
        int[][] marks = fillZeros();
        for (int j = 0; j < numberOfCountries; j++) {
            boolean st1 = true;
            boolean st2 = true;
            int point = 12;
            int max = votes[0][j];
            int imax = 0;
            while (point > 0) {
                for (int i = 0; i < numberOfCountries; i++) {
                    if (max < votes[i][j]) {
                        max = votes[i][j];
                        imax = i;
                    }
                }
                marks[imax][j] = point;
                votes[imax][j] = 0;
                if (st1) {
                    point -= 2;
                    st1 = false;
                } else if (st2) {
                    point -= 2;
                    st2 = false;
                } else {
                    point--;
                }
                max = votes[0][j];
                imax = 0;
            }
        }
        int[] result = new int[numberOfCountries];
        for (int i = 0; i < numberOfCountries; i++) {
            for (int j = 0; j < numberOfCountries; j++) {
                result[i] += marks[i][j];
            }
        }
        return result;
    }


    public static int[][] fillZeros() {
        int[][] matrix = new int[numberOfCountries][numberOfCountries];
        for (int i = 0; i < numberOfCountries; i++) {
            for (int j = 0; j < numberOfCountries; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    public static void findWinnersAndPrint(int[] rezult) {
        try (FileWriter writer = new FileWriter(nameOfOutPutFile)) {

            int max = rezult[0];
            int id_max = 0;
            for (int k = 1; k <= 10; k++) {
                for (int i = 0; i < numberOfCountries; i++) {
                    if (rezult[i] > max) {
                        max = rezult[i];
                        id_max = i;
                    }
                }
                rezult[id_max] = 0;
                writer.write(k + "." + namesOfCountries.get(id_max) + "," + max + "\r\n");
                max = 0;
                id_max = 0;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}

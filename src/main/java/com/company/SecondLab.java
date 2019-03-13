package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SecondLab {
    private static ArrayList<String> eurovision = new ArrayList();
    private static int numberOfCountries;


    public static void main(String[] args) {
        int[][] votes = fillList();
        int[] result = votesToResult(votes);
        findWinnersAndPrint(result);
    }

    public static int[][] fillList() {// method for filling votes
        ArrayList<String> lines = readAndSplitToLines();
        int[][] votes = new int[numberOfCountries][numberOfCountries];
        String[] wordsForCurrLine;
        for (int i = 0; i < lines.size(); i++) {
            wordsForCurrLine = lines.get(i).split(",");
            eurovision.add(wordsForCurrLine[0]);
            for (int j = 1; j < wordsForCurrLine.length; j++) {
                votes[i][j - 1] = Integer.parseInt(wordsForCurrLine[j]);
            }
        }
        return votes;
    }

    public static ArrayList<String> readAndSplitToLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Reader reader = new FileReader("eurovision.csv")) {
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
        int [] rezult = new int[numberOfCountries];
        for (int i = 0; i < numberOfCountries; i++) {
            for (int j = 0; j < numberOfCountries; j++) {
                rezult[i] += marks[i][j];
            }
        }
        int rez = 0;
        for (int i=0;i<rezult.length;i++){
            rez +=rezult[i];
        }
        return rezult;
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
        try (FileWriter writer = new FileWriter("rezults.csv")){

            int max = rezult[0];
            int imax = 0;
            for (int k = 1; k <= 10; k++) {
                for (int i = 0; i < numberOfCountries; i++) {
                    if (rezult[i] > max) {
                        max = rezult[i];
                        imax = i;
                    }
                }
                rezult[imax] = 0;
                writer.write(k + "." + eurovision.get(imax) + "," + max + "\r\n");
                max = 0;
                imax = 0;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}

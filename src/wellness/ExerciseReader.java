package wellness;

import java.io.*;  
import java.util.*;

public class ExerciseReader {
    private Scanner sc;
    private String fileLocation;

    public ExerciseReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void saveFile(ArrayList<Exercise> exersises) {
        ArrayList<String> exerciseStrings = new ArrayList<String>();

        /* 
         * This block is checking to see which row in the new csv will be the longest in 
         * order to add the appropriate number of commas later
        */
        int largestRow = 0;
        for (Exercise e : exersises) {
            String exerciseString = e.toString();
            int len = exerciseString.split(",").length;

            if (len > largestRow) {
                largestRow = len;
            }

            exerciseStrings.add(exerciseString);
        }

        /* 
         * This block is adding the correct number of commas to each line to ensure the number of 
         * columns is consistent in the csv
        */
        for (int i = 0; i < exerciseStrings.size(); i++) {
            int len = exerciseStrings.get(i).split(",").length;
            int diff = largestRow - len;
            String comma = ",";

            if (diff > 0) {
                String newFoodString = exerciseStrings.get(i) + comma.repeat(diff);
                exerciseStrings.set(i, newFoodString);
            }
        }

        /*
         * Looping through the created strings and writing to the food file
         */
        try {
            PrintWriter output = new PrintWriter(this.fileLocation);
            for (String s : exerciseStrings) {
                output.println(s);
            }
            output.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public ArrayList<Exercise> readFile() {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        try {
            this.sc = new Scanner(new File(this.fileLocation));
            while (this.sc.hasNextLine()) {
                String[] exercise = sc.nextLine().split(",");
                String name = exercise[1];

                // Currently only handling basic exercises; we dont know the format
                // that routines will take
                if (exercise[0].equals("e")) {
                    double calories = Double.parseDouble(exercise[2]);
                    exercises.add(new BasicExercise(name, calories));
                }
            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("An error occurred.");
            fnfe.printStackTrace();
        }

        return exercises;
    }
}

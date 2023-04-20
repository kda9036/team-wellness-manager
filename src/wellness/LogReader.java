package wellness;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogReader {
    private Scanner sc;
    private String fileLocation;
 
    public LogReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    private int getLogByDate(LocalDate date, ArrayList<Log> logs) {
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getDate().isEqual(date)) {
                return i;
            }
        }
        return -1;
    }

    public void saveFile(ArrayList<Log> logs) {
        ArrayList<String> logStrings = new ArrayList<String>();
        
        for (Log l : logs) {
            logStrings.addAll(l.logStrings());
        }
        /*
         * Looping through the created strings and writing to the food file
         */
        try {
            PrintWriter output = new PrintWriter(this.fileLocation);
            for (String s : logStrings) {
                output.println(s);
            }
            output.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }

    }

    public ArrayList<Log> readFile(ArrayList<Food> availableFoods, ArrayList<Exercise> availableExercises) {
        ArrayList<Log> logs = new ArrayList<Log>();
        try {
            this.sc = new Scanner(new File(fileLocation));
            while (this.sc.hasNextLine()) {
                String[] log = sc.nextLine().split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
                LocalDate date = LocalDate.parse(log[0] + "-" + log[1] + "-" + log[2], formatter);
                int logIndex = getLogByDate(date, logs);

                // If a log with the entered date does not exist, create it
                if (logIndex == -1) {
                    logs.add(new Log(date));
                    logIndex = getLogByDate(date, logs);
                }

                Log l = logs.get(logIndex);

                // Set the weight
                if(log[3].equals("w")) {
                    double weight = Double.parseDouble(log[4]);
                    l.setWeight(weight);
                }

                // Set the calorie limit
                if(log[3].equals("c")) {
                    double calories = Double.parseDouble(log[4]);
                    l.setCalories(calories);
                }

                if (log[3].equals("e")) {
                    String name = log[4];
                    double minutes = Double.parseDouble(log[5]);
                    ArrayList<LogExercise> logExercises = new ArrayList<LogExercise>();
                    int exerciseIndex = -1;

                    for (int i = 0; i < availableExercises.size(); i++) {
                        String existingName = availableExercises.get(i).getName();
                        if (name.equals(existingName)) {
                            exerciseIndex = i;
                        }
                    }

                    if (exerciseIndex != -1) {
                        logExercises.add(new LogExercise(availableExercises.get(exerciseIndex), minutes));
                    }

                    l.addExercises(logExercises);
                }

                if(log[3].equals("f")) {
                    String foodName = log[4];
                    double foodQuant = Double.parseDouble(log[5]);
                    ArrayList<LogFood> logFoods = new ArrayList<LogFood>();
                    int foodIndex = -1;
                    
                    for (int i = 0; i < availableFoods.size(); i++) {
                        String name = availableFoods.get(i).getName();
                        if (foodName.equals(name)) {
                            foodIndex = i;
                        }
                    }

                    if (foodIndex != -1) {
                        logFoods.add(new LogFood(availableFoods.get(foodIndex), foodQuant));
                    }

                    l.addFood(logFoods);
                }

            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("An error occurred.");
            fnfe.printStackTrace();
        }

        return logs;
    }
}

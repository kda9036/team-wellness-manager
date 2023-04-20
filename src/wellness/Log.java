package wellness;

import java.time.LocalDate;
import java.util.*;

public class Log implements Comparable<Log> {
    private LocalDate date;
    private double weight;
    private double calories;
    private ArrayList<LogFood> foods;
    private ArrayList<LogExercise> exercises;

    public Log(LocalDate date, double weight, double calories, ArrayList<LogFood> foods, ArrayList<LogExercise> exercises) {
        this.date = date;
        this.weight = weight;
        this.calories = calories;
        this.foods = foods;
        this.exercises = exercises;
    }

    public Log(LocalDate date) {
        this.date = date;
        this.weight = 0;
        this.calories = 0;
        this.foods = new ArrayList<LogFood>();
        this.exercises = new ArrayList<LogExercise>();
    }

    public Log(LocalDate date, double weight, double calories) {
        this.date = date;
        this.weight = weight;
        this.calories = calories;
        this.foods = new ArrayList<LogFood>();
        this.exercises = new ArrayList<LogExercise>();
    }

    public LocalDate getDate() {
        return this.date;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getCalories() {
        return this.calories;
    }

    public ArrayList<LogFood> getFoods() {
        return this.foods;
    }

    public ArrayList<LogExercise> getExercises() {
        return this.exercises;
    }

    public void setFoods(ArrayList<LogFood> foods) {
        this.foods = foods;
    }

    public void addFood(ArrayList<LogFood> newFood) {
        this.foods.addAll(newFood);
    }

    public void setExercises(ArrayList<LogExercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercises(ArrayList<LogExercise> exercises) {
        this.exercises.addAll(exercises);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getConsumedCalories() {
        double cals = 0;

        for (LogFood food : this.foods) {
            cals += food.getFood().getCalories() * food.getQuantity();
        }
        return cals;
    }

    public double[] getNutritionPercentages() {
        double totalGrams = 0;
        double fatGrams = 0;
        double carbGrams = 0;
        double proteinGrams = 0;

        for (LogFood food: this.foods) {
            double fat = food.getFood().getFat() * food.getQuantity();
            double carbs = food.getFood().getCarbs() * food.getQuantity();
            double protein = food.getFood().getProtein() * food.getQuantity();
            
            fatGrams += fat;
            carbGrams += carbs;
            proteinGrams += protein;
            totalGrams += fat + carbs + protein;
        }

        double fatPercent = (fatGrams / totalGrams) * 100;
        double carbPercent = (carbGrams / totalGrams) * 100;
        double proteinPercent = (proteinGrams / totalGrams) * 100;

        if (totalGrams == 0) {
            return new double[] {0, 0, 0};
        }

        return(new double[] {fatPercent, carbPercent, proteinPercent});
    }

    public double getExpendedCalories() {
        double cals = 0;

        for (LogExercise exercise : this.exercises) {
            double minutes = exercise.getMinutes();
            double expended = exercise.getExercise().calcCaloriesBurned(this.weight, minutes);

            cals += expended;
        }

        return cals;
    }

    public double getNetCalories() {
        double consumed = this.getConsumedCalories();
        double expended = this.getExpendedCalories();

        return consumed - expended;
    }

    public double getCalorieDifference() {
        double allowed = this.calories;
        double used = this.getNetCalories();

        return allowed - used;
    }

    public double getFat() {
        double fat = 0;

        for (LogFood food : this.foods) {
            fat += food.getFood().getFat() * food.getQuantity();
        }

        return fat;
    }

    public double getProtein() {
        double protein = 0;

        for (LogFood food : this.foods) {
            protein += food.getFood().getProtein() * food.getQuantity();
        }

        return protein;
    }

    public double getCarbs() {
        double carbs = 0;

        for (LogFood food : this.foods) {
            carbs += food.getFood().getCarbs() * food.getQuantity();
        }

        return carbs;
    }

    public ArrayList<String> logStrings() {
        String year = Integer.toString(this.getDate().getYear());
        String month = Integer.toString(this.getDate().getMonthValue());
        String day = "";
        if (this.getDate().getDayOfMonth() < 10) {
            day += "0";
        }
        day += Integer.toString(this.getDate().getDayOfMonth());
        String csvDate = String.format("%s,%s,%s", year, month, day);

        ArrayList<String> logStrings = new ArrayList<String>();
        if (this.getWeight() > 0) {
            logStrings.add(String.format("%s,w,%s,", csvDate, Double.toString(this.getWeight())));
        }        
        
        if (this.getCalories() > 0) {
            logStrings.add(String.format("%s,c,%s,", csvDate, Double.toString(this.getCalories())));
        }

        if (this.getFoods().size() > 0) {
            for (LogFood lf : this.getFoods()) {
                String foodName = lf.getFood().getName();
                double quant = lf.getQuantity();
                logStrings.add(String.format("%s,f,%s,%3.2f", csvDate, foodName, quant));
            }
        }

        if (this.getExercises().size() > 0) {
            for (LogExercise le : this.getExercises()) {
                String name = le.getExercise().getName();
                double minutes = le.getMinutes();
                logStrings.add(String.format("%s,e,%s,%3.2f", csvDate, name, minutes));
            }
        }
        return logStrings;
    }

    @Override
    public int compareTo(Log o) {
        return getDate().compareTo(o.getDate());
    }
}

package wellness;

import java.awt.event.*;
import java.time.*;
import java.util.HashMap;
import java.util.ArrayList;

public class WellnessController implements ActionListener {
    WellnessMediator wm;

    public WellnessController(WellnessMediator wm) {
        this.wm = wm;
    }

    public void actionPerformed(ActionEvent e) {
        // event listeners will be implemented when a GUI is created
    }

    // add basic food
    public void addFood(String name, double calories, double fat, double carbs, double protein) {
        wm.addFood(name, calories, fat, carbs, protein);
    }

    // delete food
    public void deleteFood(int index) {
        wm.deleteFood(index);
    }

    public void setLogWeight(LocalDate date, double weight) {
        this.wm.setLogWeight(date, weight);
    }

    public void setCalorieLimit(LocalDate date, double calories) {
        this.wm.setLogCalories(date, calories);
    }

    public void logFood(LocalDate date, ArrayList<LogFood> food) {
        this.wm.addLogFood(date, food);
    }
    
    // add recipe
    public void addFood(String name, HashMap<Food, Double> ingredients) {
        wm.addFood(name, ingredients);
    }

    public void createExercise(String name, double calories) {
        wm.createExercise(name, calories);
    }

    public void logExercise(LocalDate date, ArrayList<LogExercise> exercises) {
        this.wm.addLogExercise(date, exercises);
    }

    public void setCurrentLog(LocalDate date) {
        this.wm.setCurrentLog(date);
    }

    public void saveFile() {
        wm.saveFile();
    }
}

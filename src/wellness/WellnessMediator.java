package wellness;

import java.time.LocalDate;
import java.util.*;

public class WellnessMediator extends Observable {
    private FoodReader fr;
    private LogReader lr;
    private ExerciseReader er;
    private WellnessState state;

    public WellnessMediator(String foodLocation, String logLocation, String exerciseLocation) {
        super();
        this.fr = new FoodReader(foodLocation);
        this.lr = new LogReader(logLocation);
        this.er = new ExerciseReader(exerciseLocation);
        this.state = new WellnessState();
    }

    // This will  get the index of the log by date, or if the log does not exist, it will create a new log
    private int getLogIndex(LocalDate date) {
        int logIndex = this.state.getLogIndexByDate(date);
        if (logIndex == -1) {
            double lastWeight = this.state.getPreviousWeight(date);
            double lastCalories = this.state.getPreviousCalories(date);
            this.state.logs.add(new Log(date, lastWeight, lastCalories));

            logIndex = this.state.getLogIndexByDate(date);
        }
        return logIndex;
    }

    private boolean foodExists(String name) {
        for (Food f : this.state.food) {
            if (f.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private boolean exerciseExists(String name) {
        for (Exercise e : this.state.exercises) {
            if (e.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void addFood(String name, double calories, double fat, double carbs, double protein) {
        if (!foodExists(name)) {
            this.state.food.add(new BasicFood(name, calories, fat, carbs, protein));
        }
    }

    public void deleteFood(int index) {
        // // Future: Needs to be able to handle when the user enters a food that does not exist
        this.state.food.remove(index);
    }

    public void addFood(String name, HashMap<Food, Double> ingredients) {
        if (!foodExists(name)) {
            this.state.food.add(new Recipe(name, ingredients));
            setChanged();
            notifyObservers(this.state);
        }

    }

    public void setLogWeight(LocalDate date, double weight) {
        int logIndex = this.getLogIndex(date);

        this.state.logs.get(logIndex).setWeight(weight);
        setChanged();
        notifyObservers(this.state);
    }

    public void setCurrentLog(LocalDate date) {
        int logIndex = this.getLogIndex(date);
        this.state.activeDate = date;
        setChanged();
        notifyObservers(this.state);
    }

    public void setLogCalories(LocalDate date, double calories) {
        int logIndex = this.getLogIndex(date);

        this.state.logs.get(logIndex).setCalories(calories);
        setChanged();
        notifyObservers(this.state);
    }

    public void addLogFood(LocalDate date, ArrayList<LogFood> food) {
        int logIndex = this.getLogIndex(date);

        this.state.logs.get(logIndex).addFood(food);
        setChanged();
        notifyObservers(this.state);
    }

    public void createExercise(String name, double calories) {
        if (!exerciseExists(name)) {
            this.state.exercises.add(new BasicExercise(name, calories));
            setChanged();
            notifyObservers(this.state);
        }
    }

    public void addLogExercise(LocalDate date, ArrayList<LogExercise> exercises) {
        int logIndex = this.getLogIndex(date);

        this.state.logs.get(logIndex).addExercises(exercises);
        setChanged();
        notifyObservers(this.state);
    }

    public void saveFile() {
        this.fr.saveFile(this.state.food);
        this.lr.saveFile(this.state.logs);
        this.er.saveFile(this.state.exercises);
    }

    public void loadData() {
        this.state.food = fr.readFile();
        this.state.exercises = er.readFile();
        this.state.logs = lr.readFile(this.state.food, this.state.exercises);
        setChanged();
        notifyObservers(this.state);
    }
}

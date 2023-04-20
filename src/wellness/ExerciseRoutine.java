package wellness;
import java.util.*;

public class ExerciseRoutine implements Exercise {
    private String name;
    private ArrayList<Exercise> exercises;

    public ExerciseRoutine(String name, ArrayList<Exercise> exersises) {
        this.name = name;
        this.exercises = exersises;
    }

    public String getName() {
        return this.name;
    }

    public double calcCaloriesBurned(double weight, double minutes) {
        double cals = 0;
        for (Exercise e : exercises) {
            cals += e.calcCaloriesBurned(weight, minutes);
        }

        return cals;
    }

    public double getDefaultCalories() {
        double cals = 0;
        for (Exercise e : exercises) {
            cals += e.getDefaultCalories();
        }

        return cals;
    }
}

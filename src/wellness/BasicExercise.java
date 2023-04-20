package wellness;

public class BasicExercise implements Exercise {
    private String name;
    private double caloriesBurned;

    public BasicExercise(String name, double calories) {
        this.name = name;
        this.caloriesBurned = calories;
    }

    public String getName() {
        return this.name;
    }

    public double getDefaultCalories() {
        return this.caloriesBurned;
    }

    public double calcCaloriesBurned(double weight, double minutes) {
        double cals = Math.floor(this.caloriesBurned * (weight / 100.0) * (minutes / 60.0));
        
        return cals;
    }

    public String toString() {
        return String.format("e,%s,%3.2f", this.getName(), this.caloriesBurned);
    }
}

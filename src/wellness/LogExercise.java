package wellness;

public class LogExercise {
    private Exercise exercise;
    private double minutes;

    public LogExercise(Exercise exercise, double minutes) {
        this.exercise = exercise;
        this.minutes = minutes;
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public double getMinutes() {
        return this.minutes;
    }
}

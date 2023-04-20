package wellness;

public interface Exercise {
    public String getName();
    public double calcCaloriesBurned(double weight, double minutes);
    public double getDefaultCalories();
}

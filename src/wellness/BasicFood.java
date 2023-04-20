package wellness;

public class BasicFood implements Food {
    private String name;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;
 
    public BasicFood(String name, double calories, double fat, double carbs, double protein) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }
 
    public String getName() {
        return this.name;
    }
 
    public double getCalories() {
        return this.calories;
    }
 
    public double getFat() {
        return this.fat;
    }
 
    public double getCarbs() {
        return this.carbs;
    }
 
    public double getProtein() {
        return this.protein;
    }

    public String toString() {
        String foodString = String.format("b,%s,%3.2f,%3.2f,%3.2f,%3.2f", this.getName(), this.getCalories(), this.getFat(), this.getCarbs(), this.getProtein());
        return foodString;
    }

}

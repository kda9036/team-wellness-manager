package wellness;
import java.util.ArrayList;
import java.util.HashMap;

public class Recipe implements Food {
    private String name;
    private HashMap<Food, Double> ingredientMap;

    public Recipe(String name, HashMap<Food, Double> ingredientMap) {
        this.name = name;
        this.ingredientMap = ingredientMap;
    }

    public String getName() {
        return this.name;
    }

    public double getCalories() {
        double total = 0.0;
        
        for (HashMap.Entry<Food, Double> entry : ingredientMap.entrySet()) {
            Food ingredient = entry.getKey();
            double quantity = entry.getValue();

            total += ingredient.getCalories() * quantity;
        }

        return total;
    }

    public double getCarbs() {
        double total = 0.0;

        for (HashMap.Entry<Food, Double> entry : ingredientMap.entrySet()) {
            Food ingredient = entry.getKey();
            double quantity = entry.getValue();

            total += ingredient.getCarbs() * quantity;
        }

        return total;
    }

    public double getFat() {
        double total = 0.0;

        for (HashMap.Entry<Food, Double> entry : ingredientMap.entrySet()) {
            Food ingredient = entry.getKey();
            double quantity = entry.getValue();

            total += ingredient.getFat() * quantity;
        }

        return total;
    }

    public double getProtein() {
        double total = 0.0;

        for (HashMap.Entry<Food, Double> entry : ingredientMap.entrySet()) {
            Food ingredient = entry.getKey();
            double quantity = entry.getValue();

            total += ingredient.getProtein() * quantity;
        }

        return total;
    }

    public String toString() {
        String ingredientString = String.format("r,%s", this.getName());

        for (HashMap.Entry<Food, Double> entry : ingredientMap.entrySet()) {
            Food ingredient = entry.getKey();
            double quantity = entry.getValue();

            ingredientString += String.format(",%s,%3.1f", ingredient.getName(), quantity);
        }

        return ingredientString;
    }
}

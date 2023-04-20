package wellness;

public class LogFood {
    private Food food;
    private double quantity;

    public LogFood(Food food, double quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return this.food;
    }

    public double getQuantity() {
        return this.quantity;
    }
}

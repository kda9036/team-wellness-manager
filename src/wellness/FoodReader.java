package wellness;
 
import java.io.*;  
import java.util.*;

public class FoodReader {
    private Scanner sc;
    private String fileLocation;
 
    public FoodReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    private Food getIngredient(String name, ArrayList<Food> foods) {
        for (Food f : foods) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return new BasicFood("err", 0.0, 0.0, 0.0, 0.0);
    }

    public void saveFile(ArrayList<Food> foods) {
        ArrayList<String> foodStrings = new ArrayList<String>();
        
        /* 
         * This block is checking to see which row in the new csv will be the longest in 
         * order to add the appropriate number of commas later
        */
        int largestRow = 0;
        for (Food f : foods) {
            String foodString = f.toString();
            int len = foodString.split(",").length;

            if (len > largestRow) {
                largestRow = len;
            }

            foodStrings.add(foodString);
        }


        /* 
         * This block is adding the correct number of commas to each line to ensure the number of 
         * columns is consistent in the csv
        */
        for (int i = 0; i < foodStrings.size(); i++) {
            int len = foodStrings.get(i).split(",").length;
            int diff = largestRow - len;
            String comma = ",";

            if (diff > 0) {
                String newFoodString = foodStrings.get(i) + comma.repeat(diff);
                foodStrings.set(i, newFoodString);
            }
        }

        /*
         * Looping through the created strings and writing to the food file
         */
        try {
            PrintWriter output = new PrintWriter(this.fileLocation);
            for (String s : foodStrings) {
                output.println(s);
            }
            output.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }

    }

    public ArrayList<Food> readFile() {
        ArrayList<Food> foods = new ArrayList<Food>();
        try {
            this.sc = new Scanner(new File(this.fileLocation));
            while (this.sc.hasNextLine()) {
                String[] food = sc.nextLine().split(",");
                String name = food[1];
                
                if (food[0].equals("b")) {
                    double calories = Double.parseDouble(food[2]);
                    double fat = Double.parseDouble(food[3]);
                    double carbs = Double.parseDouble(food[4]);
                    double protein = Double.parseDouble(food[5]);
 
                    foods.add(new BasicFood(name, calories, fat, carbs, protein));
                }

                if (food[0].equals("r")) {
                    HashMap<Food, Double> ingredientMap = new HashMap<Food, Double>();
                    String[] strippedArr = Arrays.copyOfRange(food, 2, food.length);

                    for (int i = 0; i < strippedArr.length; i++) {
                        Food ingredient;
                        Double quantity;

                        if (i % 2 == 0) {
                            Food ing = getIngredient(strippedArr[i], foods);
                            if (!ing.getName().equals("err")) {
                                ingredient = ing;
                                quantity = Double.parseDouble(strippedArr[i+1]);

                                ingredientMap.put(ingredient, quantity);
                            }
                        }
                    }

                    foods.add(new Recipe(name, ingredientMap));
                }
            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("An error occurred.");
            fnfe.printStackTrace();
        }

        return foods;
    }
}

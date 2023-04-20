package wellness;

import java.util.Observable;
import java.util.Observer;
import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ManagerCLI implements Observer, ActionListener {
    private WellnessMediator wm;
    private WellnessController wc;
    private LogCLIMenu logAdder;
    private Scanner sc;
    private WellnessState state;
    private JFrame frame, graphFrame;
    private Image icon;
    private JPanel cPanel;
    private JButton opt0, opt1, opt2, opt3, opt4, opt5, opt6, opt7, optCreateExercise;
    private JTextArea ta;
    private JScrollPane scroll;
    private JMenuBar mb;

    public ManagerCLI(WellnessMediator wm, WellnessController wc) {
        this.sc = new Scanner(System.in);
        this.wm = wm;
        this.wc = wc;
        this.logAdder = new LogCLIMenu(sc, wc, wm);
        wm.addObserver(this);
        this.state = new WellnessState();
    }

    public void update(Observable obs, Object payload) {
        WellnessState state = (WellnessState) payload;
        this.state = state;
    }

    public void run() {
        // Welcome popup
        ImageIcon logoIcon = new ImageIcon("src/wellness/resources/logo.png");
        JOptionPane.showMessageDialog(null, null, "WELCOME",
                JOptionPane.PLAIN_MESSAGE, logoIcon);

        // adding a GUI
        // Creating the Frame
        frame = new JFrame("Wellness Manager");
        icon = Toolkit.getDefaultToolkit().getImage("src/wellness/resources/logo_simple.png");
        frame.setIconImage(icon);

        // Create graph frame
        graphFrame = new JFrame("Log Info");
        // Add graph to the frame
        graphFrame.add(new NewGraphCanvas(wm), BorderLayout.CENTER);
        graphFrame.setSize(300, 300);

        // Save and Exit on window close
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                wc.saveFile();
                System.exit(0);
            }
        });
        frame.setSize(700, 850);

        // Menu bar
        mb = createMenuBar();

        // Creating buttons for main menu selections with icons
        ImageIcon icon0 = new ImageIcon(new ImageIcon("src/wellness/resources/recipe_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt0 = new JButton(icon0);
        opt0.setText("View Foods/Recipes");
        ImageIcon icon1 = new ImageIcon(new ImageIcon("src/wellness/resources/treadmill_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt1 = new JButton(icon1);
        opt1.setText("View Exercises");
        ImageIcon icon2 = new ImageIcon(new ImageIcon("src/wellness/resources/add_food_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt2 = new JButton(icon2);
        opt2.setText("Add Food");
        ImageIcon icon3 = new ImageIcon(new ImageIcon("src/wellness/resources/add_recipe_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt3 = new JButton(icon3);
        opt3.setText("Add Recipe");
        ImageIcon iconCreateExercise = new ImageIcon(
                new ImageIcon("src/wellness/resources/add_exercise_icon.png").getImage()
                        .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        optCreateExercise = new JButton(iconCreateExercise);
        optCreateExercise.setText("Add Exercise");
        ImageIcon icon4 = new ImageIcon(new ImageIcon("src/wellness/resources/delete_food_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt4 = new JButton(icon4);
        opt4.setText("Delete Food/Recipe");
        ImageIcon icon5 = new ImageIcon(new ImageIcon("src/wellness/resources/log_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt5 = new JButton(icon5);
        opt5.setText("View/Create Log");
        ImageIcon icon6 = new ImageIcon(new ImageIcon("src/wellness/resources/progress_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt6 = new JButton(icon6);
        opt6.setText("Check Progress");
        ImageIcon icon7 = new ImageIcon(new ImageIcon("src/wellness/resources/save_icon.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        opt7 = new JButton(icon7);
        opt7.setText("Save & Exit");

        // Text Area w/ scroll
        ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setEditable(false);
        ta.setBackground(Color.WHITE);
        ta.setForeground(Color.BLUE);
        scroll = new JScrollPane(ta);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Center panel for buttons and text area
        cPanel = new JPanel();
        cPanel.setBackground(Color.darkGray);
        GridBagLayout layout = new GridBagLayout();

        cPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        cPanel.add(opt0, gbc);

        gbc.gridy = 0;
        gbc.gridx = 2;
        cPanel.add(opt1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        cPanel.add(opt2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        cPanel.add(opt3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        cPanel.add(optCreateExercise, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        cPanel.add(opt4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        cPanel.add(opt5, gbc);

        gbc.gridx = 2;
        gbc.gridy = 12;
        cPanel.add(opt6, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 3;
        cPanel.add(opt7, gbc);

        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.weighty = 8;
        cPanel.add(scroll, gbc);

        // Adding event listeners to buttons
        opt0.addActionListener(this);
        opt1.addActionListener(this);
        opt2.addActionListener(this);
        opt3.addActionListener(this);
        optCreateExercise.addActionListener(this);
        opt4.addActionListener(this);
        opt5.addActionListener(this);
        opt6.addActionListener(this);
        opt7.addActionListener(this);

        // Adding Components to the frame
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, cPanel);

        // Adjust frame height and width - set a default value vs dynamic?
        // frame.pack();
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // int height = screenSize.height;
        // int width = screenSize.width;
        // frame.setSize(width / 3, height / 2);

        // Center the frame on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        graphFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton actionSource = (JButton) e.getSource();

        if (actionSource.equals(opt0)) {
            // View Foods/Recipes
            getFoodOptions();
        } else if (actionSource.equals(opt1)) {
            // View Exercises
            getExerciseOptions();
        } else if (actionSource.equals(opt2)) {
            // Add Food
            setFoodInfo();
        } else if (actionSource.equals(opt3)) {
            // Add Recipe
            createRecipe();
        } else if (actionSource.equals(optCreateExercise)) {
            createExercise();
        } else if (actionSource.equals(opt4)) {
            // Delete Food/Recipe
            deleteFood();
        } else if (actionSource.equals(opt5)) {
            // View/Create Log
            cPanel.setVisible(false);
            this.logAdder.run(frame, cPanel, ta);
        } else if (actionSource.equals(opt6)) {
            // Check Progress
            ta.append(String.format("\nCurrent weight: %3.2f\nCurrent daily calorie limit: %3.2f\n\n",
                    this.state.getCurrentWeight(), this.state.getCurrentCalorieGoal()));
        } else if (actionSource.equals(opt7)) {
            // Save & Exit
            wc.saveFile();
            System.exit(0);
        }
    }

    // complete
    public void deleteFood() {
        try {
            String delName = JOptionPane
                    .showInputDialog("\nPlease enter the food or recipe's name that you would like to delete:\n");

            if (delName == null) {
                ta.append("\nCancelled.\n");
                return;
            }
            for (int i = 0; i < this.state.food.size(); i++) {
                Food foodEl = this.state.food.get(i);
                String name = foodEl.getName();
                if (name.equalsIgnoreCase(delName)) {
                    int index = i;
                    wc.deleteFood(index);
                    ta.append("\nDeletion complete. " + delName + " was removed.\n");
                    return;
                }
            }
            ta.append("\nDeletion incomplete. " + delName + " does not exist.\n");
            return;
        } catch (InputMismatchException ime) {
            ta.append("\nInvalid input value\n");
        } catch (Exception e) {
            ta.append("\nCancelled.\n");
        }
    }

    // complete
    public void getFoodOptions() {
        try {
            ta.append("\nFood in program listed below:");
            for (int i = 0; i < this.state.food.size(); i++) {
                Food foodEl = this.state.food.get(i);
                ta.append("\n" + foodEl.getName() + "   Calories: " + foodEl.getCalories());
            }
            ta.append("\n");

        } catch (Exception e) {
            ta.append("\nAn error has occured. Try again.\n");
        }
    }

    public void getExerciseOptions() {
        try {
            ta.append("\nExercises in program listed below:");
            for (int i = 0; i < this.state.exercises.size(); i++) {
                Exercise exercise = this.state.exercises.get(i);
                ta.append("\nExercise: " + exercise.getName() + "   Calorie Expenditure: "
                        + exercise.getDefaultCalories());
            }
            ta.append("\n");

        } catch (Exception e) {
            ta.append("\nAn error has occured. Try again.\n");
        }
    }

    // complete
    public void setFoodInfo() {
        try {
            String name = JOptionPane.showInputDialog("Please enter the food's name:");
            if (name == null) {
                ta.append("\nCancelled.\n");
                return;
            }
            double calories = Double.parseDouble(JOptionPane.showInputDialog("Please enter the number of calories:"));
            double fat = Double.parseDouble(JOptionPane.showInputDialog("Please enter the grams of fat:"));
            double carbs = Double.parseDouble(JOptionPane.showInputDialog("Please enter the grams of carbs:"));
            double protein = Double.parseDouble(JOptionPane.showInputDialog("Please enter the grams of protein:"));
            wc.addFood(name, calories, fat, carbs, protein);
            ta.append("\n" + name + " have been added.\nView foods to see change.\n");
        } catch (InputMismatchException ime) {
            ta.append("\nInvalid input value\n");
        } catch (Exception e) {
            ta.append("\nCancelled.\n");
        }
    }

    public void createExercise() {
        try {
            String name = JOptionPane.showInputDialog("Please enter the name of the exercise:");
            if (name == null) {
                ta.append("\nCancelled.\n");
                return;
            }

            double calories = Double
                    .parseDouble(JOptionPane.showInputDialog("Please enter the calorie expendature of this activity:"));
            wc.createExercise(name, calories);
            ta.append("\n" + name + " has been added.\nView exercises to see change.\n");

        } catch (InputMismatchException ime) {
            ta.append("\nInvalid input value\n");
        } catch (Exception e) {
            ta.append("\nCancelled.\n");
        }
    }

    // complete
    public void createRecipe() {
        String name = " ";
        boolean addingIngredients = true;
        HashMap<Food, Double> ingredientsAdded = new HashMap<Food, Double>();

        name = JOptionPane.showInputDialog("Please enter the recipes's name:");
        if (name == null) {
            ta.append("\nCancelled.\n");
            return;
        }

        while (addingIngredients) {
            int itemIndex = -1;
            try {
                String ingredientName = JOptionPane.showInputDialog(
                        "Add the name of the ingredient you'd like to add to your recipe, \nor enter '0' to finish adding ingredients:");
                if (ingredientName == null) {
                    ta.append("\nCancelled.\n");
                    return;
                } else if (ingredientName.equals("0") && ingredientsAdded.isEmpty()) {
                    addingIngredients = false;
                    continue;
                }

                // If there are ingredients in the list to be added, they will be shown to the
                // user
                if (ingredientName.equals("0") && !ingredientsAdded.isEmpty()) {
                    ta.append("\nYou are adding the following ingredients to your recipe:\n");
                    for (Map.Entry<Food, Double> item : ingredientsAdded.entrySet()) {
                        String ingred = String.format("%s - %3.2f\n", item.getKey().getName(), item.getValue());
                        ta.append(ingred);
                    }
                    int confirm = JOptionPane.showConfirmDialog(null, "Is this okay?\n(See text area)");
                    // 0=yes, 1=no, 2=cancel

                    // The user will be given a chance to confirm their entries. If they do not
                    // approve,
                    // the list is cleared.
                    if (confirm == 0) {
                        wc.addFood(name, ingredientsAdded);
                        addingIngredients = false;
                        continue;
                    } else {
                        ingredientsAdded.clear();
                        ta.append("Added ingredients cleared.");
                        continue;
                    }
                }

                // Check if the item exists in the available item list
                for (int i = 0; i < this.state.food.size(); i++) {
                    if (this.state.food.get(i).getName().equals(ingredientName)) {
                        itemIndex = i;
                    }
                }

                if (itemIndex != -1) {
                    try {
                        double quantity = Double
                                .parseDouble(JOptionPane.showInputDialog("Enter the quantity of this item: "));
                        ingredientsAdded.put(this.state.food.get(itemIndex), quantity);
                        String staging = String.format("\n%s added to staging list\n", ingredientName);
                        ta.append(staging);
                        itemIndex = -1;
                        continue;
                    } catch (InputMismatchException ime) {
                        ta.append("\nPlease enter a number\n");
                        continue;
                    }
                }

                ta.append("\n" + ingredientName
                        + "does not currently exist.\nPlease add the food first then try again.\n");

            } catch (Exception e) {
                ta.append("\nCancelled\n");
            }
        }

    }

    public JMenuBar createMenuBar() {
        // Creating the MenuBar and adding components
        mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Help");

        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Save");
        // Save menu option saves (no exit)
        m11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wc.saveFile();
                ta.append("\nSave Successful!\n");
            }
        });

        JMenuItem m22 = new JMenuItem("Exit");
        // Exit menu option exits (no save)
        m22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem m33 = new JMenuItem("Help Info");
        // Display help information in text area
        m33.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display help info in text area
                ta.append(
                        "\nAbout Wellness Manager / Help: \nSet nutrition and weight goals, log foods and exercises, and track progress.\n Use buttons to make selections.  Answer prompts as directed.\n Note: Foods must be enterered/exist before a recipe containing them can be added.\nExercises must be entered/exist before an exercise routine containing them can be entered.");
            }
        });

        m1.add(m11);
        m1.add(m22);
        m2.add(m33);

        return mb;
    }
}

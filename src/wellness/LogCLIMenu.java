package wellness;

import java.util.*;
import java.util.List;
import java.time.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class LogCLIMenu implements Observer, ActionListener {
    private Scanner sc;
    private ManagerCLI mainMenu;
    private WellnessMediator wm;
    private WellnessController wc; // This might have to be removed later when we have listeners?
    private WellnessState state;
    private JFrame frame;
    private JPanel logPanel;
    private JLabel currentDay;
    private JButton changeDay, lOpt0, lOpt1, lOpt2, lOpt3, lOpt4, lOpt5, lOptViewWeight, lOptViewCalorielimit,
            lOptViewNetCals, lOptViewFoods, lOptViewExercises, lOptViewNutrition;
    private JTextArea logTa;
    private JScrollPane scroll;
    private LocalDate logDay;
    private JSplitPane logSplit;

    public LogCLIMenu(Scanner sc, WellnessController wc, WellnessMediator wm) {
        this.sc = sc;
        this.wc = wc;
        this.wm = wm;
        this.logDay = LocalDate.now(); // By default, display the log for the current day
        this.currentDay = new JLabel(logDay.toString());
        wm.addObserver(this);
        this.state = new WellnessState();
    }

    public void update(Observable obs, Object payload) {
        WellnessState state = (WellnessState) payload;
        this.state = state;
        currentDay.setText(this.state.activeDate.toString());
        this.logDay = this.state.activeDate;
    }

    public void run(JFrame frame, JPanel cPanel, JTextArea ta) {
        this.frame = frame;
        // Create a split pane
        logSplit = new JSplitPane();

        // Create the label and button for displaying and changing the current day
        // currentDay = new JLabel(logDay.toString());
        changeDay = new JButton("Change current log");

        // Add the label and button to the split pane
        logSplit.setLeftComponent(currentDay);
        logSplit.setRightComponent(changeDay);
        logSplit.setEnabled(false);

        // Creating buttons for log panel
        lOpt0 = new JButton("Return to main menu");
        lOpt0.putClientProperty("prevPanel", cPanel);
        lOpt1 = new JButton("Set current weight");
        lOptViewWeight = new JButton("View current weight");
        lOpt2 = new JButton("Set calorie limit");
        lOptViewCalorielimit = new JButton("View current calorie limit");
        lOptViewNetCals = new JButton("View net calories");
        lOpt3 = new JButton("Add food(s)");
        lOpt4 = new JButton("Add exercise(s)");
        lOpt5 = new JButton("View logs");
        lOptViewFoods = new JButton("View foods in current log");
        lOptViewExercises = new JButton("View exercises in current log");
        lOptViewNutrition = new JButton("View nutritional information for current log");

        // Text Area w/ scroll
        logTa = new JTextArea();
        logTa.setLineWrap(true);
        logTa.setEditable(false);
        logTa.setBackground(Color.WHITE);
        logTa.setForeground(Color.BLUE);
        // set content from preview menu / operations
        logTa.setDocument(ta.getDocument());
        scroll = new JScrollPane(logTa);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });

        // Center panel for buttons and text area
        logPanel = new JPanel();
        logPanel.setBackground(Color.darkGray);
        GridBagLayout layout = new GridBagLayout();

        logPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        logPanel.add(logSplit, gbc);

        gbc.gridy = 0;
        gbc.gridx = 2;
        logPanel.add(lOptViewNetCals, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        logPanel.add(lOpt1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        logPanel.add(lOptViewWeight, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        logPanel.add(lOpt2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        logPanel.add(lOptViewCalorielimit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        logPanel.add(lOpt3, gbc);

        gbc.gridx = 2;
        gbc.gridy = 12;
        logPanel.add(lOptViewFoods, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        logPanel.add(lOpt4, gbc);

        gbc.gridx = 2;
        gbc.gridy = 14;
        logPanel.add(lOptViewExercises, gbc);

        gbc.gridx = 0;
        gbc.gridy = 16;
        logPanel.add(lOpt5, gbc);

        gbc.gridx = 2;
        gbc.gridy = 16;
        logPanel.add(lOptViewNutrition, gbc);

        gbc.gridx = 0;
        gbc.gridy = 24;
        gbc.gridwidth = 3;
        logPanel.add(lOpt0, gbc);

        gbc.gridy = 26;
        gbc.gridwidth = 3;
        gbc.weighty = 10;
        logPanel.add(scroll, gbc);

        // Adding event listeners to buttons
        lOpt0.addActionListener(this);
        lOpt1.addActionListener(this);
        lOpt2.addActionListener(this);
        lOpt3.addActionListener(this);
        lOpt4.addActionListener(this);
        lOpt5.addActionListener(this);
        changeDay.addActionListener(this);
        lOptViewWeight.addActionListener(this);
        lOptViewCalorielimit.addActionListener(this);
        lOptViewNetCals.addActionListener(this);
        lOptViewExercises.addActionListener(this);
        lOptViewFoods.addActionListener(this);
        lOptViewNutrition.addActionListener(this);

        // Adding Components to the frame
        frame.getContentPane().add(BorderLayout.CENTER, logPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton actionSource = (JButton) e.getSource();

        if (actionSource.equals(changeDay)) {
            changeDay();
        } else if (actionSource.equals(lOpt1)) {
            // Set current weight
            setWeight();
        } else if (actionSource.equals(lOpt2)) {
            // Set calorie limit
            setCalorieLimit();
        } else if (actionSource.equals(lOpt3)) {
            // Add food(s)
            addFood();
        } else if (actionSource.equals(lOpt4)) {
            // Add exercise(s)
            addExercise();
        } else if (actionSource.equals(lOpt5)) {
            // View Logs
            viewLogs();
        } else if (actionSource.equals(lOpt0)) {
            // Return to main menu
            logPanel.setVisible(false);
            JPanel previous = (JPanel) actionSource.getClientProperty("prevPanel");
            previous.setVisible(true);
        } else if (actionSource.equals(lOptViewWeight)) {
            viewWeight();
        } else if (actionSource.equals(lOptViewCalorielimit)) {
            viewCalorieLimit();
        } else if (actionSource.equals(lOptViewNetCals)) {
            viewNetCalories();
        } else if (actionSource.equals(lOptViewFoods)) {
            viewFoods();
        } else if (actionSource.equals(lOptViewExercises)) {
            viewExercises();
        } else if (actionSource.equals(lOptViewNutrition)) {
            viewNutrition();
        }
    }

    private void changeDay() {
        try {
            String date = JOptionPane.showInputDialog(
                    "\nPlease enter the date of the log you's like to view/edit (yyyy-mm-dd)\nor enter 0 to return to the previous menu:\n");
            if (date == "0") {
                return;
            }

            if (!date.matches("^(19|20)\\d\\d([- ])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])$")) {
                logTa.append("\nPlease enter a date in the format 'yyyy-mm-dd'\n");
                return;
            }

            this.logDay = LocalDate.parse(date);
            this.currentDay.setText(logDay.toString());
            this.wc.setCurrentLog(this.logDay);

        } catch (Exception e) {
            logTa.append("\nCancelled.\n");
        }
    }

    private void viewFoods() {
        int logIndex = this.state.getLogIndexByDate(logDay);

        if (logIndex != -1) {
            ArrayList<LogFood> foods = this.state.logs.get(logIndex).getFoods();
            if (foods.size() > 0) {
                for (LogFood f : foods) {
                    logTa.append(String.format("\n%s - %3.2f", f.getFood().getName(), f.getQuantity()));
                }
                logTa.append("\n");
                return;
            }
        }
        logTa.append("\nNo foods found in current log\n");
    }

    private void viewExercises() {
        int logIndex = this.state.getLogIndexByDate(logDay);

        if (logIndex != -1) {
            ArrayList<LogExercise> exercises = this.state.logs.get(logIndex).getExercises();
            if (exercises.size() > 0) {
                for (LogExercise e : exercises) {
                    logTa.append(String.format("\n%s - %3.2f", e.getExercise().getName(), e.getMinutes()));
                }
                logTa.append("\n");
                return;
            }
        }
        logTa.append("\nNo exercises found in current log\n");
    }

    private void viewNutrition() {
        int logIndex = this.state.getLogIndexByDate(logDay);

        if (logIndex != -1) {
            double[] nutrition = this.state.logs.get(logIndex).getNutritionPercentages();
            logTa.append(String.format("\nFat: %3.2f%%\nCarbs: %3.2f%%\nProtein: %3.2f%%\n", nutrition[0], nutrition[1],
                    nutrition[2]));
            return;
        }

        logTa.append("\nNo foods found in current log\n");
    }

    // complete
    private void setWeight() {
        try {
            double weight = Double.parseDouble(JOptionPane.showInputDialog(
                    "\nPlease enter your current weight in pounds\n"));
            if (weight <= 0) {
                logTa.append("\nWeight cannot be set to " + weight + "\n");
                return;
            }

            wc.setLogWeight(logDay, weight);
            logTa.append("\nWeight set to " + weight + "\n");
        } catch (Exception e) {
            logTa.append("\nCancelled.\n");
        }
    }

    private void viewWeight() {
        int logIndex = this.state.getLogIndexByDate(logDay);
        double weight;
        if (logIndex != -1) {
            weight = this.state.logs.get(logIndex).getWeight();
        } else {
            weight = this.state.getPreviousWeight(logDay);
        }
        logTa.append(String.format("Weight for %s - %3.2f\n", logDay.toString(), weight));
    }

    // complete
    private void viewLog(Log viewed) {
        if (viewed.getWeight() > 0) {
            logTa.append("\nWeight: " + String.valueOf(viewed.getWeight()) + "\n");
        }
        if (viewed.getCalories() > 0) {
            logTa.append("Calorie Limit: " + String.valueOf(viewed.getCalories()) + "\n");
        }
        if (viewed.getFoods().size() > 0) {
            logTa.append("Foods eaten:\n");
            for (LogFood f : viewed.getFoods()) {
                logTa.append(f.getFood().getName() + " - " + f.getQuantity() + "\n");
            }
        }
        if (viewed.getExercises().size() > 0) {
            logTa.append("Exercises completed:\n");
            for (LogExercise e : viewed.getExercises()) {
                logTa.append(e.getExercise().getName() + " - " + e.getMinutes() + "\n");
            }
        }
        logTa.append("\n");
    }

    // complete
    private void viewLogs() {
        boolean keepGoing = true;
        while (keepGoing) {
            ArrayList<String> logDates = new ArrayList<String>();
            for (int i = 0; i < this.state.logs.size(); i++) {
                String logDate = this.state.logs.get(i).getDate().toString();
                logDates.add(logDate);
            }
            // convert ArrayList to array in order to use showInputDialog fxn
            Object[] logDatesArr = logDates.toArray();

            Object selected = JOptionPane.showInputDialog(null, "Which log would you like to view:", "Log Selection",
                    JOptionPane.DEFAULT_OPTION, null, logDatesArr, "0");
            if (selected != null) {// null if the user cancels.
                int index = logDates.indexOf(selected);
                Log viewingLog = this.state.logs.get(index);
                viewLog(viewingLog);
                return;
            } else {
                logTa.append("\nCancelled\n");
                return;

            }

        }
    }

    // complete
    private void setCalorieLimit() {
        try {
            double calories = Double.parseDouble(JOptionPane.showInputDialog(
                    "\nPlease enter your desired calorie limit.\n"));
            if (calories <= 0) {
                logTa.append("\nCalories cannot be set to " + calories + "\n");
                return;
            }

            wc.setCalorieLimit(logDay, calories);
            logTa.append("\nCalorie limit set to " + calories + "\n");
        } catch (Exception e) {
            logTa.append("\nCancelled.\n");
        }
    }

    private void viewCalorieLimit() {
        int logIndex = this.state.getLogIndexByDate(logDay);
        double calorieLimit;
        if (logIndex != -1) {
            calorieLimit = this.state.logs.get(logIndex).getCalories();
        } else {
            calorieLimit = this.state.getPreviousCalories(logDay);
        }
        logTa.append(String.format("Calorie Limit for %s - %3.2f\n", logDay.toString(), calorieLimit));
    }

    private void viewNetCalories() {
        double caloriesEaten = this.state.getConsumedCalories(logDay);
        double caloriesExpended = this.state.getExpendedCalories(logDay);
        double netCalories = this.state.getNetCalories(logDay);
        int logIndex = this.state.getLogIndexByDate(logDay);
        double calorieLimit;

        if (logIndex != -1) {
            calorieLimit = this.state.logs.get(logIndex).getCalories();
        } else {
            calorieLimit = this.state.getPreviousCalories(logDay);
        }

        String caloriesExceeded;

        if (netCalories > calorieLimit) {
            caloriesExceeded = "You exceeded your daily allowed calories";
        } else {
            caloriesExceeded = "You are below your daily allowed calories";
        }

        logTa.append(String.format(
                "\nCalorie information for %s:\nCalorie limit: %3.2f\nCalories eaten: %3.2f\nCalories expended: %3.2f\nNet calories: %3.2f\n%s\n",
                logDay.toString(), calorieLimit, caloriesEaten, caloriesExpended, netCalories, caloriesExceeded));
    }

    // complete
    private void addFood() {
        boolean addingFoods = true;

        ArrayList<LogFood> foodsAdded = new ArrayList<LogFood>();

        while (addingFoods) {
            int foodIndex = -1;
            try {
                String foodName = JOptionPane.showInputDialog(
                        "Add the name of the food you'd like to add to your log or enter '0' to finish adding foods:");

                if (foodName == null) {
                    logTa.append("\nCancelled.\n");
                    addingFoods = false;
                    return;
                }

                else if (foodName.equals("0") && foodsAdded.isEmpty()) {
                    addingFoods = false;
                    continue;
                }

                // If there are foods in the list to be added, they will be shown to the user
                if (foodName.equals("0") && !foodsAdded.isEmpty()) {
                    logTa.append("\nYou are adding the following foods to your daily log:\n");
                    for (LogFood food : foodsAdded) {
                        logTa.append(String.format("%n%s - %3.2f%n", food.getFood().getName(), food.getQuantity()));
                    }

                    int confirm = JOptionPane.showConfirmDialog(null, "Is this okay?\n(See text area)");
                    // 0=yes, 1=no, 2=cancel

                    // The user will be given a chance to confirm their entries. If they do not
                    // approve,
                    // the list is cleared.
                    if (confirm == 0) {
                        wc.logFood(logDay, foodsAdded);
                        addingFoods = false;
                        continue;
                    } else {
                        foodsAdded.clear();
                        logTa.append("\nAdded foods cleared.\n");
                        continue;
                    }
                }

                // Check if the food exists in the available food list
                for (int i = 0; i < this.state.food.size(); i++) {
                    if (this.state.food.get(i).getName().toLowerCase().equals(foodName.toLowerCase())) {
                        foodIndex = i;
                    }
                }

                if (foodIndex != -1) {
                    try {
                        double quantity = Double
                                .parseDouble(JOptionPane.showInputDialog("\nEnter the quantity of this food:\n"));

                        // double cannot be null, so it checks if it is automatically set to 0.0
                        if (quantity == 0.0) {
                            logTa.append("\nCancelled\n");
                            return;
                        } else if (quantity < 0) {
                            logTa.append("\nQuantity must be greater than 0.\n");
                            return;
                        } else {
                            foodsAdded.add(new LogFood(this.state.food.get(foodIndex), quantity));
                            logTa.append(String.format("%n%s added to staging list%n", foodName));
                            foodIndex = -1;
                            continue;
                        }
                    } catch (InputMismatchException ime) {
                        logTa.append("\nPlease enter a number\n");
                        continue;
                    }
                }

                logTa.append("\n" + foodName
                        + " does not currently exist. \nPlease view your foods/recipes or add the food.\n");
                return;

            } catch (Exception e) {
                logTa.append("\nCancelled.\n");
                return;
            }
        }
    }

    private void addExercise() {
        boolean addingExercises = true;

        ArrayList<LogExercise> exercisesAdded = new ArrayList<LogExercise>();

        while (addingExercises) {
            int exerciseIndex = -1;
            try {
                String exerciseName = JOptionPane.showInputDialog(
                        "Enter the name of the exercise you'd like to add to your log,\nor enter '0' to finish adding exercises:");

                // for cancel button
                if (exerciseName == null) {
                    logTa.append("\nCancelled.\n");
                    return;
                }

                else if (exerciseName.equals("0") && exercisesAdded.isEmpty()) {
                    addingExercises = false;
                    continue;
                }

                if (exerciseName.equals("0") && !exercisesAdded.isEmpty()) {
                    logTa.append("\nYou are adding the following exercises to your daily log:\n");
                    for (LogExercise exercise : exercisesAdded) {
                        logTa.append(String.format("%n%s - %3.2f%n", exercise.getExercise().getName(),
                                exercise.getMinutes()));
                    }

                    int confirm = JOptionPane.showConfirmDialog(null, "Is this okay?\n(See text area)");

                    if (confirm == 0) {
                        wc.logExercise(logDay, exercisesAdded);
                        addingExercises = false;
                        continue;
                    } else {
                        exercisesAdded.clear();
                        logTa.append("\nAdded exercises cleared.\n");
                        continue;
                    }
                }

                for (int i = 0; i < this.state.exercises.size(); i++) {
                    if (this.state.exercises.get(i).getName().toLowerCase().equals(exerciseName.toLowerCase())) {
                        exerciseIndex = i;
                        break;
                    }
                }

                if (exerciseIndex != -1) {
                    try {
                        double minutes = Double.parseDouble(JOptionPane
                                .showInputDialog("\nEnter the time (in minutes) you performed this exercise:\n"));

                        if (minutes == 0.0) {
                            logTa.append("\nCancelled.\n");
                            return;
                        }
                        exercisesAdded.add(new LogExercise(this.state.exercises.get(exerciseIndex), minutes));
                        logTa.append(String.format("%n%s added to staging list%n", exerciseName));
                        exerciseIndex = -1;
                        continue;
                    } catch (InputMismatchException ime) {
                        logTa.append("\nPlease enter a number\n");
                        continue;
                    }
                }

                logTa.append("\n" + exerciseName
                        + " does not currently exist. \nPlease view your exercises or add a new one.\n");

            } catch (Exception e) {
                logTa.append("\nCancelled.\n");
            }
        }
    }
}

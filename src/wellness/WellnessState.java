package wellness;
import java.util.*;
import java.time.*;

// This class serves as the state and contains the full list of foods, logs, andthe user's current progress
public class WellnessState {
    public ArrayList<Log> logs;
    public ArrayList<Food> food;
    public ArrayList<Exercise> exercises;
    public LocalDate activeDate;

    public WellnessState(ArrayList<Log> logs, ArrayList<Food> food, ArrayList<Exercise> exercises) {
        this.logs = logs;
        this.food = food;
        this.exercises = exercises;
        this.activeDate = LocalDate.now();
    }

    public WellnessState() {
        this.logs = new ArrayList<Log>();
        this.food = new ArrayList<Food>();
        this.exercises = new ArrayList<Exercise>();
        this.activeDate = LocalDate.now();
    }

    public void changeActiveDate(LocalDate date) {
        this.activeDate = date;
    }

    public double getCurrentCalorieGoal() {
        Collections.sort(this.logs, Collections.reverseOrder());
        double lastCalories = 2000.0;
        for (Log l : this.logs) {
            if (l.getWeight() > 0) {
                lastCalories = l.getCalories();
                break;
            }
        }

        return lastCalories;
    }

    public double getCurrentWeight() {
        Collections.sort(this.logs, Collections.reverseOrder());
        double lastWeight = 150.0;
        for (Log l : this.logs) {
            if (l.getWeight() > 0) {
                lastWeight = l.getWeight();
                break;
            }
        }

        return lastWeight;
    }

    // This takes in a date and returns the last entered weight from dates before it
    public double getPreviousWeight(LocalDate date) {
        Collections.sort(this.logs, Collections.reverseOrder());
        double lastWeight = 150.0;

        for (Log l : this.logs) {
            if (l.getDate().isBefore(date) && l.getWeight() > 0) {
                lastWeight = l.getWeight();
                break;
            }
        }

        return lastWeight;
    }

    // This takes in a date and returns the last entered weight from dates before it
    public double getPreviousCalories(LocalDate date) {
        Collections.sort(this.logs, Collections.reverseOrder());
        double lastCalories = 2000.0;

        for (Log l : this.logs) {
            if (l.getDate().isBefore(date) && l.getCalories() > 0) {
                lastCalories = l.getCalories();
                break;
            }
        }

        return lastCalories;
    }

    public int getLogIndexByDate(LocalDate date) {
        for (int i = 0; i < this.logs.size(); i++) {
            if (this.logs.get(i).getDate().isEqual(date)) {
                return i;
            }
        }
        return -1;
    }

    // Get a list of logs between two dates
    public ArrayList<Log> getLogs(LocalDate startDate, LocalDate endDate) {
        ArrayList<Log> returnedLogs = new ArrayList<Log>();

        for (Log log : logs) {
            // Check to see if the log is within the entered date range
            if (log.getDate().isEqual(startDate) || (log.getDate().isAfter(startDate) && log.getDate().isBefore(endDate)) || log.getDate().isEqual(endDate)) {
                returnedLogs.add(log);
            }
        }

        return returnedLogs;
    }

    // Get the consumed calories for a given date
    public double getConsumedCalories(LocalDate date) {
        int logIndex = getLogIndexByDate(date);

        if (logIndex != -1) {
            return this.logs.get(logIndex).getConsumedCalories();
        }

        return 0;
    }

    // Get the number of expended calories for a given date
    public double getExpendedCalories(LocalDate date) {
        int logIndex = getLogIndexByDate(date);

        if (logIndex != -1) {
            return this.logs.get(logIndex).getExpendedCalories();
        }

        return 0;
    }

    public double getNetCalories(LocalDate date) {
        int logIndex = getLogIndexByDate(date);

        if (logIndex != -1) {
            return this.logs.get(logIndex).getNetCalories();
        }

        return 0;
    }

    public double[] getNutritionPercentages(LocalDate date) {
        int logIndex = getLogIndexByDate(date);

        if (logIndex != -1) {
            return this.logs.get(logIndex).getNutritionPercentages();
        }

        return new double[] {0, 0, 0};
    }
}


package wellness;

public class WellnessManager {
    public WellnessManager() {
    }

    public static void main(String args[]) {
        WellnessMediator wm = new WellnessMediator("./src/wellness/foods.csv", "./src/wellness/log.csv",
                "./src/wellness/exercise.csv");
        WellnessController wc = new WellnessController(wm);
        ManagerCLI cli = new ManagerCLI(wm, wc);
        // LogDatesPanel ldp = new LogDatesPanel(wm);
        wm.loadData();
        cli.run();
        // ldp.run();

    }

}

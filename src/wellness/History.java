package wellness;
import java.util.*;

public class History {
    private ArrayList<Log> logs;

    public History(ArrayList<Log> logs) {
        this.logs = logs;
    }

    public History() {
        this.logs = new ArrayList<Log>();
    }

    public ArrayList<Log> getLogs() {
        return this.logs;
    }

    public void addLog(Log log) {
        this.logs.add(log);
    }

    public void addLogs(ArrayList<Log> logs) {
        this.logs.addAll(logs);
    }
}

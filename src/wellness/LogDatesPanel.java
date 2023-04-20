package wellness;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogDatesPanel extends JPanel implements Observer, ActionListener {
    private JFrame frame;
    private ArrayList<String> dateList;
    private ArrayList<Double> weights;
    private ArrayList<String> xAxisTitle;
    private JComboBox dropdown;
    private JComponent newContentPane;
    private WellnessMediator wm;
    private WellnessState state;
    private Object[] datesToArr;
    private double[] weightsToArr;
    private String[] titleToArr;
    private JLabel test;
    private JRadioButton weightB, calorieB, limitB;
    private ButtonGroup group;
    private JPanel radioPanel;
    private BarGraphCanvas bgc;
    
    public LogDatesPanel(WellnessMediator wm) {
        this.wm = wm;
        wm.addObserver(this);
        this.state = new WellnessState();
    }

    public void run() {
        // Create window
        frame = new JFrame("Graph");
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setLayout(new BorderLayout());
        // Content pane
        newContentPane = new LogDatesPanel(wm);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        canvas();
        optionButtons();
        datesDropdown();
        
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // radio buttons
    public void optionButtons() {
        weightB = new JRadioButton("Weight");
        weightB.setActionCommand("Weight");
        calorieB = new JRadioButton("Total Calories");
        calorieB.setActionCommand("Total Calories");
        limitB = new JRadioButton("Calorie Limit");
        limitB.setActionCommand("Calorie Limit");

        // Group radio buttons
        group = new ButtonGroup();
        group.add(weightB);
        group.add(calorieB);
        group.add(limitB);

        // Add listener
        /*weightB.addActionListener(this);
        calorieB.addActionListener(this);
        limitB.addActionListener(this);*/

        radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(weightB);
        radioPanel.add(calorieB);
        radioPanel.add(limitB);

        newContentPane.add(radioPanel, BorderLayout.LINE_START);
    }

    public void datesDropdown() {
        // adds date from log into arraylist
        dateList = new ArrayList<String>();
        for (int i = 0; i < this.state.logs.size(); i++) {
            String date = this.state.logs.get(i).getDate().toString();
            dateList.add(date);
        }
        // convert arraylist to array bc jcombobox only takes array
        datesToArr = dateList.toArray();
        dropdown = new JComboBox(datesToArr);  
        dropdown.addActionListener(this);
        dropdown.setPreferredSize(new Dimension(125, 20));
        test = new JLabel();
        newContentPane.add(dropdown, BorderLayout.PAGE_END);
    }

    public void canvas() {
        weights = new ArrayList<Double>();
        xAxisTitle = new ArrayList<String>();

        for (int i = 0; i < this.state.logs.size(); i++) {
            double w = this.state.logs.get(i).getWeight();
            weights.add(w);
        }
        for (int i = 0; i < this.state.logs.size(); i++) {
            String date = this.state.logs.get(i).getDate().toString();
            xAxisTitle.add(date);
        }
        weightsToArr = weights.stream().mapToDouble(Double::doubleValue).toArray();
        titleToArr = xAxisTitle.toArray(new String[0]);
        bgc = new BarGraphCanvas(weightsToArr, titleToArr, "Bar Graph");
        newContentPane.add(bgc, BorderLayout.PAGE_START);
    }

    public void update(Observable obs, Object payload) {
        WellnessState state = (WellnessState) payload;
        this.state = state;
    }

    public void updateGraph(String date) {
        test.setText(date);
        newContentPane.add(test, BorderLayout.PAGE_START);
    }

    public void actionPerformed(ActionEvent e) {
        dropdown = (JComboBox) e.getSource();
        String date = (String) dropdown.getSelectedItem();
        /*if (e.getActionCommand() == "Weight") {

        } 
        else if (e.getActionCommand() == "Total Calories") {

        } 
        else if (e.getActionCommand() == "Calorie Limit") {

        }*/

        updateGraph(date);
    }
}

package wellness;
import java.awt.Canvas ;
import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Graphics ;
import java.awt.Rectangle ;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;
import java.awt.*;

import java.util.Observer ;
import java.util.Observable ;

public class NewGraphCanvas extends Canvas implements Observer {
    private WellnessMediator wm;
    private WellnessState state;
    private final Integer values[] = {0, 0, 0};
    private static Font titleFont = new Font("SansSerif", Font.BOLD, 20);
    private static Font labelFont = new Font("SansSerif", Font.PLAIN, 10);

    public NewGraphCanvas(WellnessMediator wm) {
        this.setPreferredSize( new Dimension(values.length * 10, 300) );
        this.setMinimumSize( new Dimension(values.length * 2, 100) );
        this.wm = wm;
        this.state = new WellnessState();

        wm.addObserver(this);
        this.update(wm, this.state);
    }

    public void update(Observable obs, Object o) {
        WellnessState state = (WellnessState) o;
        this.state = state;

        LocalDate activeDate = this.state.activeDate;
        int logIndex = this.state.getLogIndexByDate(activeDate);

        if (logIndex != -1) {
            Log log = this.state.logs.get(logIndex);
            values[0] = (int) log.getFat();
            values[1] = (int) log.getCarbs();
            values[2] = (int) log.getProtein();
        } else {
            values[0] = 0;
            values[1] = 0;
            values[2] = 0;
        }

        this.repaint();
    }

    public void paint(Graphics g) {
        Rectangle bounds = g.getClipBounds() ;
        int w = (int) bounds.getWidth() ;
        int h = (int) bounds.getHeight() ;
        g.setFont(labelFont);

        int barWidth = w / values.length ;
        
        int max = Collections.max(Arrays.asList(values));

        for( int i = 0 ; i < values.length ; i++ ) {
            int intVal = (int) values[i];

            g.setPaintMode() ;
            int height;
            if (max > 0) {
                height = (int) h * intVal / max ;
            } else {
                height = (int) h * intVal / 100 ;
            }

            if (i == 0) {
                g.setColor(Color.RED) ;
            } else if (i == 1) {
                g.setColor(Color.GREEN);
            } else if (i == 2) {
                g.setColor(Color.BLUE);
            }

            g.fillRect(i*barWidth, h-height, barWidth, height) ;

            g.setColor(Color.BLACK);
            g.setFont(titleFont);
            if (i == 0) {
                g.drawString("Fat", i*barWidth, 20);
            } else if (i == 1) {
                g.drawString("Carbs", i*barWidth, 20);
            } else if (i == 2) {
                g.drawString("Protein", i*barWidth, 20);
            }
            g.setFont(labelFont);
            g.drawString(values[i].toString() + " grams", i*barWidth + (barWidth / 2), h / 2);


        }
    }
}

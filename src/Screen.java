import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import Models.*;
import Repository.CTPRepository;

public class Screen extends JFrame{
    private JPanel mainPanel;
    //private JFrame frame;

    private JPanel mapPanel;
    private JPanel infoPanel;
    private JPanel controlPanel;
    private JRadioButton radioButton1;
    private JButton button1;

    private final List<BusLine> buslines;
    private final List<BusStop> busstops;

    public Screen() {
        Map map = new Map();
        mapPanel.add(map);

        /// -------------------------------------

        CTPRepository repo = new CTPRepository();
        buslines = repo.getAllLines();
        busstops = repo.getAllStops();

        /// -------------------------------------

        map.setStops(loadLine(buslines.get(0), false), true);
        mapPanel.repaint();

        /// -------------------------------------
        setContentPane(mainPanel);
        pack();

        setTitle("Application");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mapPanel = new JPanel();
        infoPanel = new JPanel();
        controlPanel = new JPanel();
    }

    private List<BusStop> loadLine(BusLine line, boolean isForward) {
        List<BusStop> route = new ArrayList<>();
        if(isForward) {
            for (int stopId : line.stopsForward) {
                route.add(busstops.get(stopId));
            }
        } else {
            for (int stopId : line.stopsBackward) {
                route.add(busstops.get(stopId));
            }
        }
        return route;
    }
}

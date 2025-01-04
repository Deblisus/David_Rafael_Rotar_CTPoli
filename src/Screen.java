import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import Models.*;
import Repository.CTPRepository;

public class Screen extends JFrame{
    private JPanel mainPanel;
    //private JFrame frame;

    private JPanel mapPanel;
    private JPanel infoPanel;
    private JPanel controlPanel;
    private JRadioButton radioButton1;
    private JList lineList;

    private final List<BusLine> buslines;
    private Map<String, BusLine> busLineMap;
    private final List<BusStop> busstops;

    public Screen() {
        GMap map = new GMap();
        mapPanel.add(map);

        /// -------------------------------------

        CTPRepository repo = new CTPRepository();
        buslines = repo.getAllLines();
        busLineMap = repo.getAllLineMap();
        busstops = repo.getAllStops();

        /// -------------------------------------

        populateLineList();

        map.setStops(loadLine(busLineMap.get("10"), false), true);
        //map.setStops(busstops, true);

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

    private void populateLineList() {
        String[] namesArray = buslines.stream().map(BusLine::getNumber).toArray(String[]::new);
        lineList.setListData(namesArray);
    }

    private List<BusStop> loadLine(BusLine line, boolean isForward) {
        List<BusStop> route = new ArrayList<>();
        if(isForward) {
            for (int stopId : line.stopsForward) {
                route.add(busstops.get(stopId-1));
                System.out.println(busstops.get(stopId-1).name);
            }
        } else {
            for (int stopId : line.stopsBackward) {
                route.add(busstops.get(stopId-1));
                System.out.println(busstops.get(stopId-1).name);
            }
        }
        return route;
    }
}

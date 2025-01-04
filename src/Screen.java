import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JCheckBox directionBox;
    private JButton allStops;

    private final List<BusLine> buslines;
    private Map<String, BusLine> busLineMap;
    private final List<BusStop> busstops;

    private GMap map;
    private boolean lineDirection = false;

    public Screen() {
        map = new GMap();
        mapPanel.add(map);

        /// -------------------------------------

        CTPRepository repo = new CTPRepository();
        buslines = repo.getAllLines();
        busLineMap = repo.getAllLineMap();
        busstops = repo.getAllStops();

        /// -------------------------------------

        populateLineList();
        lineList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedLine();
            }
        });
        directionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDirection();
            }
        });
        allStops.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllStops();
            }
        });
        map.setStops(busstops, false);

        /// -------------------------------------

        setContentPane(mainPanel);
        pack();

        setTitle("Application");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void selectedLine() {
        if(lineList.getSelectedValue() != null) {
            String lineSelected = lineList.getSelectedValue().toString();

            map.setStops(loadLine(busLineMap.get(lineSelected), lineDirection), true);
            mapPanel.repaint();
        }
    }

    private void selectedDirection() {
        if(!lineList.isSelectionEmpty()) {
            String lineSelected = lineList.getSelectedValue().toString();
        }

        lineDirection = directionBox.isSelected();

        if(!lineList.isSelectionEmpty()) {
            selectedLine();
            mapPanel.repaint();
        }
    }

    private void showAllStops() {
        lineList.clearSelection();

        map.setStops(busstops, false);
        mapPanel.repaint();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mapPanel = new JPanel();
        infoPanel = new JPanel();
        controlPanel = new JPanel();
    }

    private void populateLineList() {
        /// Only line with chatgpt I think.
        /// Just gets the line numbers from buslines and puts them in another array
        String[] namesArray = buslines.stream().map(BusLine::getNumber).toArray(String[]::new);
        lineList.setListData(namesArray);
    }

    private List<BusStop> loadLine(BusLine line, boolean isForward) {
        List<BusStop> route = new ArrayList<>();
        if(isForward) {
            for (int stopId : line.stopsForward) {
                route.add(busstops.get(stopId-1));
                //System.out.println(busstops.get(stopId-1).name);
            }
        } else {
            for (int stopId : line.stopsBackward) {
                route.add(busstops.get(stopId-1));
                //System.out.println(busstops.get(stopId-1).name);
            }
        }
        return route;
    }
}

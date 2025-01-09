package Panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import Models.*;
import Repository.CTPRepository;

/**
 * Main screen of the application
 */
public class Screen extends JFrame{
    private JPanel mainPanel;
    //private JFrame frame;

    private JPanel mapPanel;
    private JPanel infoPanel;
    private JPanel controlPanel;
    private JList lineList;
    private JCheckBox directionBox;
    private JButton allStops;
    private JTable stopsTable;
    private JScrollPane stopsScrollPane;
    private JButton logOutButton;

    private Login loginPage;

    private final List<BusLine> buslines;
    private Map<String, BusLine> busLineMap;
    private final List<BusStop> busstops;

    private List<BusStop> coloredStops;

    private GMap map;
    private boolean lineDirection = false;

    public Screen() {
        map = new GMap();
        mapPanel.add(map);

        loginPage = new Login();

        /// -------------------------------------
        ///  Load the Data into the app
        ///  buslines saved as list for the selection list on the screen
        ///  busLineMap the dictionary/hashmap to access the bus lines
        ///  busstops where all the stops are saved
        ///
        ///  auxiliary coloredStops for highlighting with red the selected stops of a line

        CTPRepository repo = new CTPRepository();
        buslines = repo.getAllLines();
        busLineMap = repo.getAllLineMap();
        busstops = repo.getAllStops();

        coloredStops = new ArrayList<>();

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
        stopsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow();
            }
        });

        loginPage.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginPage.tryLogin()){
                    setContentPane(mainPanel);
                    pack();
                    setSize(1280, 720);
                }
            }
        });
        loginPage.registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginPage.tryRegister()){
                    setContentPane(mainPanel);
                    pack();
                    setSize(1280, 720);
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(loginPage.getPanel());
                pack();
                setSize(1280, 720);
            }
        });
        ///  Initialize the map with all the stops shown and no highlights
        map.setStops(busstops, false);
        map.setColored(coloredStops);

        /// -------------------------------------
        /// Frame configuration

        setContentPane(loginPage.getPanel());
        pack();

        setTitle("CTPoli");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        mapPanel = new JPanel();
    }

    private void selectedLine() {
        ///  Show a bus line on screen
        ///  Get the selected route and load it into the map, then repaint
        if(lineList.getSelectedValue() != null) {
            String lineSelected = lineList.getSelectedValue().toString();

            map.setStops(loadLine(busLineMap.get(lineSelected)), true);
            mapPanel.repaint();

            loadStations(busLineMap.get(lineSelected));
        }
    }

    private void selectedDirection() {
        ///  Updates the map according to lineDirection
        ///  Routes can be either forward or backward
        lineDirection = directionBox.isSelected();

        if(!lineList.isSelectionEmpty()) {
            selectedLine();
            mapPanel.repaint();
        }
    }

    private void showAllStops() {
        ///  Resets the map and shows all the stops, no highlights and no lines
        lineList.clearSelection();

        coloredStops.clear();
        map.setColored(coloredStops);
        map.setStops(busstops, false);
        mapPanel.repaint();

        DefaultTableModel model = (DefaultTableModel) stopsTable.getModel();
        model.setRowCount(0);
    }

    private void loadStations(BusLine line) {
        ///  Updates the Table which displays the route with the names
        ///  of the stops and the lines that pass through it
        String[][] data;
        List<Integer> stopList;

        if(lineDirection) {
            stopList = line.stopsForward;
        } else {
            stopList = line.stopsBackward;
        }

        data = new String[stopList.size()][2];
        for(int i = 0; i < stopList.size(); i++) {
            BusStop stop = busstops.get(stopList.get(i)-1);

            data[i][0] = stop.name;

            String passingLines = new String();
            for(String passingLine : stop.lines) {
                passingLines += passingLine;
                passingLines += ",";
            }

            data[i][1] = passingLines;
        }
        String[] columns = {"Stop Name", "Lines"};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        stopsTable.setModel(model);
    }

    private void selectedRow() {
        ///  creates an array of selected stops from the table and updates the map so it highlights them
        int[] selectedRow = stopsTable.getSelectedRows();
        coloredStops.clear();
        if(!lineList.isSelectionEmpty()) {
            String lineSelected = lineList.getSelectedValue().toString();
            List<Integer> stopList;
            if(lineDirection) {
                stopList = busLineMap.get(lineSelected).stopsForward;
            } else {
                stopList = busLineMap.get(lineSelected).stopsBackward;
            }
            for (int i : selectedRow) {
                BusStop stop = busstops.get(stopList.get(i)-1);
                coloredStops.add(stop);
            }
            map.setColored(coloredStops);
            mapPanel.repaint();
        }
    }

    private void populateLineList() {
        /// Only line with chatgpt I think.
        /// Just gets the line numbers from buslines and puts them in another array
        String[] namesArray = buslines.stream().map(BusLine::getNumber).toArray(String[]::new);
        lineList.setListData(namesArray);
    }

    private List<BusStop> loadLine(BusLine line) {
        ///  Returns the specific route of a line and the direction  of the route
        List<BusStop> route = new ArrayList<>();
        if(lineDirection) {
            for (int stopId : line.stopsForward) {
                route.add(busstops.get(stopId-1));
            }
        } else {
            for (int stopId : line.stopsBackward) {
                route.add(busstops.get(stopId-1));
            }
        }
        return route;
    }
}

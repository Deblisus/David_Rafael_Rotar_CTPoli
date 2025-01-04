package Repository;

import Models.BusLine;
import Models.BusStop;
import Connection.ConnectionFactory;

import Utility.CSVParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CTPRepository {
    private final Connection connection;

    public CTPRepository() {
        this.connection = ConnectionFactory.getConnection();
    }

    public List<BusLine> getAllLines() {
        List<BusLine> BusLineList = new ArrayList<>();
        String sql = "SELECT * FROM lines ORDER BY length(number), number";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String number = rs.getString("number");
                String stopsForward = rs.getString("stopsforward");
                String stopsBackward = rs.getString("stopsbackward");

                ArrayList<Integer> stopsFw = CSVParser.processStops(stopsForward);
                ArrayList<Integer> stopsBw = CSVParser.processStops(stopsBackward);

                BusLine busline = new BusLine(number, stopsFw, stopsBw);
                BusLineList.add(busline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BusLineList;
    }

    public Map<String, BusLine> getAllLineMap() {
        Map<String, BusLine> BusLineMap = new HashMap<>();
        String sql = "SELECT * FROM lines";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String number = rs.getString("number");
                String stopsForward = rs.getString("stopsforward");
                String stopsBackward = rs.getString("stopsbackward");

                ArrayList<Integer> stopsFw = CSVParser.processStops(stopsForward);
                ArrayList<Integer> stopsBw = CSVParser.processStops(stopsBackward);

                BusLine busline = new BusLine(number, stopsFw, stopsBw);
                BusLineMap.put(number, busline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BusLineMap;
    }

    public List<BusStop> getAllStops() {
        List<BusStop> BusStopList = new ArrayList<>();
        String sql = "SELECT * FROM stops ORDER BY id";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                double lat = rs.getDouble("latitude");
                double lng = rs.getDouble("longitude");
                String lines = rs.getString("lines");

                ArrayList<String> lineList = CSVParser.processLines(lines);

                BusStop busstop = new BusStop(name, lat, lng, lineList);
                BusStopList.add(busstop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BusStopList;
    }
}

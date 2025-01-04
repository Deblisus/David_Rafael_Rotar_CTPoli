package Models;

import java.util.ArrayList;

/**
 * BusStop class that models a real bus stop, with its respective name, coordinates on the globe and the lines that visit it
 */
public class BusStop {
    public String name;
    public double lat;
    public double lng;
    public ArrayList<String> lines;

    public BusStop(String name, double lat, double lng, ArrayList<String> lines) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.lines = lines;
    }
}

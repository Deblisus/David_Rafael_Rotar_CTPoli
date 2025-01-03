package Models;

import java.util.ArrayList;

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

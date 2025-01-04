package Models;

import java.util.ArrayList;

/**
 * BusLine class, used to model a bus line, with its respective number and the series of stops in its route
 */
public class BusLine {
    public String number;
    public ArrayList<Integer> stopsForward;
    public ArrayList<Integer> stopsBackward;

    public BusLine(String number, ArrayList<Integer> stopsForward, ArrayList<Integer> stopsBackward) {
        this.number = number;
        this.stopsForward = stopsForward;
        this.stopsBackward = stopsBackward;
    }

    public String getNumber() {
        return number;
    }
}

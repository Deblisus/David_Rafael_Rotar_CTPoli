package Models;

import java.util.ArrayList;

public class BusLine {
    public String number;
    public ArrayList<Integer> stopsForward;
    public ArrayList<Integer> stopsBackward;

    public BusLine(String number, ArrayList<Integer> stopsForward, ArrayList<Integer> stopsBackward) {
        this.number = number;
        this.stopsForward = stopsForward;
        this.stopsBackward = stopsBackward;
    }
}

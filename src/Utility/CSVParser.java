package Utility;

import java.util.ArrayList;
import java.util.Arrays;

public class CSVParser {
    public static ArrayList<Integer> processStops(String stops){
        ArrayList<Integer> rez = new ArrayList<>();
        String[] stopList = stops.split(",");
        for(String stop : stopList){
            if(stop != null && !stop.isEmpty()){
                rez.add(Integer.parseInt(stop));
            }
        }
        return rez;
    }

    public static ArrayList<String> processLines(String lines){
        String[] lineList = lines.split(",");

        return new ArrayList<>(Arrays.asList(lineList));
    }
}

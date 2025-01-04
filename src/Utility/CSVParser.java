package Utility;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used for parsing the lists of stops and bus lines from string into lists
 */
public class CSVParser {
    public static ArrayList<Integer> processStops(String stops){
        /// Function that receives a string of stops separated by commas, and returns an array with the integer values
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
        /// Function that receives a string of line numbers in CSV format and return an array of the separated line numbers
        String[] lineList = lines.split(",");

        return new ArrayList<>(Arrays.asList(lineList));
    }
}

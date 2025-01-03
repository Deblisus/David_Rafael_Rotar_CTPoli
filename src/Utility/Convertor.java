package Utility;

import java.awt.*;

public class Convertor {
    public int width = 640;
    public int height = 480;
    private double scale = 1;
    public double ratio;

    private double lat_diff;
    private double lng_diff;
    private double point0_lat;
    private double point0_lng;

    public static int TILE_SIZE = 256; // 256
    public static int zoom = 12;

    public static Point getCoord(double lat, double lng){
        double scale = 1 << zoom;
        Point worldCoord = project(lat, lng);
        return new Point( (int)(Math.floor((worldCoord.x * scale) / (double)TILE_SIZE)), (int)(Math.floor((worldCoord.y) * scale) / (double)TILE_SIZE));
    }

    public static Point project(double lat, double lng){
        double siny = Math.sin((lat * Math.PI) / 180);

        siny = Math.min(Math.max(siny, -0.9999), 0.9999);
        return new Point( (int)(TILE_SIZE * (0.5 + lng / 360)), (int)(TILE_SIZE * (0.5 - Math.log((1+siny)/(1-siny))/(4*Math.PI))));
    }

}

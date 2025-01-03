package Utility;

import java.awt.*;

public class Projector {
    private  int width = 640;
    private  int height = 480;
    private  double scale = 2;

    private final double lat_diff;
    private final double lng_diff;
    private final double point0_lat;
    private final double point0_lng;

    public Projector(int width, int height, double scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;

        double center_coord_lat = 46.775523;
        double center_coord_lng = 23.588079;

        double left_coord_lat = 46.751994;
        double left_coord_lng = 23.477894;

        double bottom_coord_lat = 46.71886929842522;
        double bottom_coord_lng = 23.620448813287563;

        lng_diff = (center_coord_lng - left_coord_lng)*2;
        lat_diff = (center_coord_lat - bottom_coord_lat)*2;

        point0_lng = center_coord_lng - (lng_diff/2);
        point0_lat = center_coord_lat + (lat_diff/2);
    }

    public Point project(double lat, double lng) {
        //f(x) = (x - input_start) / (input_end - input_start) * (output_end - output_start) + output_start
        double latFin = (lat - (point0_lat-lat_diff)) / (lat_diff) * (height - 0) + 0;
        ///  Take complement to height because pixel coordinates go top-down, but latitude
        ///  values go South-North, so bottom-up
        latFin = height-latFin;
        double lngFin = (lng - point0_lng) / (lng_diff) * (width - 0) + 0;
        return new Point((int)(lngFin*scale), (int)(latFin*scale));
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
}

package Panels;

import Models.BusStop;
import Models.Point;
import Utility.Projector;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class GMap extends JPanel{
    private int width = 640;
    private int height = 480;
    private double scale = 1;

    private JLabel image_map;
    private ImageIcon image_icon;

    private List<BusStop> showStops;
    private Boolean drawLines = false;
    private Projector projector;
    private List<BusStop> coloredStops;

    //public BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    public GMap() {
        ///  Loads the image and the projector and size configuration
        image_icon = new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance((int)(width*scale), (int)(height*scale),
                java.awt.Image.SCALE_SMOOTH));
        image_map = new JLabel(image_icon);
        setPreferredSize(new Dimension((int)(width*scale), (int)(height*scale)));

        projector = new Projector(width, height, scale);
    }

    public void setStops(List<BusStop> stops, boolean drawLines) {
        ///  Updates the stops to be displayed on the map
        ///  drawLines is whether it should draw lines between ordered stops or not
        showStops = stops;
        this.drawLines = drawLines;
    }

    public void setColored(List<BusStop> coloredStops) {
        ///  Updates the colored/highlighted stops to be shown on a route
        this.coloredStops = coloredStops;
    }

    private void paintDot(Graphics g, Point mid, String color) {
        ///  Paints a dot on the map, representing a stop
        int size1 = 12;
        int size2 = 8;
        Color darkGreen = new Color(0, 96, 0);
        Color lightGreen = new Color(0, 200, 0);
        Color darkRed = new Color(200, 0, 0);
        Color lightRed = new Color(96, 0, 0);

        if(color.equals("Green")) g.setColor(darkGreen);
        if(color.equals("Red")) g.setColor(darkRed);
        g.fillOval(mid.x-size1/2, mid.y-size1/2, size1, size1);
        if(color.equals("Green")) g.setColor(lightGreen);
        if(color.equals("Red")) g.setColor(lightRed);
        g.fillOval(mid.x-size2/2, mid.y-size2/2, size2, size2);
    }

    @Override
    public void paintComponent(Graphics g) {
        ///  paints the Map.
        image_icon.paintIcon(this, g, 0, 0);

        int count = 0;
        Point prev = null;
        for(BusStop stop : showStops) {
            Point pnt = projector.project(stop.lat, stop.lng);

            if(count > 0 && drawLines) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.orange);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(pnt.x, pnt.y, prev.x, prev.y);
            }
            paintDot(g, pnt, "Green");
            if(prev != null) paintDot(g, prev, "Green");

            prev = pnt;
            count++;
        }

        for (BusStop stop : coloredStops) {
            Point pnt = projector.project(stop.lat, stop.lng);
            paintDot(g, pnt, "Red");
        }
    }

    public ImageIcon getMapImage() {
        return image_icon;
    }

    public JLabel getImage_map() {
        return image_map;
    }

    /*
    public static void downloadFromAPI() {
        try {
            //String imageUrl = "http://maps.googleapis.com/maps/api/staticmap?center=40,26?zoom=1&size=150x112&maptype=satellite&key=&format=jpg";
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=46.775523,23.588079&zoom=12&size=640x480&scale=2&key=";
            String destinationFile = "t.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    */
}

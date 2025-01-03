import Models.BusStop;
import Utility.Projector;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.Point;

public class Map extends JPanel{
    public int width = 640;
    public int height = 480;
    private double scale = 1.5;

    private JLabel image_map;
    private ImageIcon image_icon;

    private List<BusStop> showStops;
    private Boolean drawLines = false;
    private Projector projector;

    //public BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    public Map() {
        image_icon = new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance((int)(width*scale), (int)(height*scale),
                java.awt.Image.SCALE_SMOOTH));
        image_map = new JLabel(image_icon);
        setPreferredSize(new Dimension((int)(width*scale), (int)(height*scale)));

        projector = new Projector(width, height, scale);
        //add(image_map);
    }

    public void setStops(List<BusStop> stops, boolean drawLines) {
        showStops = stops;
        this.drawLines = drawLines;
    }

    private void paintDot(Graphics g, Point mid) {
        int size1 = 12;
        int size2 = 8;
        Color darkGreen = new Color(0, 96, 0);
        Color lightGreen = new Color(0, 200, 0);

        g.setColor(darkGreen);
        g.fillOval(mid.x-size1/2, mid.y-size1/2, size1, size1);
        g.setColor(lightGreen);
        g.fillOval(mid.x-size2/2, mid.y-size2/2, size2, size2);
    }

    private Point mid = new Point(width/2, height/2);
    @Override
    public void paintComponent(Graphics g) {
        image_icon.paintIcon(this, g, 0, 0);
        //Point mid = new Point(width/2, height/2);

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
            paintDot(g, pnt);
            if(prev != null) paintDot(g, prev);

            prev = pnt;
            count++;
        }

        //g.fillOval(100, 100, 100, 100);
    }

    public void setDot(int x, int y) {
        mid.x = x;
        mid.y = y;
    }

    public ImageIcon getMapImage() {
        return image_icon;
    }

    public JLabel getImage_map() {
        return image_map;
    }


    public static void downloadFromAPI() {
        /*
        try {
            //String imageUrl = "http://maps.googleapis.com/maps/api/staticmap?center=40,26?zoom=1&size=150x112&maptype=satellite&key=AIzaSyCB6XMtzaAS0c9EUsGp8sU4lYI4cxNXIYE&format=jpg&signature=mjuJW9IfSGD_bvhaeol-PBmy0mE=";
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=46.775523,23.588079&zoom=12&size=640x480&scale=2&key=AIzaSyCB6XMtzaAS0c9EUsGp8sU4lYI4cxNXIYE";
            String destinationFile = "t.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            /// center coords: 46.775523,23.588079
            /// left edge coords: 46.751994, 23.477894

            /// x center: width/2 ........... center.x - left edge coords
            /// wanted pos:     x ...........
            ///  what the frick

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
        */
    }
}

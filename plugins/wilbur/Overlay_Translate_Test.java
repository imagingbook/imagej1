package plugins.wilbur;

import java.awt.Color;
import java.awt.geom.Path2D;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Line;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.gui.ShapeRoi;
import ij.plugin.PlugIn;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

/**
 *
 * @author W. Burger
 */
public class Overlay_Translate_Test implements PlugIn {

    ImageProcessor ip = null;

    @Override
    public void run(String arg) {

        double x1 = 1.0;
        double y1 = 1.0;
        double x2 = 1.0;
        double y2 = 2.0;

        ip = new ByteProcessor(24, 24);
        makeCheckerBoard(ip);

        ImagePlus im = new ImagePlus("TestImage", ip);
        
        Overlay oly = new Overlay();
        
        Roi line1 = new Line(1.5, 1.5, 1.5, 2.5);
        line1.setStrokeColor(Color.green);
        line1.enableSubPixelResolution();
        oly.add(line1);
        
        Roi line2 = new Line(3.0, 2.0, 4.0, 5.0);
        line2.setStrokeColor(Color.red);
        line2.enableSubPixelResolution();
        oly.add(line2);
        
        Path2D path = new Path2D.Double();
		path.moveTo(6, 2);
		path.lineTo(7, 5);
		ShapeRoi line3 = new ShapeRoi(path);
		line3.setStrokeColor(Color.blue);
		line3.enableSubPixelResolution();
        oly.add(line3);
        
        //Roi circle = new Oval(3.0, 2.0, 4.0, 5.0);

        oly.translate(0.5, 0.5);
        im.setOverlay(oly);
        im.show();
    }

    // -------------------------------------------------
    void makeCheckerBoard(ImageProcessor ip) {
        int w = ip.getWidth();
        int h = ip.getHeight();

        for (int u = 0; u < w; u++) {
            int val = u % 2;
            for (int v = 0; v < h; v++) {
                ip.putPixel(u, v, val * 255);
                val = (val + 1) % 2;
            }
        }
    }

}

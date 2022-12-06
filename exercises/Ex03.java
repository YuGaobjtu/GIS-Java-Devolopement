package Exercises;
import java.awt.*;
import javax.swing.*;

import GUI.components.MapPanel;
import ag2411.mapalgebra.Layer;


    // input:
    // example: localSum 4 .\data\prod\development.txt .\data\prod\hydrology.txt
    // example: zonalMinimum 4 .\data\prod\elevation.txt .\data\prod\vegetation.txt
    // example: focalVariety 4 .\data\prod\elevation.txt 1 false


public class Ex03 {
    public static void main(String[] args) {
        // operation skala indatafil (radie F) (fil 2 LZ) (square nbh F)
        String operation = args[0];
        int scale = Integer.parseInt(args[1]);
        Layer inLayer1 = new Layer("in1", args[2]);

        Layer outLayer = null;
        if (operation.equals("localSum")) {
            Layer inLayer2 = new Layer("in2", args[3]);
            outLayer = inLayer1.localSum(inLayer2, "localoutput");
                // save and visualize the output layer
            }
        else if (operation.equals("focalVariety")) {
            // perform focalVariety and save & visualize the output layer
            int r = Integer.parseInt(args[3]);
            boolean isSquare = Boolean.parseBoolean(args[4]);
            outLayer = inLayer1.focalVariety(r, isSquare, "focaloutput");
        }
        else if (operation.equals("zonalMinimum")) {
            // perform zonalMinimum and save & visualize the output layer
            Layer inLayer2 = new Layer("in2", args[3]);
            outLayer = inLayer1.zonalMin(inLayer2, "zonaloutput");
        }
        else {
            System.out.print(operation + " is not currently available.");
        }


        JFrame appFrame = new JFrame();
        MapPanel map = new MapPanel(outLayer.toImage(), scale);
        Dimension dimension = new Dimension(scale * outLayer.nCols, scale * outLayer.nRows);
        map.setPreferredSize(dimension);

        appFrame.add(map);

        appFrame.pack();
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setVisible(true);
    }
}
package kth.ag2411.mapalgebra;

import java.util.Arrays;
import javax.swing.*;
import kth.ag2411.mapalgebra.*;

public class Ex02 { 
    public static void main(String[] args) { 
        String input =args[1];
        String inputname =input;
        //Jframe 1
        Layer layer = new Layer(args[0], inputname); 
        JFrame appFrame=new JFrame();
        String arg4=args[3];
        String[] input1=arg4.split(",");//305.7144,333.756,290.1696
        double[] input2 = Arrays.stream(input1).mapToDouble(Double::parseDouble).toArray();
        int scale= Integer.parseInt(args[2]);;
        MapPanel mp= new MapPanel(layer.toImage(input2),scale);
        appFrame.add(mp);
        appFrame.pack();
        appFrame.setVisible(true);
        appFrame.setExtendedState(appFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        //Jframe 2
        Layer layer2 = new Layer(args[0], inputname);
        JFrame appFrame2=new JFrame();
        MapPanel mp2= new MapPanel(layer2.toImage(),scale);
        appFrame2.add(mp2);
        appFrame2.pack();
        appFrame2.setVisible(true);
        appFrame2.setExtendedState(appFrame2.getExtendedState()|JFrame.MAXIMIZED_BOTH);
    }
}
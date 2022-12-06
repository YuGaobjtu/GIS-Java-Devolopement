package Exercises;

import javax.swing.JFrame;

import ag2411.mapalgebra.Layer;
import GUI.components.MapPanel;

public class Ex02 {
	public static void main(String[] args) {
		if(args.length == 3) {
			//Instantiate a layer
			Layer layer = new Layer(args[0], args[1]);	// input arguments from terminal, layername "space" filename (with sufix)
			
			// ----- FROM EX01 -----
			//Printing it on the console
			//layer.print();
			//Saving it to the file output.txt
			//layer.save(args[2]);
			// ----- FROM EX01 -----
			
			String scaleString = args[2];
			int scaleInt;
			scaleInt = Integer.parseInt(scaleString);
			
			
			JFrame appFrame = new JFrame();
			MapPanel mp = new MapPanel(layer.toImage(), scaleInt);
			appFrame.add(mp);
			appFrame.setVisible(true);
			appFrame.setExtendedState(appFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		}
		else if(args.length > 3) {
			// ----- Read args[3] and turn into double []
			
			//Instantiate a layer
			Layer layer = new Layer(args[0], args[1]);	// input arguments from terminal, layername "space" filename (with sufix)
			
			String scaleString = args[2];
			int scaleInt;
			scaleInt = Integer.parseInt(scaleString);
			
			
			double[] vois = new double [args.length-3];
			for (int i = 0; i < args.length-3; i++) {
				vois[i] = Double.parseDouble(args[i+3]);
			}
			
			JFrame appFrame = new JFrame();
			MapPanel mp = new MapPanel(layer.toImage(vois), scaleInt);		//args2 h�r
			appFrame.add(mp);
			appFrame.setVisible(true);
			appFrame.setExtendedState(appFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
			
			JFrame appFrame1 = new JFrame();
			MapPanel mp1 = new MapPanel(layer.toImage(), scaleInt);		//args2 h�r
			appFrame1.add(mp1);
			appFrame1.setVisible(true);
			appFrame1.setExtendedState(appFrame1.getExtendedState()|JFrame.MAXIMIZED_BOTH);
			
			
		}
		else {
			System.out.println("Too many or few arguments......");
		}
	}
}

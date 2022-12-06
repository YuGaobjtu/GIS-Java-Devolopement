package kth.ag2411.project.components;

import javax.swing.*;

import kth.ag2411.project.App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//HOWTO add a new tool to the toolbox:
//1: Add 'static final private String TOOLHANDLE = "toolhandle";' to the proerties of this clas. 
//      note that the variable name TOOLHANDLE should be in caps and the value should be a minor case string. 
//2: Create a button for the tool by writing 'JButton toolName = makeToolButton(TOOLHANDLE, "Tool Name");'
//      "Tool Name" is the string that is displayed on the button. toolName is the variable containing the button
//3: Display the new button by writing 'add(toolName);' under the correct headline. 
//4: Add the tool to the tool dialog by following instructions in ToolDialog.java. 

public class ToolBox extends JToolBar
                     implements ActionListener{
    static final public Color BG = new Color(210,210,210);                    
    static final public String ZONAL_MIN = "zonalMin";
    static final public String ZONAL_MAX = "zonalMax";
    static final public String ZONAL_AVERAGE = "zonalAverage";
    static final public String SLOPE = "slope";
    static final public String ASPECT = "aspect";
    static final public String LOCAL_SUM = "localSum";
    static final public String LOCAL_DIFF = "localDifference";
    static final public String LOCAL_DIV = "localDivision";
    static final public String LOCAL_PROD = "localProduct";
    static final public String UNIFORM_LAYER = "newUniformLayer";


    public ToolBox(){
        super();

        setBackground(BG);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        //Create buttons for the tool here
        JButton zonalMin = makeToolButton(ZONAL_MIN, "zonal minimum");
        JButton zonalMax = makeToolButton(ZONAL_MAX, "zonal maximum");
        JButton zonalAverage = makeToolButton(ZONAL_AVERAGE, "zonal average");
        JButton slope = makeToolButton(SLOPE, "Slope");
        JButton aspect = makeToolButton(ASPECT, "Aspect");
        JButton localSum = makeToolButton(LOCAL_SUM, "Local Sum");
        JButton localDiff = makeToolButton(LOCAL_DIFF, "Local Difference");
        JButton localDiv = makeToolButton(LOCAL_DIV, "Local Division");
        JButton localProd = makeToolButton(LOCAL_PROD, "Local Product");
        JButton newLayer = makeToolButton(UNIFORM_LAYER, "Create uniform layer");

        JLabel header = new JLabel("Tool box");
        header.setFont(App.H1);
        JLabel headerZonal = new JLabel("Zonal Operations");
        headerZonal.setFont(App.H2);
        JLabel headerFocal = new JLabel("Focal Operations");
        headerFocal.setFont(App.H2);
        JLabel headerLocal = new JLabel("Local Operations");
        headerLocal.setFont(App.H2);
        JLabel headerNetwork = new JLabel("Network Operations");
        headerNetwork.setFont(App.H2);
        JLabel headerCreate = new JLabel("Create Layer");
        headerCreate.setFont(App.H2);




        add(header);
        add(headerZonal);
        //Add zonal tools here
        add(zonalMin);
        add(zonalMax);
        add(zonalAverage);
        add(headerFocal);
        //Add Focal tools here
        add(slope);
        add(aspect);
        add(headerLocal);
        //Add Local tools here
        add(localSum);
        add(localDiff);
        add(localDiv);
        add(localProd);
        /*  //Network and create tools not implemented for version 1.0
        add(headerNetwork);
        //Add network tools here
        add(headerCreate);
        add(newLayer);
        */
    }

    protected JButton makeToolButton(String actionCommand, String name) {

        //Create and initialize the button.
        JButton button = new JButton(name);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        return button;
}
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        System.out.println("Tool button pressed: "+cmd);

        ToolDialog td = new ToolDialog(cmd);
        td.setVisible(true);
    }
    
}

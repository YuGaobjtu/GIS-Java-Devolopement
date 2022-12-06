package kth.ag2411.project.components;

import kth.ag2411.project.mapalgebra.*;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.*;

import kth.ag2411.project.App;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolDialog extends JFrame
                        implements ActionListener {
    private static final String OK = "ok";
    private static final String CANCEL = "cancel";
    private static final String CHOOSE_FILE1 = "choose1";
    private static final String CHOOSE_FILE2 = "choose2";
    private static final String SAVE_LOC = "save";
    private final String OPERATION;
    private JLabel input1;
    private JLabel input2;
    private JLabel outputLoc;
    private JPanel panel;
    private Layer inLayer1;
    private Layer inLayer2;
    private File saveFile;
    private String defaultSavePath;

    //HOWTO: Add a tool to the tool dialog: 
    //1. Fist follow the steps in ToolBox.java to add the tool to the toolbox
    //2. Check if the operation needs one or two input layers. 
    //FOR OPERATIONS WITH 2 INPUT LAYERS:
    //  3. Add a new case for the switch-statement in the actionPerformed(ActionEvent e) method
    //      by writing "case ToolBox.TOOLHANDLE:" (Same toolhandle as used in ToolBox.java)
    //  4. Add the following code to the case and change the call to the correct layer-method: 
        /*
        if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
            outLayer = inLayer1.zonalMin(inLayer2, outName); //Add the call to the correct Layer method here. 
            App.dispLayers.add(outLayer); //Ads the layer to the cataloge
            if(saveFile == null){ //Check if there is a specified savespace. Otherwise uses default. 
                outLayer.save(defaultSavePath+outName+".txt");
            } else {
               outLayer.save(saveFile.getAbsolutePath());
            }          
        }
        */
    //FOR OPERATIONS WITH ONLY 1 INPUT LAYER:
    //  3. Add a new case for the switch-statement in the actionPerformed(ActionEvent e) method
    //      by writing "case ToolBox.TOOLHANDLE:" (Same toolhandle as used in ToolBox.java)
    //  4. Change the code example above so that the if statement only checks inLayer1. 
    //      Use the following IF-statement instead: (inLayer1 != null)
    //  5. In the ToolDialog constructor, edit the if statement so it doesn´t run for your operation. 
    //      add "... && !OPERATION.equals(ToolBox.TOOLHANDLE)" inside the parenthesis of the if statement.

    public ToolDialog(String operation){
        super();

        OPERATION = operation;
        setTitle(OPERATION);

        setDefaultSavePath();

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JLabel message = new JLabel("Choose input file(s) for "+operation);
        message.setFont(App.H2);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(message,c);

        input1 = new JLabel("\nInput file 1: No file chosen");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(input1,c);

        JButton fileButton1 = new JButton("Choose input file...");
        fileButton1.addActionListener(this);
        fileButton1.setActionCommand(CHOOSE_FILE1);

        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(fileButton1,c);

        //Change this if-statement if adding tool that takes only one input layer. 
        if(!OPERATION.equals(ToolBox.SLOPE)){
            input2 = new JLabel("\nInput file 2: No file chosen");

            JButton fileButton2 = new JButton("Choose input file...");
            fileButton2.addActionListener(this);
            fileButton2.setActionCommand(CHOOSE_FILE2);
    
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 2;
            panel.add(input2,c);

            c.gridwidth = 1;
            c.gridx = 2;
            c.gridy = 2;
            panel.add(fileButton2,c);
            
        }

        outputLoc = new JLabel("Output file name: Null, using default");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(outputLoc,c);

        JButton NameButton = new JButton("Choose output location...");
        NameButton.addActionListener(this);
        NameButton.setActionCommand(SAVE_LOC);
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 3;
        panel.add(NameButton,c);


        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButton.setActionCommand(OK);

        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 4;
        panel.add(okButton,c);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand(CANCEL);

        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 4;
        panel.add(cancelButton,c);

        add(panel);
        pack();

        


    }

    private void setDefaultSavePath(){
        String fileSep = System.getProperty("file.separator");

        String runLocation = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        File locFile = new File(runLocation);

        int nameLength = locFile.getName().length();
        int folderLength = runLocation.length()-nameLength;

        String folder = runLocation.substring(0,folderLength);

        defaultSavePath = folder+"output"+fileSep;


        System.out.println("Default save path: "+defaultSavePath);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        File currentDir = new File(System.getProperty("user.dir"));
        if(cmd.equals(OK)){
            System.out.println("ok is pressed");

            if(inLayer1 == null){
                JOptionPane.showMessageDialog(
                    this,
                    "Unable to preform action: no layer loaded",
                    "No Layer",
                    JOptionPane.OK_OPTION
                );
            } else {
                String outName;
                if(saveFile == null){
                    outName = inLayer1.name + "_" + OPERATION;
                } else {
                    String[] name = saveFile.getName().split("[.]", 0);
                    outName = name[0];
                }

                if(saveFile == null){
                    File saveDir = new File(defaultSavePath);
                    Boolean newDir = saveDir.mkdirs();
                    System.out.println("Directory created: " + newDir);

                    saveFile = new File(defaultSavePath+outName+".txt");
                }

                Layer outLayer;
                switch(OPERATION){
                    case ToolBox.SLOPE: 
                        if(inLayer1 != null){
                            outLayer = inLayer1.focalSlope(outName);
                            App.dispLayers.add(outLayer);
                            outLayer.save(saveFile.getAbsolutePath());
                        }
                        break;
                    case ToolBox.ASPECT: 
                        System.out.println(ToolBox.ASPECT + "is run");

                        if(inLayer1 != null){
                            outLayer = inLayer1.focalAspect(outName);
                            App.dispLayers.add(outLayer);
                            outLayer.save(saveFile.getAbsolutePath());
                        }
                        break;
                    case ToolBox.ZONAL_MIN:
                        if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
                            outLayer = inLayer1.zonalMin(inLayer2, outName); //Add the call to the correct Layer method here. 
                            App.dispLayers.add(outLayer); //Ads the layer to the cataloge
                            outLayer.save(saveFile.getAbsolutePath());        
                        }
                        break;
                    case ToolBox.ZONAL_MAX:   
                        System.out.println(ToolBox.ZONAL_MAX + "is run");

                        if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
                            outLayer = inLayer1.zonalMax(inLayer2, outName); //Add the call to the correct Layer method here. 
                            App.dispLayers.add(outLayer); //Ads the layer to the cataloge
                            outLayer.save(saveFile.getAbsolutePath());  
                        } 
                        break;
                    case ToolBox.ZONAL_AVERAGE:  
                    System.out.println(ToolBox.ZONAL_AVERAGE + "is run");

                        if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
                            outLayer = inLayer1.zonalAvg(inLayer2, outName); //Add the call to the correct Layer method here. 
                            App.dispLayers.add(outLayer); //Ads the layer to the cataloge
                            outLayer.save(saveFile.getAbsolutePath());
                        }
                        break;
                    case ToolBox.LOCAL_SUM:
                        System.out.println(ToolBox.LOCAL_SUM + "is run");

                            if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
                                outLayer = inLayer1.localSum(inLayer2, outName); //Add the call to the correct Layer method here. 
                                App.dispLayers.add(outLayer); //Ads the layer to the cataloge
                                outLayer.save(saveFile.getAbsolutePath());      
                        }
                        break;
                    case ToolBox.LOCAL_DIFF:
                        System.out.println(ToolBox.LOCAL_DIFF + "is run");

                            if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
                                outLayer = inLayer1.localDifference(inLayer2, outName); //Add the call to the correct Layer method here. 
                                App.dispLayers.add(outLayer); //Ads the layer to the cataloge
                                outLayer.save(saveFile.getAbsolutePath());
                        }
                        break;
                    case ToolBox.LOCAL_DIV:
                        System.out.println(ToolBox.LOCAL_DIV + "is run");

                            if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
                                outLayer = inLayer1.localDivision(inLayer2, outName); //Add the call to the correct Layer method here. 
                                App.dispLayers.add(outLayer); //Ads the layer to the cataloge
                                outLayer.save(saveFile.getAbsolutePath());   
                        }
                        break;
                    case ToolBox.LOCAL_PROD:
                        System.out.println(ToolBox.LOCAL_PROD + "is run");

                            if(inLayer1 != null && inLayer2 != null){ //checks that the layers have been added. 
                                outLayer = inLayer1.localProduct(inLayer2, outName); //Add the call to the correct Layer method here. 
                                App.dispLayers.add(outLayer); //Ads the layer to the cataloge
                                outLayer.save(saveFile.getAbsolutePath());   
                        }
                        break;
                    //Add new case here
                    }
                
                App.catalogue.updateCatalogue();

                JFrame window = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, panel);
                window.dispose();
            }

        } else if (cmd.equals(CANCEL)){
            JFrame window = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, panel);
            window.dispose();
        }
        else if(cmd.equals(CHOOSE_FILE1)){
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
            fc.setCurrentDirectory(currentDir);
    
            int returnVal = fc.showOpenDialog(ToolDialog.this);
            System.out.println(returnVal);

    
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening: " + file.getName() + ".");
    
                String[] name = file.getName().split("[.]", 0);
                inLayer1 = new Layer(name[0],file);

                input1.setText("\nInput file: "+file.getName());

            }            
        } else if(cmd.equals(CHOOSE_FILE2)){
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
            fc.setCurrentDirectory(currentDir);
    
            int returnVal = fc.showOpenDialog(ToolDialog.this);
            System.out.println(returnVal);

    
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening: " + file.getName() + ".");
    
                String[] name = file.getName().split("[.]", 0);
                inLayer2 = new Layer(name[0],file);

                input2.setText("\nInput file: "+file.getName());

            }
        } else if(cmd.equals(SAVE_LOC)){
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
            fc.setCurrentDirectory(currentDir);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
            int returnVal = fc.showSaveDialog(ToolDialog.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                saveFile = fc.getSelectedFile();
                System.out.println("Saving at: " + saveFile.getName() + ".");

                outputLoc.setText("\nOutput file name: "+saveFile.getName());
            }
        }
    }
}
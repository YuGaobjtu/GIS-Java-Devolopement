package kth.ag2411.project.components;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.filechooser.*;

import kth.ag2411.project.App;
import kth.ag2411.project.mapalgebra.*;

public class FileMenu extends JPopupMenu
                    implements ActionListener {

    private JMenuItem loadRaster, loadNetwork, save, saveAll;
    static final private String LOADR = "Load Raster";
    static final private String LOADN = "Load Network";
    static final private String SAVEALL = "Save All";
    static final private String SAVETHIS = "Save This";

    public FileMenu(){
        super();

        loadRaster = new JMenuItem("Load Raster");
        loadRaster.addActionListener(this);
        loadRaster.setActionCommand(LOADR);

        loadNetwork = new JMenuItem("Load Network");
        loadNetwork.addActionListener(this);
        loadNetwork.setActionCommand(LOADN);
        loadNetwork.setEnabled(false);

        /* //Save not used in version 1.0
        saveAll = new JMenuItem("Save All");
        saveAll.addActionListener(this);
        saveAll.setActionCommand(SAVEALL);

        save = new JMenuItem("Save this");
        save.addActionListener(this);
        save.setActionCommand(SAVETHIS);
        */ 

        setBackground(MenuBar.BG);
        loadRaster.setBackground(MenuBar.BG);
        loadNetwork.setBackground(MenuBar.BG);
        //save.setBackground(MenuBar.BG);
        //saveAll.setBackground(MenuBar.BG);

        add(loadRaster);
        add(loadNetwork);
        //add(save);
        //add(saveAll)
    }

    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();
        System.out.println("Action triggered: "+cmd);

        //Create a file chooser
        JFileChooser fc = new JFileChooser();
 
        //Handle each button.
        if (LOADR.equals(cmd)) { //Load raster clicked
            
            //open JFilechooser for raster
        
            System.out.println("Load R Action triggered: "+cmd);
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
            File currentDir = new File(System.getProperty("user.dir"));
            fc.setCurrentDirectory(currentDir);

            int returnVal = fc.showOpenDialog(FileMenu.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening: " + file.getName() + ".");

                String[] name = file.getName().split("[.]", 0);
                Layer inLayer = new Layer(name[0],file);
                App.dispLayers.add(inLayer);

                App.catalogue.updateCatalogue();

            } else {
                System.out.println("Open command cancelled by user.");
            }

        } 
        else if (LOADN.equals(cmd)) { // Load Network clicked

            //open JFilechooser for network - this is not ready

            System.out.println("Load N Action triggered: "+cmd);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text network files only", "txt");
            fc.setFileFilter(filter);

            int returnVal = fc.showOpenDialog(FileMenu.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                //Implement sending the file to the network construct method

                System.out.println("Opening: " + file.getName() + ".");
            } else {
                System.out.println("Open command cancelled by user.");
            }

        } 
        
        else if (SAVEALL.equals(cmd)) { // third button clicked
            
            //Open location chooser for save (to be implemented)

            System.out.println("Save Action triggered: "+cmd);
        }
    }
}
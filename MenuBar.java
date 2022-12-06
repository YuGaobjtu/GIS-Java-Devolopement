package kth.ag2411.project.components;
import javax.swing.*;

import kth.ag2411.project.App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class MenuBar extends JPanel
                     implements ActionListener{
    static final private FlowLayout LAYOUT = new FlowLayout(FlowLayout.LEADING);
    static final public Color BG = new Color(129,129,129);
    static final private String FILE = "file";
    static final private String SHOW = "show";
    static final private String HELP = "help";
    static final private String ZOOM_IN = "zoom in";
    static final private String ZOOM_OUT = "zoom out";
    static final private int HEIGHT = 39;

    public MenuBar(){
        super();

        setLayout(LAYOUT);

        setBackground(BG);

        JButton fileButton = createMenueButton("File...",FILE);
        JButton showButton = createMenueButton("Show",SHOW);
        JButton helpButton = createMenueButton("Help",HELP);
        JButton zoomIn = createMenueButton("Zoom in",ZOOM_IN);
        JButton zoomOut = createMenueButton("Zoom out",ZOOM_OUT);

        add(fileButton);
        //add(showButton); //Showbutton not implemented for version 1. 
        add(helpButton);
        add(zoomIn);
        add(zoomOut);
    }
    
    private JButton createMenueButton(String name, String action){
        JButton b = new JButton(name);
        b.setActionCommand(action);
        b.addActionListener(this);
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        JPopupMenu fileMenu = new FileMenu();
        JPopupMenu showMenu = new ShowMenu();
        
        System.out.println("Action triggered: "+cmd);

        // Handle each button.
        if (FILE.equals(cmd)) { //first button clicked
            add(fileMenu);
            fileMenu.show(this, 0, HEIGHT);

        } else if (SHOW.equals(cmd)) { // second button clicked
            add(showMenu);
            showMenu.show(this, 90, HEIGHT);

        } else if (HELP.equals(cmd)) { // third button clicked
            URI helplink;
            try {
                helplink = new URI("https://mobfest.se/Help.html");
                openWebpage(helplink);

            } catch (URISyntaxException e1) {
                // TODO Add dialog saying something is wrong
                e1.printStackTrace();
            }
            

        } else if (ZOOM_IN.equals(cmd)){
            App.zoom(1);
        } else if (ZOOM_OUT.equals(cmd)){
            if (App.zoomLvl > 1){
                App.zoom(-1);
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Unable to preform action: Att minimum zoom level",
                    "Min Zoom reached",
                    JOptionPane.OK_OPTION
                );
            }
        }
 
    }

    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

}

package kth.ag2411.project.components;
import javax.swing.*;

public class ShowMenu extends JPopupMenu{
    private JMenuItem toolBox, catalogue;

    public ShowMenu(){
        super();
        System.out.println("File Menu constructed");

        toolBox = new JMenuItem("Tool Box");
        catalogue = new JMenuItem("Catalogue");

        setBackground(MenuBar.BG);
        toolBox.setBackground(MenuBar.BG);
        catalogue.setBackground(MenuBar.BG);

        add(toolBox);
        add(catalogue);
    }
    
}

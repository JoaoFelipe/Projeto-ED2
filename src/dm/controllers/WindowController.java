package dm.controllers;

import dm.DataManager;
import dm.views.MainWindow;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WindowController {

    //Um dos controladores do padrão de projeto MVC
    
    public static void open(JDialog dialog) {
        JFrame mainFrame = DataManager.getApplication().getMainFrame();
        dialog.setLocationRelativeTo(mainFrame);
        DataManager.getApplication().show(dialog);
    }

    public static void closeDialog(JDialog dialog) {
        dialog.dispose();
    }

    public static void closeProgram() {
        System.exit(0);
    }

    public static void message(String text) {
        JOptionPane.showMessageDialog(MainWindow.getInstance(), text);
    }
    
}

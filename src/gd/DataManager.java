package gd;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import gd.views.MainWindow;
import javax.swing.JFrame;

public class DataManager extends SingleFrameApplication {
    //Aqui está sendo usado o padrão Singleton, que foi usado para haver uma única
    //instância dessa classe que pode ser acessada estaticamente em qualquer
    //parte do projeto
    private static DataManager instance = null;

    private DataManager() {
        super();
    }

    public static DataManager getInstance() {
        if (DataManager.instance == null){
            DataManager.instance = new DataManager();
        }
        return DataManager.instance;
    }

    @Override protected void startup() {
        show(MainWindow.getInstance());
        MainWindow.getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override protected void configureWindow(java.awt.Window root) {
    }

    public static DataManager getApplication() {
        return Application.getInstance(DataManager.class);
    }

    public static void main(String[] args) {
        launch(DataManager.class, args);
    }
}

/*
 * GerenciadorDadosApp.java
 */

package gd;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import gd.views.principal.TelaPrincipal;
import javax.swing.JFrame;

/**
 * The main class of the application.
 */
public class GerenciadorDados extends SingleFrameApplication {

    //padrão singleton

    public GerenciadorDados() {
        super();
    }

    public static GerenciadorDados instancia;

    public static GerenciadorDados getInstance() {
        if (GerenciadorDados.instancia == null){
            GerenciadorDados.instancia = new GerenciadorDados();
        }
        return GerenciadorDados.instancia;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(TelaPrincipal.getInstance());
        TelaPrincipal.getInstance().getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of GerenciadorDadosApp
     */
    public static GerenciadorDados getApplication() {
        return Application.getInstance(GerenciadorDados.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(GerenciadorDados.class, args);
    }
}

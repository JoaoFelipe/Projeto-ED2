package gd;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import gd.views.TelaPrincipalView;
import javax.swing.JFrame;

public class GerenciadorDados extends SingleFrameApplication {
    
    public static GerenciadorDados instancia = null;

    private GerenciadorDados() {
        super();
    }

    public static GerenciadorDados getInstance() {
        if (GerenciadorDados.instancia == null){
            GerenciadorDados.instancia = new GerenciadorDados();
        }
        return GerenciadorDados.instancia;
    }

    @Override protected void startup() {
        show(TelaPrincipalView.getInstance());
        TelaPrincipalView.getInstance()./*getFrame().*/setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override protected void configureWindow(java.awt.Window root) {
    }

    public static GerenciadorDados getApplication() {
        return Application.getInstance(GerenciadorDados.class);
    }

    public static void main(String[] args) {
        launch(GerenciadorDados.class, args);
    }
}

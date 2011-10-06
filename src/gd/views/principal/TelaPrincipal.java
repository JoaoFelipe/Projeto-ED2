/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal;

import gd.views.principal.menu.MenuBar;
import gd.GerenciadorDados;
import javax.swing.JFrame;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;

/**
 *
 * @author Joao
 */
public class TelaPrincipal  extends FrameView {

    //Padrão Singleton

    private MenuBar menuBar;
    private MainPanel mainPanel;

    private static TelaPrincipal instancia = null;

    public TelaPrincipal(SingleFrameApplication app) {
        super(app);
        menuBar = new MenuBar();
        mainPanel = new MainPanel();
        initComponents();

    }




    public static TelaPrincipal getInstance() {
        if (TelaPrincipal.instancia == null)
            TelaPrincipal.instancia = new TelaPrincipal(GerenciadorDados.getInstance());
        return TelaPrincipal.instancia;
    }

    public void initComponents() {
  
        setMenuBar(menuBar);
        setComponent(mainPanel);
    }

 //   @Action
//    public void showAboutBox() {
//        sobreView = MainController.abrirDialog(sobreView, SobreView.class);
 //       GerenciadorDados.getApplication().show(sobreView);
 //   }

 //   @Action
 //   public void showCriarTabelaBox() {
 //       criarTabelaView = MainController.abrirDialog(criarTabelaView, CriarTabelaView.class);
 //       GerenciadorDados.getApplication().show(criarTabelaView);
 //   }

 //   @Action
 //   public void showCriarReferenciaView() {
 //       criarReferenciaView = MainController.abrirDialog(criarReferenciaView, CriarReferenciaView.class);
 //       GerenciadorDados.getApplication().show(criarReferenciaView);
 //   }





}

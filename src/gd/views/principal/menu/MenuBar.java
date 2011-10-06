/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.views.principal.menu.CriarReferenciaMenuItem;

/**
 *
 * @author Joao
 */
public class MenuBar extends javax.swing.JMenuBar{

    javax.swing.JMenuItem criarTabelaMenuItem = null;
    javax.swing.JMenuItem excluirTabelaMenuItem = null;
    javax.swing.JMenuItem criarReferenciaMenuItem = null;
    javax.swing.JPopupMenu.Separator separadorArquivoSeparator = null;
    javax.swing.JMenuItem tabelaMenu = null;
    javax.swing.JMenuItem inserirRegistroMenuItem = null;
    javax.swing.JMenuItem consultarRegistroMenuItem = null;
    javax.swing.JMenuItem atualizarRegistroMenuItem = null;
    javax.swing.JMenuItem removerRegistroMenuItem = null;
    javax.swing.JMenuItem sobreMenuItem = null;

    javax.swing.JMenu arquivoMenu;
    javax.swing.JMenuItem sairMenuItem;
    javax.swing.JMenu ajudaMenu;

    public MenuBar(){
        arquivoMenu = new javax.swing.JMenu();
        criarTabelaMenuItem = new CriarTabelaMenuItem();
        excluirTabelaMenuItem = new ExcluirTabelaMenuItem();
        criarReferenciaMenuItem = new CriarReferenciaMenuItem();
        separadorArquivoSeparator = new javax.swing.JPopupMenu.Separator();
        sairMenuItem = new FecharMenuItem();
        tabelaMenu = new javax.swing.JMenu();
        inserirRegistroMenuItem = new InserirRegistroMenuItem();
        consultarRegistroMenuItem = new ConsultarRegistroMenuItem();
        atualizarRegistroMenuItem = new AtualizarRegistroMenuItem();
        removerRegistroMenuItem = new RemoverRegistroMenuItem();
        ajudaMenu = new javax.swing.JMenu();
        sobreMenuItem = new SobreMenuItem();
        initComponents();
    }

    public void initComponents() {
        arquivoMenu.setText("Arquivo");
        arquivoMenu.add(criarTabelaMenuItem);
        arquivoMenu.add(criarReferenciaMenuItem);
        arquivoMenu.add(excluirTabelaMenuItem);
        arquivoMenu.add(separadorArquivoSeparator);
        arquivoMenu.add(sairMenuItem);
        this.add(arquivoMenu);

        tabelaMenu.setText("Tabela");
        tabelaMenu.add(inserirRegistroMenuItem);
        tabelaMenu.add(consultarRegistroMenuItem);
        tabelaMenu.add(atualizarRegistroMenuItem);
        tabelaMenu.add(removerRegistroMenuItem);
        this.add(tabelaMenu);

        ajudaMenu.setText("Ajuda"); 
        ajudaMenu.add(sobreMenuItem);
        this.add(ajudaMenu);

    }

}

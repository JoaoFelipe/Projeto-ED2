/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package gd.views.principal;

import gd.views.principal.TabelaPanel;
import gd.views.principal.TabelasPanel;

/**
 *
 * @author Joao
 */
public class DivisaoPrincipal extends javax.swing.JSplitPane {

    private TabelasPanel tabelasPanel = null;
    private TabelaPanel tabelaPanel = null;

    public DivisaoPrincipal() {
        tabelasPanel = new TabelasPanel();
        tabelaPanel = new TabelaPanel();
        initComponents();
    }

    public void initComponents() {
        /*divisaoPrincipalSplitPane.setDividerLocation(200);
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(divisaoPrincipalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(divisaoPrincipalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
        );*/

        this.setLeftComponent(tabelasPanel);
        this.setRightComponent(tabelaPanel);

    }

}

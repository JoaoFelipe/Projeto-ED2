package gd.views;

import gd.DataManager;
import gd.controllers.Command;
import gd.controllers.AbstractSearchCommand;
import gd.controllers.SearchCommand;
import gd.controllers.InsertCommand;
import gd.controllers.WindowController;
import gd.controllers.RemoveCommand;
import gd.controllers.TableController;
import gd.models.ER.Entity;
import gd.models.ER.ERList;
import gd.models.arquivo.Search;
import gd.views.tabelaer.MainTable;
import javax.swing.JFrame;

public class MainWindow extends JFrame {

    //Padrão singleton
    
    private static MainWindow instance = null;
    private Command insertCommand = null;

    public static MainWindow getInstance() {
        if (MainWindow.instance == null)
            MainWindow.instance = new MainWindow();
        return MainWindow.instance;
    }

    private MainWindow() {
        initComponents();
        
        cancelButton.setVisible(false);
        cancelButton.setText("Cancelar");
        insertCommand = new InsertCommand(cancelButton, (MainTable) tupleTable);      
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainSplit = new javax.swing.JSplitPane();
        listPanel = new javax.swing.JPanel();
        listLabel = new javax.swing.JLabel();
        listScrollPane = new javax.swing.JScrollPane();
        tableList = new gd.views.TableList();
        listButtonPanel = new javax.swing.JPanel();
        createTableButton = new javax.swing.JButton();
        removeTableButton = new javax.swing.JButton();
        createReferenceButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        tableButtonPanel = new javax.swing.JPanel();
        insertTupleButton = new javax.swing.JButton();
        searchTuplesButton = new javax.swing.JButton();
        changeTuplesButton = new javax.swing.JButton();
        removeTuplesButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        tableScrollPane = new javax.swing.JScrollPane();
        tupleTable = gd.views.tabelaer.MainTable.instantiate(tableButtonPanel);
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        createTableMenuItem = new javax.swing.JMenuItem();
        createReferenceMenuItem = new javax.swing.JMenuItem();
        removeTableMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        tableMenu = new javax.swing.JMenu();
        insertTupleMenuItem = new javax.swing.JMenuItem();
        searchTuplesMenuItem = new javax.swing.JMenuItem();
        changeTuplesMenuItem = new javax.swing.JMenuItem();
        removeTuplesMenuItem = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gd.DataManager.class).getContext().getResourceMap(MainWindow.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(600, 0));
        setName("Form"); // NOI18N

        mainPanel.setName("mainPanel"); // NOI18N

        mainSplit.setDividerLocation(175);
        mainSplit.setName("mainSplit"); // NOI18N

        listPanel.setName("listPanel"); // NOI18N

        listLabel.setText(resourceMap.getString("listLabel.text")); // NOI18N
        listLabel.setName("listLabel"); // NOI18N

        listScrollPane.setName("listScrollPane"); // NOI18N

        tableList.setName("tableList"); // NOI18N
        listScrollPane.setViewportView(tableList);

        listButtonPanel.setName("listButtonPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(gd.DataManager.class).getContext().getActionMap(MainWindow.class, this);
        createTableButton.setAction(actionMap.get("showCriarTabelaBox")); // NOI18N
        createTableButton.setText(resourceMap.getString("createTableButton.text")); // NOI18N
        createTableButton.setName("createTableButton"); // NOI18N
        createTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTableActionPerformed(evt);
            }
        });

        removeTableButton.setText(resourceMap.getString("removeTableButton.text")); // NOI18N
        removeTableButton.setName("removeTableButton"); // NOI18N
        removeTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTableActionPerformed(evt);
            }
        });

        createReferenceButton.setAction(actionMap.get("showCriarReferenciaView")); // NOI18N
        createReferenceButton.setText(resourceMap.getString("createReferenceButton.text")); // NOI18N
        createReferenceButton.setName("createReferenceButton"); // NOI18N
        createReferenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createReferenceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout listButtonPanelLayout = new javax.swing.GroupLayout(listButtonPanel);
        listButtonPanel.setLayout(listButtonPanelLayout);
        listButtonPanelLayout.setHorizontalGroup(
            listButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(listButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(createTableButton, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addGroup(listButtonPanelLayout.createSequentialGroup()
                        .addComponent(createReferenceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeTableButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        listButtonPanelLayout.setVerticalGroup(
            listButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listButtonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createTableButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(listButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeTableButton)
                    .addComponent(createReferenceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(listLabel))
                .addContainerGap())
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainSplit.setLeftComponent(listPanel);

        tablePanel.setName("tablePanel"); // NOI18N

        tableButtonPanel.setName("tableButtonPanel"); // NOI18N

        insertTupleButton.setText(resourceMap.getString("insertTupleButton.text")); // NOI18N
        insertTupleButton.setName("insertTupleButton"); // NOI18N
        insertTupleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertTupleActionPerformed(evt);
            }
        });

        searchTuplesButton.setText(resourceMap.getString("searchTuplesButton.text")); // NOI18N
        searchTuplesButton.setName("searchTuplesButton"); // NOI18N
        searchTuplesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTuplesActionPerformed(evt);
            }
        });

        changeTuplesButton.setText(resourceMap.getString("changeTuplesButton.text")); // NOI18N
        changeTuplesButton.setName("changeTuplesButton"); // NOI18N
        changeTuplesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTuplesActionPerformed(evt);
            }
        });

        removeTuplesButton.setText(resourceMap.getString("removeTuplesButton.text")); // NOI18N
        removeTuplesButton.setName("removeTuplesButton"); // NOI18N
        removeTuplesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTuplesActionPerformed(evt);
            }
        });

        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtoninserirRegistroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tableButtonPanelLayout = new javax.swing.GroupLayout(tableButtonPanel);
        tableButtonPanel.setLayout(tableButtonPanelLayout);
        tableButtonPanelLayout.setHorizontalGroup(
            tableButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tableButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(insertTupleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchTuplesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changeTuplesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeTuplesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addContainerGap())
        );
        tableButtonPanelLayout.setVerticalGroup(
            tableButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tableButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tableButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertTupleButton)
                    .addComponent(searchTuplesButton)
                    .addComponent(changeTuplesButton)
                    .addComponent(removeTuplesButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableScrollPane.setName("tableScrollPane"); // NOI18N

        tupleTable.setName("tupleTable"); // NOI18N
        tableScrollPane.setViewportView(tupleTable);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainSplit.setRightComponent(tablePanel);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainSplit)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        createTableMenuItem.setText(resourceMap.getString("createTableMenuItem.text")); // NOI18N
        createTableMenuItem.setName("createTableMenuItem"); // NOI18N
        createTableMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTableActionPerformed(evt);
            }
        });
        fileMenu.add(createTableMenuItem);

        createReferenceMenuItem.setText(resourceMap.getString("createReferenceMenuItem.text")); // NOI18N
        createReferenceMenuItem.setName("createReferenceMenuItem"); // NOI18N
        createReferenceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createReferenceActionPerformed(evt);
            }
        });
        fileMenu.add(createReferenceMenuItem);

        removeTableMenuItem.setText(resourceMap.getString("removeTableMenuItem.text")); // NOI18N
        removeTableMenuItem.setName("removeTableMenuItem"); // NOI18N
        removeTableMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTableActionPerformed(evt);
            }
        });
        fileMenu.add(removeTableMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        tableMenu.setText(resourceMap.getString("tableMenu.text")); // NOI18N
        tableMenu.setName("tableMenu"); // NOI18N
        tableMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertTupleActionPerformed(evt);
            }
        });

        insertTupleMenuItem.setText(resourceMap.getString("insertTupleMenuItem.text")); // NOI18N
        insertTupleMenuItem.setName("insertTupleMenuItem"); // NOI18N
        insertTupleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertTupleActionPerformed(evt);
            }
        });
        tableMenu.add(insertTupleMenuItem);

        searchTuplesMenuItem.setText(resourceMap.getString("searchTuplesMenuItem.text")); // NOI18N
        searchTuplesMenuItem.setName("searchTuplesMenuItem"); // NOI18N
        searchTuplesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTuplesActionPerformed(evt);
            }
        });
        tableMenu.add(searchTuplesMenuItem);

        changeTuplesMenuItem.setText(resourceMap.getString("changeTuplesMenuItem.text")); // NOI18N
        changeTuplesMenuItem.setName("changeTuplesMenuItem"); // NOI18N
        changeTuplesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTuplesActionPerformed(evt);
            }
        });
        tableMenu.add(changeTuplesMenuItem);

        removeTuplesMenuItem.setText(resourceMap.getString("removeTuplesMenuItem.text")); // NOI18N
        removeTuplesMenuItem.setName("removeTuplesMenuItem"); // NOI18N
        removeTuplesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTuplesActionPerformed(evt);
            }
        });
        tableMenu.add(removeTuplesMenuItem);

        menuBar.add(tableMenu);

        aboutMenu.setText(resourceMap.getString("aboutMenu.text")); // NOI18N
        aboutMenu.setName("aboutMenu"); // NOI18N

        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        aboutMenu.add(aboutMenuItem);

        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        WindowController.closeProgram();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void createTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createTableActionPerformed
        WindowController.open(new CreateTableView(DataManager.getApplication().getMainFrame(), (TableList) tableList));
    }//GEN-LAST:event_createTableActionPerformed

    private void createReferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createReferenceActionPerformed
        WindowController.open(new CreateReferenceView(DataManager.getApplication().getMainFrame(), (TableList) tableList));
    }//GEN-LAST:event_createReferenceActionPerformed

    private void removeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeTableActionPerformed
        TableList table = (TableList) tableList;
        TableController.removeTable(table.getSelectedName());
        table.update();
    }//GEN-LAST:event_removeTableActionPerformed

    private void insertTupleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertTupleActionPerformed
        insertCommand = insertCommand.execute();
    }//GEN-LAST:event_insertTupleActionPerformed

    private void searchTuplesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTuplesActionPerformed
        AbstractSearchCommand command = new SearchCommand(new Search((Entity) ERList.getSelected(), null), (MainTable) tupleTable);
        WindowController.open(new SearchView(DataManager.getApplication().getMainFrame(), command));
    }//GEN-LAST:event_searchTuplesActionPerformed

    private void changeTuplesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeTuplesActionPerformed
        WindowController.open(new ChangeTuplesView(DataManager.getApplication().getMainFrame(), new Search((Entity) ERList.getSelected(), null),(MainTable) tupleTable));
    }//GEN-LAST:event_changeTuplesActionPerformed

    private void removeTuplesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeTuplesActionPerformed
        AbstractSearchCommand command = new RemoveCommand(new Search((Entity) ERList.getSelected(), null), (MainTable) tupleTable);
        WindowController.open(new SearchView(DataManager.getApplication().getMainFrame(), command));
    }//GEN-LAST:event_removeTuplesActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        WindowController.open(new AboutView(DataManager.getApplication().getMainFrame()));
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void cancelButtoninserirRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtoninserirRegistroActionPerformed
        ((MainTable) tupleTable).removeRow();
        cancelButton.setVisible(false);
        insertCommand = new InsertCommand(cancelButton, (MainTable) tupleTable);
    }//GEN-LAST:event_cancelButtoninserirRegistroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton changeTuplesButton;
    private javax.swing.JMenuItem changeTuplesMenuItem;
    private javax.swing.JButton createReferenceButton;
    private javax.swing.JMenuItem createReferenceMenuItem;
    private javax.swing.JButton createTableButton;
    private javax.swing.JMenuItem createTableMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton insertTupleButton;
    private javax.swing.JMenuItem insertTupleMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPanel listButtonPanel;
    private javax.swing.JLabel listLabel;
    private javax.swing.JPanel listPanel;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSplitPane mainSplit;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton removeTableButton;
    private javax.swing.JMenuItem removeTableMenuItem;
    private javax.swing.JButton removeTuplesButton;
    private javax.swing.JMenuItem removeTuplesMenuItem;
    private javax.swing.JButton searchTuplesButton;
    private javax.swing.JMenuItem searchTuplesMenuItem;
    private javax.swing.JPanel tableButtonPanel;
    private javax.swing.JList tableList;
    private javax.swing.JMenu tableMenu;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JTable tupleTable;
    // End of variables declaration//GEN-END:variables

}
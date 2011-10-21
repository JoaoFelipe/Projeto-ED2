package gd.views.base;

import javax.swing.event.ListSelectionListener;

public abstract class Lista extends javax.swing.JList implements ListSelectionListener{

    public Lista() {
        super();
        addListSelectionListener(this);
    }

    public abstract void atualizar();

}

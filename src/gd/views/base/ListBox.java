package gd.views.base;

import javax.swing.event.ListSelectionListener;

public abstract class ListBox extends javax.swing.JList implements ListSelectionListener{

    public ListBox() {
        super();
        addListSelectionListener(this);
    }

    public abstract void update();

}

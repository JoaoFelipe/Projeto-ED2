/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.criartabela;

import gd.controllers.*;
import gd.views.criartabela.AtributosTabela;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class RemoverAtributoCommand extends Command{

    AtributosTabela table = null;


    public RemoverAtributoCommand(AtributosTabela table) {
        super();
        this.table = table;
    }

    @Override
    public void execute(Object... arg) {
        table.removerAtributo();
    }



}

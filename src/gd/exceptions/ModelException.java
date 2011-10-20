/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.exceptions;

import gd.controllers.Command;
import gd.controllers.JanelaController;
import javax.swing.JOptionPane;

/**
 *
 * @author Joao
 */
public class ModelException extends Exception{

    public ModelException(String message){
        super(message);
    }

    public ModelException(){
        super();
    }

    public ModelException(Exception e){
        super(e);
    }

    public void execute(){
        JanelaController.mensagem(this.getMessage());
    }

}

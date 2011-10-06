/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.exceptions;

import gd.controllers.Command;
import gd.controllers.MensagemErroCommand;
import javax.swing.JOptionPane;

/**
 *
 * @author Joao
 */
public class ModelException extends Exception{
    private Command command = new MensagemErroCommand();

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
        command.execute(this.getMessage());
    }

}

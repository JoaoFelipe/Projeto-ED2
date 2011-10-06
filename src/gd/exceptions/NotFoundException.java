/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.exceptions;

/**
 *
 * @author Joao
 */
public class NotFoundException extends ModelException{

    private String message ;

    public NotFoundException(String message){
        super(message);
        this.message = message;
    }

    public NotFoundException(){
        super("Not Found Exception");
        this.message = "Not Found Exception";
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}

package gd.exceptions;

import gd.controllers.WindowController;

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
        WindowController.message(this.getMessage());
    }

}

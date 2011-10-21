package gd.exceptions;

public class NonUniqueException extends ModelException{

    private String message ;

    public NonUniqueException(String message){
        super(message);
        this.message = message;
    }

    public NonUniqueException(){
        super("Non Unique Value Exception");
        this.message = "Non Unique Value Exception";
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}

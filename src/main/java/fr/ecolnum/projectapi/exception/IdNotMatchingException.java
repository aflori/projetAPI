package fr.ecolnum.projectapi.exception;

public class IdNotMatchingException extends  Exception{
    public IdNotMatchingException(){

    }

    public IdNotMatchingException(String message){
        super(message);
    }
}

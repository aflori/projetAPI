package fr.ecolnum.projectapi.exception;

public class PoolNotMatchingException extends  Exception{
    public PoolNotMatchingException(){

    }

    public PoolNotMatchingException(String message){
        super(message);
    }
}

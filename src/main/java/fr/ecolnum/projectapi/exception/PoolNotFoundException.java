package fr.ecolnum.projectapi.exception;

public class PoolNotFoundException extends Exception{
    public PoolNotFoundException(){
        super();
    }
    public PoolNotFoundException(String str){
        super(str);
    }
}

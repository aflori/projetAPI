package fr.ecolnum.projectapi.exception;

public class IdNotFoundException extends Exception{
    public IdNotFoundException(){
        super();
    }
    public IdNotFoundException(String str){
        super(str);
    }
}

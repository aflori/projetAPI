package fr.ecolnum.projectapi.exception;

import javax.naming.Name;

public class NameNotFoundException extends Exception{
    public NameNotFoundException(String message){
        super(message);
    }
}

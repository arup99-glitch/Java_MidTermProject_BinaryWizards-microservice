package com.BinaryWizards.authenticationserver.exception;

public class UserNotFoundByEmailException extends RuntimeException{

    public UserNotFoundByEmailException(String email){
        super("Invalid email address ( "+email+" )");
    }

}

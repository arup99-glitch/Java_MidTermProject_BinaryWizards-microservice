package com.BinaryWizards.authenticationserver.exception;

public class InvalidRoleException extends RuntimeException{
    public InvalidRoleException(String roleName){
        super("Invalid Role ( "+roleName+" )");
    }

}

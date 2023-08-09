package com.BinaryWizards.authenticationserver.validator;

public interface MessageConstants {

    String FIRSTNAME_EMPTY = "firstName must not be empty";
    String LASTNAME_EMPTY = "lastName must not be empty";
    String EMAIL_EMPTY = "email should be valid";
    String PASSWORD_EMPTY = "password must not be empty";
    String PHONE_EMPTY = "phone must not be empty";
    String ADDRESS_EMPTY = "address must not be empty";
    String ROLES_EMPTY = "roles must not be empty";
    String EMAIL_EXISTS = "user with the email already exists";
    String INVALID_EMAIL = "email should be valid";
    String REGISTRATION_SUCCESS = "User registration successful";
    String JWT_TOKEN_GENERATION_FAILED = "Failed to generate the jwt token please try again";
    String LOGIN_SUCCESSFUL = "Login successful";
    String LOGIN_FAILED = "Login failed";

}

package com.BinaryWizards.authenticationserver.model;

import com.BinaryWizards.authenticationserver.role.Role;
import com.BinaryWizards.authenticationserver.validator.MessageConstants;
import com.BinaryWizards.authenticationserver.validator.annotation.TrimmedNotEmpty;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequest {

    @TrimmedNotEmpty(message = MessageConstants.FIRSTNAME_EMPTY)
    private String firstName;
    @TrimmedNotEmpty(message = MessageConstants.LASTNAME_EMPTY)
    private String lastName;
    @Email(message = MessageConstants.INVALID_EMAIL)
    private String email;
    @TrimmedNotEmpty(message = MessageConstants.PASSWORD_EMPTY)
    private String password;
    @TrimmedNotEmpty(message = MessageConstants.PHONE_EMPTY)
    private String phone;
    @TrimmedNotEmpty(message = MessageConstants.ADDRESS_EMPTY)
    private String address;
    @NotEmpty(message = MessageConstants.ROLES_EMPTY)
    private Set<Role> roles;
    private Date dob;

}

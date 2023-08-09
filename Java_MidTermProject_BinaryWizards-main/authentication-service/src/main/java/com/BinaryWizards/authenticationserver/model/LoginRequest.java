package com.BinaryWizards.authenticationserver.model;

import com.BinaryWizards.authenticationserver.validator.MessageConstants;
import com.BinaryWizards.authenticationserver.validator.annotation.TrimmedNotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @TrimmedNotEmpty(message = MessageConstants.EMAIL_EMPTY)
    private String email;
    @TrimmedNotEmpty(message = MessageConstants.PASSWORD_EMPTY)
    private String password;

}

package com.BinaryWizards.authenticationserver.validator;

import com.BinaryWizards.authenticationserver.validator.annotation.TrimmedNotEmpty;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TrimmedNotEmptyValidator implements ConstraintValidator<TrimmedNotEmpty, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return !value.trim().isEmpty();
    }

}

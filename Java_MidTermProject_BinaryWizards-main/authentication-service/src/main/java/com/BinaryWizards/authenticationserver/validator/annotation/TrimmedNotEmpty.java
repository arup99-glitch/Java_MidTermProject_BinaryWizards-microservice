package com.BinaryWizards.authenticationserver.validator.annotation;

import com.BinaryWizards.authenticationserver.validator.TrimmedNotEmptyValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TrimmedNotEmptyValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotEmpty(message = "Field must not be empty")
public @interface TrimmedNotEmpty {

    String message() default "Field must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
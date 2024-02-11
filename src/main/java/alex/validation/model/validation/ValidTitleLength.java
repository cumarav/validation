package alex.validation.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchTitleLengthValidator.class)
public @interface ValidTitleLength {

    String message() default "Invalid Title Length value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

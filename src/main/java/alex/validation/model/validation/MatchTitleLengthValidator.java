package alex.validation.model.validation;

import alex.validation.model.BookInput;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Log4j2
public class MatchTitleLengthValidator implements ConstraintValidator<ValidTitleLength, BookInput> {

    public MatchTitleLengthValidator() {
        log.info("Validator created");
    }

    @Override
    public void initialize(ValidTitleLength constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BookInput bookInput, ConstraintValidatorContext constraintValidatorContext) {
        if (bookInput != null && isNotBlank(bookInput.getTitle()) && bookInput.getTitleLength() != null) {
            int titleLength = bookInput.getTitleLength().intValue();
            return titleLength == bookInput.getTitle().length();
        }
        return false;
    }
}

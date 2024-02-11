package alex.validation.service;

import alex.validation.model.BookInput;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final Validator validator;
    private final Random random = new Random();

    public Long saveNewBook(@Valid BookInput bookInput) {
        Set<ConstraintViolation<BookInput>> errors = validator.validate(bookInput);

        if(!errors.isEmpty()){
            throw new ConstraintViolationException(errors);
        }

        return bookInput.getTitleLength() + random.nextLong(1000L, 1100L);
    }

}

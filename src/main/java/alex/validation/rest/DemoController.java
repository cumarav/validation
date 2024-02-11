package alex.validation.rest;

import alex.validation.model.BookInput;
import alex.validation.model.BookOutput;
import alex.validation.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping(value = DemoController.BOOKS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class DemoController {

    public static final String BOOKS_ENDPOINT = "/api/books";

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookOutput> createBook(@RequestBody @Valid BookInput newBook) {


        Long bookId = bookService.saveNewBook(newBook);

        BookOutput newBookBody = BookOutput.builder().bookId(bookId).build();
        return ResponseEntity.created(URI.create(BOOKS_ENDPOINT).resolve(Long.toString(bookId)))
                .body(newBookBody);
    }


}

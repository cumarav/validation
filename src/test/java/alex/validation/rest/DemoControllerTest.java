package alex.validation.rest;

import alex.validation.model.BookInput;
import alex.validation.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static alex.validation.rest.DemoController.BOOKS_ENDPOINT;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes =
        {
                BookService.class,
                DemoController.class,
                GlobalExceptionHandler.class
        }
)
@WebMvcTest(DemoController.class)
class DemoControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void basicCreateBookTest() throws Exception {
        String body = toJson(createBook());
        mvc.perform(
                        post(BOOKS_ENDPOINT)
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void requiresTitleValidationTest() throws Exception {
        var book = createBadBook();
        String body = toJson(book);
        mvc.perform(
                        post(BOOKS_ENDPOINT)
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Title Length should be set")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void requiresTitleLengthValidationTest() throws Exception {
        var book = createBook();
        book.setTitleLength(100L);
        String body = toJson(book);
        mvc.perform(
                        post(BOOKS_ENDPOINT)
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid Title Length value")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private BookInput createBook() {
        return BookInput.builder().title("FirstBook").isbn(1_000_001L).titleLength(9L).build();

    }

    private BookInput createBadBook() {
        return BookInput.builder().title("FirstBook").isbn(1_000_001L).build();
    }

    @SneakyThrows
    private String toJson(Object pojo) {
        return objectMapper.writeValueAsString(pojo);
    }
}
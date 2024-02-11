package alex.validation.model;

import alex.validation.model.validation.ValidTitleLength;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldNameConstants
@ValidTitleLength
public class BookInput {

    @NotNull(message = "ISBN must be specified.")
    private Long isbn;

    @NotBlank(message = "Title must be specified.")
    private String title;

    @PositiveOrZero
    @NotNull(message = "Title Length should be set")
    private Long titleLength;
}

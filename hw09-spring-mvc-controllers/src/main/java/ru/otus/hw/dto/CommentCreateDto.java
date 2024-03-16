package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentCreateDto {

    @Positive
    private long id;

    @NotBlank(message = "Comments field should not be blank")
    @Size(min = 1, max = 255, message = "Comments field  should be between 1 and 255 characters")
    private String textContent;

    @Positive
    private long bookId;
}

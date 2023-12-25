package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        var questionDtoList = parseCsvToQuestionDto(fileNameProvider);
        return transferCsvToDomain(questionDtoList);
    }

    private List<QuestionDto> parseCsvToQuestionDto(TestFileNameProvider fileNameProvider) {
        var classLoader = getClass().getClassLoader();
        try (var inputStream = classLoader.getResourceAsStream(fileNameProvider.getTestFileName());
             var streamReader = new InputStreamReader(
                     Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)
        ) {
            var csvReader = new CsvToBeanBuilder<QuestionDto>(streamReader)
                    .withType(QuestionDto.class).withSeparator(';').withSkipLines(1).build();
            return csvReader.parse();
        } catch (Exception e) {
            throw new QuestionReadException("Error of reading file with questions!", e);
        }
    }

    @NonNull
    private List<Question> transferCsvToDomain(@NonNull List<QuestionDto> dtoList) {
        List<Question> questionList = new ArrayList<>();
        for (QuestionDto dto : dtoList) {
            questionList.add(dto.toDomainObject());
        }
        return questionList;
    }
}

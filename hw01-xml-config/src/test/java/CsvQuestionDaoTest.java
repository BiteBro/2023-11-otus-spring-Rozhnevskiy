import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CsvQuestionDaoTest {
    private TestFileNameProvider fileNameProvider;
    private QuestionDao questionDao;

    public CsvQuestionDaoTest() {
    }

    @BeforeEach
    void tearDown() {
        this.fileNameProvider = null;
        this.questionDao = null;
    }

    @DisplayName("testAppProperties")
    @Test
    public void testAppProperties() {
        this.fileNameProvider = new AppProperties("test_questions.csv");
        assertNotNull(fileNameProvider.getTestFileName());
    }

    @DisplayName("testCsvQuestionDao")
    @Test
    public void testCsvQuestionDao() {
        this.fileNameProvider = new AppProperties("test_questions.csv");
        this.questionDao = new CsvQuestionDao(fileNameProvider);
        assertEquals(questionDao.findAll().size(), 5);
    }
}

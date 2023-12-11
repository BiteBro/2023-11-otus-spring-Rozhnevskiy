import org.junit.jupiter.api.*;
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

    @AfterEach
    void tearDown() {
        this.fileNameProvider = null;
        this.questionDao = null;
    }

    @DisplayName("testAppProperties")
    @Test
    public void testAppProperties() {
        this.fileNameProvider = new AppProperties("questions.csv");
        assertNotNull(fileNameProvider.getTestFileName());
    }

    @DisplayName("testCsvQuestionDao")
    @Test
    public void testCsvQuestionDao() {
        this.questionDao = new CsvQuestionDao(fileNameProvider);
        assertEquals(questionDao.findAll().size(), 5);
    }
}

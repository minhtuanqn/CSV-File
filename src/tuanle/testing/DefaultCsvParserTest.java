package tuanle.testing;

import org.junit.Test;
import tuanle.CsvFileConfig;
import tuanle.CsvParser;
import tuanle.DefaultCsvParser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import static tuanle.testing.CsvTestUtils.createCsvFileConfig;

public class DefaultCsvParserTest {
    @Test
    public void whenParse_QuotedCsv_ThenCorrectlyParse() throws URISyntaxException, IOException {
        final CsvFileConfig parserConfig = createCsvFileConfig();
        parserConfig.setDelimiter("|");
        execute(parserConfig, "quoted.csv");
//        execute(parserConfig, "/quoted.csv");
    }

    @Test
    public void whenParse_UnquotedCsv_ThenCorrectlyParse() throws URISyntaxException, IOException {
        final CsvFileConfig parserConfig = createCsvFileConfig();
        parserConfig.setDelimiter("|");
        parserConfig.setQuoteMode(false);
        execute(parserConfig, "unquoted.csv");
//        execute(parserConfig, "/unquoted.csv");
    }

    @Test
    public void whenParse_CommaDelimiter_ThenCorrectlyParse() throws URISyntaxException, IOException {
        final CsvFileConfig parserConfig = createCsvFileConfig();
        parserConfig.setDelimiter(",");
//        execute(parserConfig, "/comma.csv");
        execute(parserConfig, "comma.csv");
    }

    @Test(expected = IOException.class)
    public void whenParse_NonExistingFile_ThenReject() throws URISyntaxException, IOException {
//        execute(createCsvFileConfig(), "/not-found.csv");
        execute(createCsvFileConfig(), "not-found.csv");
    }

    /**
     * The CSV to be considered as an abnormal file contains incorrect number
     * of segments not matching number of header columns, please refer to abnormal.csv
     * file for details
     */
    @Test(expected = IOException.class)
    public void whenParse_CorruptedFile_ThenReject() throws URISyntaxException, IOException {
        final CsvFileConfig parserConfig = new CsvFileConfig();
        parserConfig.setDelimiter(",");
        parserConfig.setQuoteMode(true);
//        execute(parserConfig, "/abnormal.csv");
        execute(parserConfig, "abnormal.csv");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInit_NullConfigParam_ThenReject() throws URISyntaxException {
//        final File file = Paths.get(DefaultCsvParserTest.class.getResource("/comma.csv").toURI()).toFile();
        File file = new File("comma.csv");
        new DefaultCsvParser(file, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInit_NullFileParam_ThenReject() {
        new DefaultCsvParser(null, createCsvFileConfig());
    }

    private void execute(CsvFileConfig config, String fileName) throws URISyntaxException, IOException {
        // Get test data from resources
//        final File file = Paths.get(DefaultCsvParserTest.class.getResource(fileName).toURI()).toFile();
        final  File file = new File(fileName);
        final CsvParser parser = new DefaultCsvParser(file, config);

        // Assert lines
        CsvLinesAssertion
                .create().expectLine(0)
                .atSegment(0, "FirstName")
                .atSegment(1, "LastName")
                .and().expectLine(1)
                .atSegment(0, "John")
                .atSegment(1, "Biden")
                .and().expectLine(2)
                .atSegment(0, "Donald")
                .atSegment(1, "Trump")
                .and().assertCsvLine(parser);

        // Close resources
        parser.close();
    }
}

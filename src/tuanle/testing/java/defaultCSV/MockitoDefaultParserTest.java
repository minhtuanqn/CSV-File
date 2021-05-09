package defaultCSV;

import defaultCSV.iml.DefaultCsvParser;
import model.CsvFileConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static defaultCSV.CsvTestUtils.createCsvFileConfig;

@RunWith(MockitoJUnitRunner.class)
public class MockitoDefaultParserTest {

    private CsvFileConfig fileConfig = Mockito.mock(CsvFileConfig.class);

    private File createFile(String fileName) {
        try {
            File file = Paths.get(DefaultCsvParserTest.class.getResource(fileName).toURI()).toFile();
            return file;
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void whenReadFile_WithQuote_ThenLineList() {
        File file = createFile("/quoted.csv");

        Mockito.when(fileConfig.getDelimiter()).thenReturn("|");
        Mockito.when(fileConfig.isQuoteMote()).thenReturn(true);

        DefaultCsvParser defaultCsvParserTest = new DefaultCsvParser(file, fileConfig);

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
                .and().assertCsvLine(defaultCsvParserTest);

    }

    @Test
    public void whenParse_UnquotedCsv_ThenCorrectlyParse() throws URISyntaxException, IOException {
        final CsvFileConfig parserConfig = createCsvFileConfig();

        File file = createFile("/unquoted.csv");

        Mockito.when(fileConfig.getDelimiter()).thenReturn("|");
        Mockito.when(fileConfig.isQuoteMote()).thenReturn(false);
        DefaultCsvParser defaultCsvParserTest = new DefaultCsvParser(file, fileConfig);

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
                .and().assertCsvLine(defaultCsvParserTest);
    }


}

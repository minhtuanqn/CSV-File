package tuanle.testing;

import org.junit.Test;
import tuanle.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import java.util.Scanner;
import java.util.function.Consumer;

import static tuanle.testing.CsvTestUtils.createTestFile;
import static tuanle.testing.CsvTestUtils.writeWithAssertion;
import static tuanle.testing.CsvTestUtils.createCsvFileConfig;
import static tuanle.testing.CsvTestUtils.createDumpCsvLines;

public class DefaultCsvWriterTest {
    @Test
    public void whenWriteSingle_QuoteModeOn_ThenWrite() throws IOException {
        execute(createCsvFileConfig(),
                writer -> writeWithAssertion(writer, createDumpCsvLines().get(0)),
                actual -> {
                    assertEquals(1, actual.size());
                    assertEquals("\"FirstName\",\"LastName\"", actual.get(0));
                });
    }

    @Test
    public void whenWriteSingle_QuoteModeOff_ThenWrite() throws IOException {
        final CsvFileConfig csvFileConfig = createCsvFileConfig();
        csvFileConfig.setQuoteMode(false);
        execute(csvFileConfig,
                writer -> writeWithAssertion(writer, createDumpCsvLines().get(0)),
                actual -> {
                    assertEquals(1, actual.size());
                    assertEquals("FirstName,LastName", actual.get(0));
                });
    }

    @Test
    public void whenWriteSingle_PipeLineDelimiter_ThenWrite() throws IOException {
        final CsvFileConfig csvFileConfig = createCsvFileConfig();
        csvFileConfig.setDelimiter("|");
        execute(csvFileConfig,
                writer -> writeWithAssertion(writer, createDumpCsvLines().get(0)),
                actual -> {
                    assertEquals(1, actual.size());
                    assertEquals("\"FirstName\"|\"LastName\"", actual.get(0));
                });

    }

    @Test
    public void whenWriteMultiple_QuoteModeOn_ThenWrite() throws IOException {
        execute(createCsvFileConfig(),
                writer -> writeWithAssertion(writer, createDumpCsvLines()),
                actual -> {
                    assertEquals(2, actual.size());
                    assertEquals("\"FirstName\",\"LastName\"", actual.get(0));
                    assertEquals("\"John\",\"Biden\"", actual.get(1));
                });

    }

    @Test
    public void whenWriteMultiple_QuoteModeOff_ThenWrite() throws IOException {
        final CsvFileConfig csvFileConfig = createCsvFileConfig();
        csvFileConfig.setQuoteMode(false);
        execute(csvFileConfig,
                writer -> writeWithAssertion(writer, createDumpCsvLines()),
                actual -> {
                    assertEquals(2, actual.size());
                    assertEquals("FirstName,LastName", actual.get(0));
                    assertEquals("John,Biden", actual.get(1));
                });
    }

    @Test
    public void whenWriteMultiple_PipelineDelimiter_ThenWrite() throws IOException {
        final CsvFileConfig csvFileConfig = createCsvFileConfig();
        csvFileConfig.setDelimiter("|");
        execute(csvFileConfig,
                writer -> writeWithAssertion(writer, createDumpCsvLines()),
                actual -> {
                    assertEquals(2, actual.size());
                    assertEquals("\"FirstName\"|\"LastName\"", actual.get(0));
                    assertEquals("\"John\"|\"Biden\"", actual.get(1));
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInit_NullConfigParam_ThenReject() throws IOException {
        new DefaultCsvParser(createTestFile(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInit_NullFileParam_ThenReject() {
        new DefaultCsvParser(null, createCsvFileConfig());
    }

    @Test(expected = IOException.class)
    public void whenWriteSingle_NonExistingFile_ThenReject() throws IOException {
        final CsvWriter writer = new DefaultCsvWriter(new File("non-existing.csv"), createCsvFileConfig());
        writer.write(new CsvLine());
    }

    @Test(expected = IOException.class)
    public void whenWriteMultiple_NonExistingFile_ThenReject() throws IOException {
        final CsvWriter writer = new DefaultCsvWriter(new File("non-existing.csv"), createCsvFileConfig());
        writer.write(Arrays.asList(new CsvLine(), new CsvLine()));
    }

    private void execute(CsvFileConfig csvFileConfig, Consumer<CsvWriter> writerConsumer, Consumer<List<String>> assertion) throws IOException {
        final List<String> actualLines = new ArrayList<>();
        final File testFile = createTestFile();
        final CsvWriter writer = new DefaultCsvWriter(testFile, csvFileConfig);

        writerConsumer.accept(writer);
        writer.close();

        try (Scanner scanner = new Scanner(testFile)) {
            while (scanner.hasNext()) {
                actualLines.add(scanner.next());
            }
        } finally {
            writer.close();
            assertion.accept(actualLines);
        }
    }
}


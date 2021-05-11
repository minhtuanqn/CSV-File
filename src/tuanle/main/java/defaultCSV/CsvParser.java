package defaultCSV;

import model.CsvFileConfig;
import model.CsvLine;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Used for iterating through every lines in the CSV file and close resource
 * to release memory after done processing with records, please refer these
 */
public interface CsvParser extends Iterator<CsvLine>, Closeable {
    List<CsvLine> readFile(CsvFileConfig fileConfig) throws IOException;
}

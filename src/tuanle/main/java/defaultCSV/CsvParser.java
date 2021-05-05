package defaultCSV;

import model.CsvLine;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Used for iterating through every lines in the CSV file and close resource
 * to release memory after done processing with records, please refer these
 */
public interface CsvParser extends Iterator<CsvLine>, Closeable {

}

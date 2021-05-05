import defaultCSV.CsvWriter;
import defaultCSV.iml.DefaultCsvWriter;
import model.CsvLine;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) {

        /**
         * Test read and parse from file
         */
        // Initialize parser
//        final File file = new File("foo.csv");
//        final CsvParser parser = new DefaultCsvParser(file);
//
//
//        // Iterate through each lines
//        while (parser.hasNext()) {
//            final CsvLine line = parser.next();
//            // Print value of the first segment
//            System.out.println(line.get(0));
//        }
//
//        // Close resource after reading a file
//        try {
//            parser.close();
//        } catch (IOException e) {
//            Logger logger = Logger.getLogger("ParserLogger");
//            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
//        }

        /**
         * Test write to file
         */
        try {
            // Initialize writer
            final File file = new File("foo.csv");
            final CsvWriter writer = new DefaultCsvWriter(file);

            // Set value for the first segment
            final CsvLine line = new CsvLine();
            line.set(0, "bar");

            // Write data into a file
            writer.write(line);

            // Close resources
            writer.close();
            System.out.println("Write to file successfully");
        }
        catch (IOException e) {
            System.out.println("ok");
            Logger logger = Logger.getLogger("WriterLogger");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }

    }
}

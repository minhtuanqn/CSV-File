package tuanle;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Implement CSV writing logic here
 */
public class DefaultCsvWriter implements CsvWriter{

    private File file;
    private PrintWriter printWriter = null;
    private CsvFileConfig csvFileConfig = null;

    /**
     * Default constructor
     */
    public DefaultCsvWriter() {
    }

    /**
     * Constructor with 1 parameter
     * @param file
     */
    public DefaultCsvWriter(File file) {
        this.file = file;
    }

    /**
     * Constructor with 2 parameters
     * @param file
     * @param csvFileConfig
     */
    public DefaultCsvWriter(File file, CsvFileConfig csvFileConfig) {
        this.file = file;
        this.csvFileConfig = csvFileConfig;
    }

    /**
     * get delimiter from file config
     * @param fileConfig
     * @return
     */
    private String getDelimiter(CsvFileConfig fileConfig) {
        String delimiter = ",";
        //Define delimiter
        if(csvFileConfig != null && csvFileConfig.getDelimiter() != null
                && !csvFileConfig.getDelimiter().equals("")) {
            delimiter = csvFileConfig.getDelimiter();
        }
        return delimiter;
    }

    /**
     * define status of double quote is on or off
     * @param csvFileConfig
     * @return
     */
    private boolean defineStatusOfQuote(CsvFileConfig csvFileConfig) {
        boolean quotedMote = false;
        //Check doubleQuoteMode
        if(csvFileConfig != null && csvFileConfig.isQuoteMote() == true) {
            quotedMote = true;
        }
        return quotedMote;
    }

    /**
     * Check available of file and writer object for writing to file
     * @throws IOException
     */
    private void checkFileAndWriterObject() throws IOException{
        if(this.file == null || !this.file.exists()) {
            throw new IOException();
        }

        if(printWriter == null) {
            printWriter = new PrintWriter(file);
        }
    }

    /**
     * Method write one line to file
     * @param line
     * @throws IOException
     */
    @Override
    public void write(CsvLine line) throws IOException {
        String delimiter = getDelimiter(csvFileConfig);
        boolean quotedMote = defineStatusOfQuote(csvFileConfig);

        checkFileAndWriterObject();

        List<String> stringList = line.getStringList();
        if(stringList != null && stringList.size() > 0) {
            String details = "";
            String symbol = "";
            if(quotedMote == true) {
                symbol = "\"";
            }

            for (String element: stringList) {
                if(!details.equals("")) {
                    details = details + delimiter + symbol + element + symbol;
                }
                else {
                    details = details + symbol + element + symbol;
                }
            }

            printWriter.write(details);
        }
    }

    /**
     * Method write multiple of lines to file
     * @param line
     * @throws IOException
     */
    @Override
    public void write(Collection<CsvLine> line) throws IOException {
        checkFileAndWriterObject();
        if(line != null && line.size() > 0) {

            String delimiter = getDelimiter(csvFileConfig);
            boolean quotedMote = defineStatusOfQuote(csvFileConfig);

            Iterator<CsvLine> iterator = line.iterator();
            String detail = "";
            while (iterator.hasNext()) {
                detail = "";
                String symbol = "";
                if(quotedMote == true) {
                    symbol = "\"";
                }
                List<String> stringList = iterator.next().getStringList();
                if(stringList != null) {
                    for (String element: stringList) {
                        if (!detail.equals("")) {
                            detail = detail + delimiter + symbol + element + symbol;
                        }
                        else {
                            detail = detail + symbol + element + symbol;
                        }
                    }
                }
                printWriter.println(detail);
            }
        }
    }

    /**
     * Close resources
     * @throws IOException
     */
    @Override
    public void close() throws IOException{
        if(printWriter != null) {
            printWriter.close();
        }
    }
}

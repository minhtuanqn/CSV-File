package defaultCSV.iml;

import defaultCSV.CsvWriter;
import model.CsvFileConfig;
import model.CsvLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Implement CSV writing logic here
 */
public class DefaultCsvWriter extends DefaultCsvSupporter implements CsvWriter{

    private File file;
    private PrintWriter printWriter = null;
    private CsvFileConfig csvFileConfig = null;

    public DefaultCsvWriter() {
    }

    public DefaultCsvWriter(File file) {
        this.file = file;
    }


    public DefaultCsvWriter(File file, CsvFileConfig csvFileConfig) {
        this.file = file;
        this.csvFileConfig = csvFileConfig;
    }


    /**
     * Check available of file
     *
     * @throws IOException
     */
    private void checkFileAndConnection() throws IOException {
        if (this.file == null || !this.file.exists()) {
            throw new IOException();
        }
        if (printWriter == null) {
            printWriter = new PrintWriter(file);
        }
    }

    /**
     * Method write one line to file
     *
     * @param line
     * @throws IOException
     */
    @Override
    public void write(CsvLine line) throws IOException {
        checkFileAndConnection();
        String delimiter = getDelimiter(csvFileConfig);
        boolean quotedMote = defineStatusOfQuote(csvFileConfig);

        List<String> stringList = line.getStringList();
        if (stringList != null && stringList.size() > 0) {
            String details = "", symbol = "";
            if (quotedMote == true) {
                symbol = "\"";
            }
            for (String element : stringList) {
                if (!details.equals("")) {
                    details = details + delimiter + symbol + element + symbol;
                } else {
                    details = details + symbol + element + symbol;
                }
            }
            printWriter.write(details);
            printWriter.flush();
        }

    }

    /**
     * Method write multiple of lines to file
     *
     * @param line
     * @throws IOException
     */
    @Override
    public void write(Collection<CsvLine> line) throws IOException {
        checkFileAndConnection();
        if (line != null && line.size() > 0) {
            String delimiter = getDelimiter(csvFileConfig);
            boolean quotedMote = defineStatusOfQuote(csvFileConfig);

            Iterator<CsvLine> iterator = line.iterator();
            while (iterator.hasNext()) {
                String detail = "", symbol = "";

                if (quotedMote == true) {
                    symbol = "\"";
                }
                List<String> stringList = iterator.next().getStringList();
                if (stringList != null) {
                    for (String element : stringList) {
                        if (!detail.equals("")) {
                            detail = detail + delimiter + symbol + element + symbol;
                        } else {
                            detail = detail + symbol + element + symbol;
                        }
                    }
                }
                printWriter.println(detail);
                printWriter.flush();
            }
        }
    }

    /**
     * Close resources
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (printWriter != null) {
            printWriter.close();
        }
    }
}

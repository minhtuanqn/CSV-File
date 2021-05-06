package defaultCSV.iml;

import defaultCSV.CsvParser;
import model.CsvFileConfig;
import model.CsvLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Class DefaultCsvParser implements CsvParser interface
 * Implement CSV parsing logic here
 */
public class DefaultCsvParser extends DefaultCsvUtils implements CsvParser {

    private File file;
    private FileReader fileReader = null;
    private BufferedReader bufferedReader = null;
    private List<CsvLine> csvLineList = null;
    private int curFlag;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCsvParser.class);


    /**
     * @param file
     * @throws IOException
     */
    public DefaultCsvParser(File file) throws IOException {
        this.file = file;
        this.csvLineList = readFile(null);
        this.curFlag = 0;
    }

    /**
     * @param file
     * @param fileConfig
     * @throws IOException
     */
    public DefaultCsvParser(File file, CsvFileConfig fileConfig) {
        this.file = file;
        try {
            this.csvLineList = readFile(fileConfig);
        } catch (IOException e) {
            String message = "ERROR IOException from read file function: ";
            LOGGER.error(message, e.getStackTrace());
        }
        this.curFlag = 0;
    }

    /**
     * Check available of file and connection for read file
     *
     * @param fileConfig
     * @throws IOException
     */
    private void checkFileAndConnection(CsvFileConfig fileConfig) throws IOException {
        if (fileConfig == null) {
            throw new IllegalArgumentException();
        }
        if (file == null) {
            throw new IllegalArgumentException();
        }
        if (!file.exists()) {
            throw new IOException();
        }
        if (fileReader == null) {
            fileReader = new FileReader(file);
        }
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(fileReader);
        }
    }




    /**
     * Read file for each line in file and then each line is set to one CsvLine Object
     * Set all CsvLine object in file to list
     *
     * @param fileConfig
     * @return list of CsvLine object
     * @throws IOException
     */
    public List<CsvLine> readFile(CsvFileConfig fileConfig) throws IOException {
        checkFileAndConnection(fileConfig);
        String delim = getDelimiter(fileConfig);
        boolean doubleQuoteMode = defineStatusOfQuote(fileConfig);

        List<CsvLine> lineList = new ArrayList<>();
        String detail = "";
        while ((detail = bufferedReader.readLine()) != null) {
            CsvLine line = new CsvLine();
            List<String> stringList = new ArrayList<>();
            StringTokenizer stk = new StringTokenizer(detail, delim);
            while (stk.hasMoreTokens()) {
                String element = stk.nextToken();
                if (doubleQuoteMode) {
                    if (element.length() > 1 && element.startsWith("\"") && element.endsWith("\"")) {
                        element = element.substring(1, element.length() - 1);
                    }
                }
                stringList.add(element);
            }
            line.setStringList(stringList);
            lineList.add(line);
            checkNormalFile(lineList);
        }
        return lineList;
    }


    /**
     * Check exist or not of next element
     *
     * @return
     */
    @Override
    public boolean hasNext() throws NoSuchElementException {
        if (this.csvLineList != null) {
            if (this.csvLineList.size() - 1 >= this.curFlag) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return next element of collection
     * @throws NoSuchElementException
     */
    @Override
    public CsvLine next() throws NoSuchElementException {
        if (this.csvLineList.size() - 1 >= this.curFlag) {
            CsvLine csvLine = this.csvLineList.get(this.curFlag);
            this.curFlag++;
            return csvLine;
        }
        return null;
    }

    /**
     * Close resources
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (fileReader != null) {
            fileReader.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }
}

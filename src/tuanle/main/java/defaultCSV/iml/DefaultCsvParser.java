package defaultCSV.iml;

import defaultCSV.CsvParser;
import model.CsvFileConfig;
import model.CsvLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Class DefaultCsvParser implements CsvParser interface
 * Implement CSV parsing logic here
 */
public class DefaultCsvParser implements CsvParser {

    private File file;
    private BufferedReader bufferedReader = null;
    private FileReader fileReader = null;
    private List<CsvLine> csvLineList = null;
    private int curFlag;


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
            Logger logger = Logger.getLogger("ParserLogger");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        this.curFlag = 0;
    }

    /**
     * Check available of file and connection for read file
     * @param fileConfig
     * @throws IOException
     */
    private void checkConnectionForRead(CsvFileConfig fileConfig) throws IOException{
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
     * Check file is normal or not
     * @param csvLineList
     * @throws IOException
     */
    private void checkNormalFile(List<CsvLine> csvLineList) throws IOException {
        if(csvLineList != null && csvLineList.size() > 0) {
            int numberOfHeader = csvLineList.get(0).getStringList().size();
            for (int count = 1; count < csvLineList.size(); count++) {
                if(numberOfHeader != csvLineList.get(count).getStringList().size()) {
                    throw new IOException();
                }
            }
        }
    }


    private String getDelemiter(CsvFileConfig fileConfig) {
        String delim = ",";
        if (fileConfig.getDelimiter() != null
                && !fileConfig.getDelimiter().equals("")) {
            delim = fileConfig.getDelimiter();
        }
        return delim;
    }

    /**
     * Check status of double quote mote is on of off
     * @param fileConfig
     * @return
     */
    private boolean defineStatusQuote(CsvFileConfig fileConfig) {
        boolean doubleQuoteMode = false;
        if (fileConfig.isQuoteMote()) {
            doubleQuoteMode = true;
        }
        return doubleQuoteMode;
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
        checkConnectionForRead(fileConfig);
        String delim = getDelemiter(fileConfig);
        boolean doubleQuoteMode = defineStatusQuote(fileConfig);
        String detail = "";

        List<CsvLine> csvLineList = new ArrayList<>();
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
            csvLineList.add(line);
            checkNormalFile(csvLineList);
        }
        return csvLineList;

    }


    /**
     * Check exist or not of next element
     *
     * @return
     */
    @Override
    public boolean hasNext() throws NoSuchElementException{
        if(this.csvLineList != null) {
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
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (fileReader != null) {
            fileReader.close();
        }
    }
}

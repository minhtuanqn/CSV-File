package tuanle;

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
public class DefaultCsvParser implements CsvParser{

    private File file;
    private BufferedReader bufferedReader = null;
    private FileReader fileReader = null;
    private List<CsvLine> csvLineList = null;
    private int curFlag;


    /**
     *
     * @param file
     * @throws IOException
     */
    public DefaultCsvParser(File file){
        this.file = file;
        this.csvLineList = readFile(null);
        this.curFlag = 0;
    }

    /**
     *
     * @param file
     * @param fileConfig
     * @throws IOException
     */
    public DefaultCsvParser(File file, CsvFileConfig fileConfig){
        this.file = file;
        this.csvLineList = readFile(fileConfig);
        this.curFlag = 0;
    }

    /**
     * Read file for each line in file and then each line is set to one CsvLine Object
     * Set all CsvLine object in file to list
     * @param fileConfig
     * @return list of CsvLine object
     * @throws IOException
     */
    public List<CsvLine> readFile(CsvFileConfig fileConfig){
        if(file == null) {
            System.out.println("This file does not exist");
            return null;
        }
        else {
            try {
                String delim = ",";
                boolean doubleQuoteMode = false;
                if(fileReader == null) {
                    fileReader = new FileReader(file);
                }
                if(bufferedReader == null) {
                    bufferedReader = new BufferedReader(fileReader);
                }
                if(fileConfig != null && fileConfig.getDelimiter() != null
                        && !fileConfig.getDelimiter().equals("")) {
                    delim = fileConfig.getDelimiter();
                }
                if(fileConfig != null && fileConfig.isQuoteMote()) {
                    doubleQuoteMode = true;
                }
                String detail = "";

                List<CsvLine> csvLineList = new ArrayList<>();
                while((detail = bufferedReader.readLine()) != null) {
                    CsvLine line = new CsvLine();
                    List<String> stringList = new ArrayList<>();
                    StringTokenizer stk = new StringTokenizer(detail, delim);
                    while(stk.hasMoreTokens()) {
                        String element = stk.nextToken();
                        if(doubleQuoteMode) {
                            if (element.length() > 1 && element.startsWith("\"") && element.endsWith("\"")) {
                                element = element.substring(1, element.length() - 1);
                            }
                        }
                        stringList.add(element);
                    }
                    line.setStringList(stringList);
                    csvLineList.add(line);
                }
                return  csvLineList;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Check exist or not of next element
     * @return
     */
    @Override
    public boolean hasNext() {
        if(this.csvLineList.size() - 1 >= this.curFlag) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return next element of collection
     * @throws NoSuchElementException
     */
    @Override
    public CsvLine next() throws NoSuchElementException {
        if(this.csvLineList.size() - 1 >= this.curFlag) {
            CsvLine csvLine = this.csvLineList.get(this.curFlag);
            this.curFlag++;
            return csvLine;
        }
        return null;
    }

    /**
     * Close resources
     * @throws IOException
     */
    @Override
    public void close() throws IOException{
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if(fileReader != null) {
            fileReader.close();
        }
    }
}

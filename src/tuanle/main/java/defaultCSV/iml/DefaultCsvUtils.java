package defaultCSV.iml;

import model.CsvFileConfig;
import model.CsvLine;

import java.io.IOException;
import java.util.List;

abstract class DefaultCsvUtils {
    /**
     * get delimiter from file config
     *
     * @param csvFileConfig
     * @return
     */
     String getDelimiter(CsvFileConfig csvFileConfig) {
        String delimiter = ",";
        //Define delimiter
        if (csvFileConfig != null && csvFileConfig.getDelimiter() != null
                && !csvFileConfig.getDelimiter().equals("")) {
            delimiter = csvFileConfig.getDelimiter();
        }
        return delimiter;
     }

    /**
     * define status of double quote is on or off
     *
     * @param csvFileConfig
     * @return
     */
     boolean defineStatusOfQuote(CsvFileConfig csvFileConfig) {
        boolean quotedMote = false;
        //Check doubleQuoteMode
        if (csvFileConfig != null && csvFileConfig.isQuoteMote() == true) {
            quotedMote = true;
        }
        return quotedMote;
     }

    /**
     * Check csv file is normal or not
     *
     * @param csvLineList
     * @throws IOException
     */
    void checkNormalFile(List<CsvLine> csvLineList) throws IOException {
        if (csvLineList != null && csvLineList.size() > 0) {
            int numberOfHeader = csvLineList.get(0).getStringList().size();
            for (int count = 1; count < csvLineList.size(); count++) {
                if (numberOfHeader != csvLineList.get(count).getStringList().size()) {
                    throw new IOException();
                }
            }
        }
    }
}

package tuanle;

/**
 * Configuration used to determine parsing strategy and writing strategy
 */

public class CsvFileConfig {

    private String delimiter;
    private boolean quoteMote;

    /**
     * Default constructor
     */
    public CsvFileConfig() {
    }

    /**
     * Constructor with two parameter
     * @param delimiter
     * @param quoteMote
     */
    public CsvFileConfig(String delimiter, boolean quoteMote) {
        this.delimiter = delimiter;
        this.quoteMote = quoteMote;
    }

    /**
     *
     * @return character between segments
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * Character as splitting character between segments
     *
     * @param delimiter Delimiter character
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     *
     * @return status of double quote is true or false
     */
    public boolean isQuoteMote() {
        return quoteMote;
    }

    /**
     * Is data wrapped with double quotes
     *
     * @param quoted true is double-quotes-wrapped data, otherwise false
     */
    public void setQuoteMode(boolean quoted) {
        this.quoteMote = quoted;
    }


}

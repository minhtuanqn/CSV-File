package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Each CSVLine is each line in file .csv
 */
public class CsvLine {

    /**
     * list of strings are splitted by delimiter
     */
    private List<String> stringList;

    /**
     * Default constructor
     */
    public CsvLine() {}

    /**
     *
     * @return list of strings are splitted by delimiter
     */
    public List<String> getStringList() {
        return stringList;
    }

    /**
     * Set list of strings are splitted by delimiter to CsvLine
     * @param stringList
     */
    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }


    /**
     * Get data at specific position in a row
     *
     * @param index Position of segment
     * @return Data
     */
    public String get(int index) {
        if(stringList != null && stringList.size() >= index) {
            return stringList.get(index);
        }
        return null;
    }

    /**
     * Set data in a specific position
     *
     * @param position Position
     * @param data     Data
     */
    public void set(int position, String data) {
        if(stringList == null) {
            stringList = new ArrayList<>();
        }
        if(stringList != null && stringList.size() >= position) {
            stringList.add(position, data);
        }
    }

}

package filescounter.implementation.counterlogic;


import java.io.IOException;

/**
 *  Describes basic actions available for processing data (read, write and display results).
 */
public abstract class DataProcessor {
    /**
     * Holds path to a source file from which data will be read.
     */
    protected String sourceFile;
    /**
     * Holds path to a file to which results will be written.
     */
    protected String resultsFile;
    /**
     * Holds index of record that will be displayed in results table.
     */
    protected volatile int recordIndex = 0;


    public DataProcessor(String sourceFile, String resultsFile) {
        this.sourceFile = sourceFile;
        this.resultsFile = resultsFile;
    }

    /**
     * Responsible for reading source file.
     * @throws IOException
     */
    public abstract void readData() throws IOException;

    /**
     * Writes results of counting.
     * @throws IOException
     */
    public abstract void writeData() throws IOException;

    /**
     * Prints results of the count to the console.
     * @param numberOfFiles holds result of counting
     * @param filePath holds path to the directory for which files counting performed
     */
    public final synchronized void displayResults(int numberOfFiles, String filePath) {
        System.out.println(++recordIndex + " " + numberOfFiles + " "+ filePath);
    }

}

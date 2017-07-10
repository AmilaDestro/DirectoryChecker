package com.mobileeffort.testtask;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public abstract class FilesCounter {
    protected String sourceFile;
    protected String resultsFile;
    protected ConcurrentHashMap<String,Integer> countedFiles = new ConcurrentHashMap<>();
    protected static volatile boolean escStatus;



    public FilesCounter(String sourceFile, String resultsFile) {
        this.sourceFile = sourceFile;
        this.resultsFile = resultsFile;
    }

    /*
     * Responsible for reading source file and creating new threads that count number of files by each path,
     * included to the file
     */
    public abstract void readData() throws InterruptedException;

    /*
     * Writes counted files that are stored in the countedFiles map to the file
     */
    public abstract void writeData();

    /*
     * Prints results of the count to the console
     */
    public void displayResults() throws IOException{
        int index = 0;
        BufferedReader readResultsFile = new BufferedReader(new FileReader(resultsFile));
        while (readResultsFile.ready()) {
            String data = readResultsFile.readLine();
            String[] stringContent = data.split(";");
            System.out.println(++index + " " + stringContent[1] + " "+ stringContent[0]);
        }
        if (!isEscPressed()) {
            System.out.println("Counting files is finished. Press <Esc> to shutdown the application.");
        }
    }

    /*
     * Indicates whether Esc key was pressed by the user
     * @return
     */
    protected boolean isEscPressed() {
        return escStatus;
    }

    /*
     * Allows threads to change value of escStatus variable
     * @param statement
     */
    protected static synchronized void setIsEscPressed(boolean statement) {
        escStatus = statement;
    }
}

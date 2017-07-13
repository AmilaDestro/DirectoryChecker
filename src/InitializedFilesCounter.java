package com.mobileeffort.filescounter.counterlogic;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements all abstract methods of the superclass and includes inner class that implements the Countable interface
 */
public class InitializedFilesCounter extends DataProcessor {
    private static volatile boolean escStatus;
    private int recordsQuantity;
    private ConcurrentHashMap<String,Integer> countedFiles = new ConcurrentHashMap<>();


    public InitializedFilesCounter(String sourceFile, String resultsFile) {
        super(sourceFile, resultsFile);
    }


    @Override
    public void readData() {
       try (BufferedReader fileReader = new BufferedReader(new FileReader(sourceFile))) {
          while (fileReader.ready()) {
              ++recordsQuantity;
              String path = fileReader.readLine();
//              new CounterThread(path);
              new Thread(new CounterThread2(this, path)).start();
          }
       } catch (IOException e) {
           System.out.println(e.getMessage());
           System.out.println("Press <Esc> to shutdown the application.");
       }
    }


    @Override
    public synchronized void writeData() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(resultsFile))) {
            for (Map.Entry<String, Integer> pathsAndFiles: countedFiles.entrySet()) {
                    String path = pathsAndFiles.getKey();
                    int numberOfFiles = pathsAndFiles.getValue();
                    fileWriter.write(path + ";" + numberOfFiles);
                    fileWriter.newLine();

            }
        } catch (IOException e) {
                System.out.println(e.getMessage());
        }

    }

    /*
     * Indicates whether Esc key was pressed by the user
     * @return
     */
    public static synchronized boolean getEscStatus() {
        return escStatus;
    }

    /*
     * Allows threads to change value of escStatus variable
     * @param statement
     */
    public static synchronized void setEscStatus(boolean statement) {
        escStatus = statement;
    }

    public synchronized int getRecordsQuantity() {
        return recordsQuantity;
    }

    public synchronized int getRecordIndex() {
        return recordIndex;
    }

    public synchronized void updateCountedFiles(String path, Integer numberOfFiles) {
        countedFiles.put(path, numberOfFiles);
    }

    /**
     * Responsible for counting files, record and display of results.
     */
    public class CounterThread extends Thread implements Countable {
        private final String filePath;
        private int numberOfFiles;

        private CounterThread(String filePath) {
            this.filePath = filePath;
            start();
        }

        @Override
        public void run() {
            try {
                countFiles(filePath);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            displayResults(numberOfFiles, filePath);
            writeData();
            if (recordsQuantity == recordIndex & !getEscStatus()) {
                System.out.println("Counting files finished. Press <Esc> to shutdown the application.");
            }
        }


        @Override
        public void countFiles(String filePath) throws InterruptedException {
            File file = new File(filePath);
            // if !file.isDirectory
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File currentFile: listFiles){
                    if (getEscStatus()) {
                        break;
                    }
                    if (currentFile.isFile()){
                        numberOfFiles++;
                    }
                    if (currentFile.isDirectory()){
                        countFiles(currentFile.getPath());

                    }
                    countedFiles.put(this.filePath, numberOfFiles);
                    sleep(15);
                }
            }
        }
    }
}

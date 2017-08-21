package filescounter.implementation.counterlogic;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements all abstract methods of the DataProcessor, creates threads that are capable to count files and includes
 * map that holds results of counting.
 */
public class InitializedFilesCounter extends DataProcessor {
    /**
     * Indicates whether ESC button was pressed.
     */
    private static volatile boolean escStatus;
    /**
     * Holds actual quantity of records in the source file.
     */
    private int recordsQuantity;
    /**
     * Holds results of counting (files quantity by each path).
     */
    private ConcurrentHashMap<String,Integer> countedFiles = new ConcurrentHashMap<>();


    public InitializedFilesCounter(String sourceFile, String resultsFile) {
        super(sourceFile, resultsFile);
    }


    @Override
    public void readData() throws IOException{
       try (BufferedReader fileReader = new BufferedReader(new FileReader(sourceFile))) {
          while (fileReader.ready()) {
              ++recordsQuantity;
              String path = fileReader.readLine();
              new Thread(new CounterThread(this, path)).start();
          }
       } catch (FileNotFoundException e) {
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
                System.out.println("Data wasn't recorded to the file!");
        }

    }


    static synchronized boolean getEscStatus() {
        return escStatus;
    }

    /**
     * Allows to change value of escStatus variable if ESC key was pressed by the user
     * @param statement holds true if Esc key was pressed and false if it wasn't
     */
    public static synchronized void setEscStatus(boolean statement) {
        escStatus = statement;
    }

    synchronized int getRecordsQuantity() {
        return recordsQuantity;
    }

    synchronized int getRecordIndex() {
        return recordIndex;
    }

    /**
     * Puts results of files counting to the countedFiles map
     * @param path holds path to directory for which files counting performed
     * @param numberOfFiles holds number of counted files
     */
    synchronized void updateCountedFiles(String path, Integer numberOfFiles) {
        countedFiles.put(path, numberOfFiles);
    }
}

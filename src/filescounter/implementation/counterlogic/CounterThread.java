package filescounter.implementation.counterlogic;


import java.io.File;

/**
 * Implements logic of files counting, writes and displays results of counting
 */
public class CounterThread implements Countable, Runnable {
    /**
     * Holds link to existing InitializedFilesCounter object
     */
    private InitializedFilesCounter filesCounter;
    /**
     * Holds path to directory for which number of interior files will be counted
     */
    private String filePath;
    /**
     * Holds result of counting
     */
    private int numberOfFiles;


    CounterThread(InitializedFilesCounter filesCounter, String filePath) {
        this.filesCounter = filesCounter;
        this.filePath = filePath;
    }


    @Override
    public void run() {
        try {
            countFiles(filePath);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        filesCounter.displayResults(numberOfFiles, filePath);
        filesCounter.writeData();
        if (filesCounter.getRecordsQuantity() == filesCounter.getRecordIndex() & !InitializedFilesCounter
                .getEscStatus()) {
            System.out.println("Counting files finished. Press <Esc> to shutdown the application.");
        }
    }


    @Override
    public void countFiles(String path) throws InterruptedException {
        File file = new File(path);
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File currentFile: listFiles) {
                if (InitializedFilesCounter.getEscStatus()) {
                    break;
                }
                if (currentFile.isFile()) {
                    numberOfFiles++;
                }
                if (currentFile.isDirectory()) {
                    countFiles(currentFile.getPath());

                }
                filesCounter.updateCountedFiles(filePath, numberOfFiles);
                Thread.sleep(10);
            }
        }
    }
}

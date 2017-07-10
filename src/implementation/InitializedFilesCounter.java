package implementation;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class InitializedFilesCounter extends FilesCounter {

    public InitializedFilesCounter(String sourceFile, String resultsFile) {
        super(sourceFile, resultsFile);
    }


    @Override
    public void readData() throws InterruptedException{
       try (BufferedReader fileReader = new BufferedReader(new FileReader(sourceFile))) {
          while (fileReader.ready()) {
              String path = fileReader.readLine();
              CountThread countThread = new CountThread(path);
              countThread.setPriority(Thread.MAX_PRIORITY);
              countThread.start();
              countThread.join();
          }
       } catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }


    @Override
    public void writeData() {
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


    public class CountThread extends Thread {
        private String filePath;
        private int numberOfFiles;

        private CountThread(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void run() {
            try {
                countedFiles.put(filePath, countFiles(filePath));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        private int countFiles(String filePath) throws InterruptedException{
            File file = new File(filePath);
            File[] listFiles = file.listFiles();

            for (File currentFile: listFiles){
                if (isEscPressed()) {
                    break;
                }
                if (currentFile.isFile()){
                    numberOfFiles++;
                }
                if (currentFile.isDirectory()){
                    countFiles(currentFile.getPath());
                }
                sleep(10);
            }
            return numberOfFiles;
        }
    }
}

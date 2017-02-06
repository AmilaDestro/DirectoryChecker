package filesforanalysis;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class FilesCounter processes records of the input file and writes counted files in every path to the output file.*/

public class FilesCounter {
    public static final FilesCounter counter = new FilesCounter();
    public static final IncomingData data = new IncomingData();
    private ArrayList<String> paths = new ArrayList<>();
    private static HashMap<String, Integer> countedFiles = new HashMap<>();
    private static int numberOfFiles;
    private static boolean escStatus = false;
    private static boolean searchFinished = false;

    private FilesCounter(){}


    public static boolean isSearchFinished(){
        return searchFinished;
    }

    //reads info from the input file
    public final void readSourceFile(){
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(data.getSourceFileName())))){
            String line;
            while ((line = reader.readLine()) != null){
                paths.add(line);
            }

        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    //each path included to the input file gets passed to the countFile method
    public final void processPaths(){
        System.out.println("Files count started. Wait until it's finished.");
        System.out.println("Press <Esc> if you want to interrupt the process.");

        for (String path: counter.paths){
            numberOfFiles = 0;
            countFiles(path);
            countedFiles.put(path, numberOfFiles);
            if (isEscPressed()) break;
        }
    }

    //counts inner files for every path
    private Integer countFiles(String filePath){
        File file = new File(filePath);
        File[] listFiles = file.listFiles();

        try{
            for (File currentFile: listFiles){
              if (isEscPressed()){
                break;
              }
              if (currentFile.isFile()){
                numberOfFiles++;
              }
              if (currentFile.isDirectory()){
                countFiles(currentFile.getPath());
              }
               try {
                 Thread.sleep(20);
               }catch (InterruptedException e){
                  System.out.println(e.toString());
              }
            }
        }catch (NullPointerException ex){
            System.out.println(ex.toString());
        }

        return numberOfFiles;
    }

    //indicates whether Esc button was pressed
    public static boolean isEscPressed(){
        return escStatus;
    }

    public static void setIsEscPressed(boolean escPressed){
        FilesCounter.escStatus = escPressed;
    }

    //writes results of files counting to the specified output file
    public void writeDestinationFile(){
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(data.getDestinationFileName())))){
            for (Map.Entry<String, Integer> pair: countedFiles.entrySet()){
                String key = pair.getKey();
                Integer value = pair.getValue();
                String record = key + ";" + value;
                writer.write(record);
                writer.newLine();
            }
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }


    //displays records of the output files in the console
    public void showResults(String outputFile){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(outputFile));
            int column = 0;
            String line;
            while((line = reader.readLine()) != null){
                column++;
                Scanner scanner = new Scanner(line).useDelimiter(";");
                while (scanner.hasNext()){
                    String path = scanner.next();
                    String numberOfFiles = scanner.next();
                    System.out.println(column + "\t" + numberOfFiles + "\t" + path);
                }
            }
            searchFinished = true;
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

}

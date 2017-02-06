package filesforanalysis;


import java.util.Scanner;

/**
 * Class IncomingData takes and holds names of input and output files*/

public class IncomingData {
    private String sourceFileName;
    private String destinationFileName;

    public void initiate(){
        inputFiles();
    }


    private void inputFiles(){
        System.out.println("To start files count complete the following steps:");
        System.out.println("1. Insert value of full path to txt-file that contains different paths to data storage." + "\n" +
                "Count of files number will be performed for each path separately.");
        System.out.println("2. Insert value of full path to csv-file that includes counted results for each path.");
        System.out.println("Both values must be inserted into one row by dividing them by space as it is shown in example:" + "\n" +
        "paths.txt result.csv");
        System.out.println();
        Scanner input = new Scanner(System.in);
        this.sourceFileName = input.next();
        this.destinationFileName = input.next();
        input.close();
    }

    public String getSourceFileName(){
        return sourceFileName;
    }

    public String getDestinationFileName(){
        return destinationFileName;
    }
}

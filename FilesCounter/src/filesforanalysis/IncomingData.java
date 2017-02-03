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
        System.out.println("Enter two values.");
        System.out.println("1st - source file name, 2nd - output file name.");
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

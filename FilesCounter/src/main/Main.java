package main;

import filesforanalysis.FilesCounter;
import keyboardlistener.KeyPressDetector;

import static filesforanalysis.FilesCounter.counter;
import static filesforanalysis.FilesCounter.data;
import static filesforanalysis.FilesCounter.isSearchFinished;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("<Welcome to FilesCounter program!>");
        System.out.println();
        System.out.println("Program counts number of files in a directory and it's subdirectories.");
        System.out.println();
        data.initiate();
        KeyPressDetector detector = new KeyPressDetector();
        Thread thread2 = new Thread(detector);
        thread2.setDaemon(true);
        thread2.start();
        counter.readSourceFile();
        counter.processPaths();
        if (FilesCounter.isEscPressed()){
            System.out.println("You have interrupted files count.");
        }
        counter.writeDestinationFile();
        counter.showResults(data.getDestinationFileName());

        if (!FilesCounter.isEscPressed() || isSearchFinished()) {
            System.out.println("Count is finished. Press any key to exit.");
        }

    }
}

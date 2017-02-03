package main;

import filesforanalysis.FilesCounter;
import keyboardlistener.KeyPressDetector;

import static filesforanalysis.FilesCounter.counter;
import static filesforanalysis.FilesCounter.data;
import static filesforanalysis.FilesCounter.isSearchFinished;

public class Main {
    public static void main(String[] args)
    {
        data.initiate();
        KeyPressDetector detector = new KeyPressDetector();
        Thread thread2 = new Thread(detector);
        thread2.setDaemon(true);
        thread2.start();
        counter.readSourceFile();
        counter.processPaths();
        counter.writeDestinationFile();
        counter.showResults(data.getDestinationFileName());

        if (!FilesCounter.isEscPressed() && isSearchFinished()){
            System.out.println("Search is finished. Press any key to exit.");
        }

    }


}

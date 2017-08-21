package filescounter.implementation.main;


import filescounter.implementation.counterlogic.DataProcessor;
import filescounter.implementation.counterlogic.InitializedFilesCounter;
import filescounter.implementation.keyboardlistener.KeyPressDetector;
import java.io.IOException;

/**
 * Demonstrates work of the application
 */
public class Main {
    public static void main(String[] args)  throws InterruptedException {
        if (args.length == 2) {
            String file1 = args[0];
            String file2 = args[1];
            System.out.println("Welcome to updated version of <FilesCounter> application!");
            System.out.println("It will count all files in directories included to " + file1 + " and write them to " +
                    file2);
            System.out.println("Press <Esc> if you want to stop counting files.");
            DataProcessor counter = new InitializedFilesCounter(file1, file2);
            Thread keyboardListener = new Thread(new KeyPressDetector());
            keyboardListener.start();
            try {
                counter.readData();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Less than 2 incoming parameters were specified.");
        }
    }
}

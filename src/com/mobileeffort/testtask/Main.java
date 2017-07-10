package com.mobileeffort.testtask;


import java.io.IOException;


public class Main {
    public static void main(String[] args)  {
        String file1 = args[0];
        String file2 = args[1];
        FilesCounter counter = new InitializedFilesCounter(file1, file2);
        KeyPressDetector detector = new KeyPressDetector();
        Thread keyboardListener = new Thread(detector);
        keyboardListener.setDaemon(true);
        keyboardListener.start();
        try {
            counter.readData();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        counter.writeData();
        try {
            counter.displayResults();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

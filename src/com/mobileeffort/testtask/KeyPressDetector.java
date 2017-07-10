package com.mobileeffort.testtask;


import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class KeyPressDetector implements Runnable, NativeKeyListener {

    @Override
    public void run() {
        System.out.println("Press <Esc> if you want to interrupt the process.");
        GlobalScreen.getInstance().addNativeKeyListener(this);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VK_ESCAPE){
            InitializedFilesCounter.setIsEscPressed(true);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VK_ESCAPE){
            GlobalScreen.unregisterNativeHook();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

}

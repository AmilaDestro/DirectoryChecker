package keyboardlistener;


import filesforanalysis.FilesCounter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Class KeyPressDetector is responsible for listening to keyboard.
 **/

public class KeyPressDetector implements Runnable, NativeKeyListener {

    @Override
    public void run() {
        GlobalScreen.getInstance().addNativeKeyListener(this);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.out.println(e.toString());
        }

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VK_ESCAPE){
            FilesCounter.setIsEscPressed(true);
        }
        if (FilesCounter.isSearchFinished() && nativeKeyEvent.getKeyCode() != 0){
            GlobalScreen.unregisterNativeHook();
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
//        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VK_ESCAPE){
//            GlobalScreen.unregisterNativeHook();
//        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

}

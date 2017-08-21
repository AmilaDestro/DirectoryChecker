package filescounter.implementation.keyboardlistener;


import filescounter.implementation.counterlogic.InitializedFilesCounter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Global keyboard listener
 */
public class KeyPressDetector implements Runnable, NativeKeyListener {

    @Override
    public void run() {
        GlobalScreen.getInstance().addNativeKeyListener(this);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Invoked when a key has been pressed.
     * @param nativeKeyEvent the native key event.
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VK_ESCAPE){
            InitializedFilesCounter.setEscStatus(true);
            GlobalScreen.unregisterNativeHook();
        }
    }

    /**
     * Invoked when a key has been released.
     * @param nativeKeyEvent the native key event.
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}

    /**
     * Invoked when a key has been pressed.
     * @param nativeKeyEvent the native key event.
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

}

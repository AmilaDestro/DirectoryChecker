package filescounter.implementation.counterlogic;


/**
 *  Suggests method abstraction that can count files.
 */
public interface Countable {
    /**
     * Performs files counting.
     * @param filePath holds path to the directory for which files counting performed
     * @throws InterruptedException
     */
    void countFiles(String filePath) throws InterruptedException;
}

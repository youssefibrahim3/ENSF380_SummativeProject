package edu.ucalgary.oop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Class for logging database actions to action_log.txt
 *
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-04
 */
public class ActionLogger {
    private static final String FILE_NAME = "data/action_log.txt";
    private static ActionLogger instance = null;
    
    private ActionLogger() {}

    /**
     * Gets the current ActionLogger singleton instance. If none exist, creates a new instance.
     * 
     * @return The current/new ActionLogger instance
     */
    public static ActionLogger getInstance() {
        if (instance == null) instance = new ActionLogger();
        return instance;
    }

    /**
     * Writes a log as text into action_log.txt. The logged message follows a specific format.
     * 
     * @param action The action to log (e.g. ADDED)
     * @param description The description of the action
     */
    public void log(String action, String description)
    {
        try {
            FileOutputStream out = new FileOutputStream(FILE_NAME, true);
            String newEntry = "[" + LocalDate.now() + "] " + action + " " + description + "\n";
            out.write(newEntry.getBytes());
            out.close();
        } catch (IOException e) {
            System.out.println("Logging failed with exception: " + e.getMessage());
        }
    }
}

package edu.ucalgary.oop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Class for logging unrecoverable errors to errorlog.txt
 *
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-06
 */

public class ErrorLogger {
    private static final String FILE_NAME = "data/errorlog.txt";

    /**
     * Writes an error log as text into action_log.txt.
     * The error message follows a specific format.
     * 
     * @param exception The Exception that the error caused
     */
    public static void log(Exception exception)
    {
        try {
            FileOutputStream out = new FileOutputStream(FILE_NAME, true);
            String newEntry = "[" + LocalDateTime.now() + "] ERROR: " + exception.getMessage() + "\n";
            out.write(newEntry.getBytes());
            out.close();
        } catch (IOException e) {
            System.out.println("Could not write to error log: " + e.getMessage());
        }
    }
}

package edu.ucalgary.oop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

//singleton
public class ActionLogger {
    private static final String FILE_NAME = "src/data/action_log.txt";
    private static ActionLogger instance = null;
    
    private ActionLogger() {}

    public static ActionLogger getInstance() {
        if (instance == null) instance = new ActionLogger();
        return instance;
    }

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

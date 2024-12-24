package model;

public class Log {
    
    private static Log instance;
    
    private StringBuffer logBuffer;

    private Log() {
        logBuffer = new StringBuffer();
    }

    public static Log getInstance() {
        
        if (instance == null) {
            instance = new Log();
        }
        
        return instance;
    }

    public void addEntry(String entry) {
        logBuffer.append(String.format("[%s] %s%n", 
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_TIME),
            entry));
    }

    public void writeToFile(String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.print(logBuffer.toString());
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error writing log file: " + e.getMessage());
        }
    }

    public String getLog() {
        return logBuffer.toString();
    }
} 